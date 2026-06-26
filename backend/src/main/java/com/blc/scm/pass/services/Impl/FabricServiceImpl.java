package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blc.scm.pass.mapper.BlockChainMemberMapper;
import com.blc.scm.pass.models.BlockChain;
import com.blc.scm.pass.models.BlockChainMember;
import com.blc.scm.pass.utils.ExecUtil;
import com.blc.scm.pass.utils.YamlUtil;
import com.blc.scm.pass.services.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author Elaine Huang
 * @date 2024/3/21 5:55 PM
 * @signature Do it while you can!
 */
@Service
public class FabricServiceImpl implements FabricService {
    @Autowired
    private YamlUtil yamlUtil=new YamlUtil();

    @Autowired
    private ExecUtil execUtil=new ExecUtil();

    @Autowired
    private BlockChainMemberMapper blockChainMemberMapper;

    private final String DOCKER_COMPOSE_PATH = "/home/lan/network/{0}/compose/";

    private final String CRYPTO_PATH = "/home/lan/network/{0}/organizations/";

    private final String CONFIGTX_PATH = "/home/lan/network/{0}/configtx/";

    private final String CRYPTO_CMD = "cryptogen generate --config={0} --output {1}";

    private final String START_ORDER_CMD="orderer start";

    private final String START_ORG_CMD="peer node start";

    private final String ORDERER_GENESIS = "genesis.block";//创世区块的名字

    private final String EXP_FILE_PATH ="/home/lan/blc-scm-pass-backend/src/main/resources/templates/sudoScripts/sudo_script.exp";
    //private final String EXP_FILE_PATH = "/opt/src/github.com/hyperledger/fabric/scripts/test/sudo_script.exp";

    @Override
    public void buildChain(BlockChain blockChain) {
        QueryWrapper<BlockChainMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blockchain_id", blockChain.getId());
        List<BlockChainMember> orgs = blockChainMemberMapper.selectList(queryWrapper);
        setOrderOrg(blockChain.getName());
        setPeerOrg(blockChain.getName(), orgs);
        generateGenesis(blockChain.getName(), orgs);
        networkUp(blockChain.getName());
    }

    @Override
    public void setOrderOrg(String networkCode) {
        //存储网络加密材料的文件夹
        String fileDir = MessageFormat.format(CRYPTO_PATH, networkCode);
        //配置文件的名称
        String fileName = "crypto-config-order.yaml";
        //判断网络的加密材料文件夹是否存在
        yamlUtil.folderExists(fileDir);
        String filePath=fileDir + fileName;
        Map<String,Object> configs = yamlUtil.readYamlAsMap("src/main/resources/templates/crypto-config-order.yaml");
        yamlUtil.writeMapToYamlFile(configs,filePath);
        //设置cryptogen执行命令，为该组织生成加密文件
        String cmd = MessageFormat.format(CRYPTO_CMD, filePath, fileDir);
        execUtil.execute("../bin/"+cmd);
    }

    @Override
    public void setPeerOrg(String networkCode, List<BlockChainMember> orgs) {
        //存储网络加密材料的文件夹
        String fileDir = MessageFormat.format(CRYPTO_PATH, networkCode);
        //配置文件的名称模板
        String fileName = "crypto-config-{0}.yaml";
        //判断网络的加密材料文件夹是否存在
        yamlUtil.folderExists(fileDir);
        //为每个组织成员生成加密材料
        for (BlockChainMember org : orgs) {
            //为该组织成员设置配置文件的名称
            String orgFileName = MessageFormat.format(fileName, org.getName());
            //该组织成员的配置文件存储路径
            String filePath = fileDir + orgFileName;
            String name = org.getName()+"MSP";
            //生成该组织的配置文件
            Map<String,Object> configs=yamlUtil.readYamlAsMap("src/main/resources/templates/crypto-config-org.yaml");
            configs=yamlUtil.setValue(configs,"Name",org.getName());
            configs=yamlUtil.setValue(configs,"Domain",org.getName()+".example.com");
            yamlUtil.writeMapToYamlFile(configs,filePath);
            //设置cryptogen执行命令，为该组织生成加密文件
            String cmd = MessageFormat.format(CRYPTO_CMD, filePath, fileDir);
            execUtil.execute("../bin/"+cmd);
        }
    }

    @Override
    public void startOrder() {
        execUtil.executeCommand(START_ORDER_CMD);
    }

    @Override
    public void startOrg() {
        execUtil.executeCommand(START_ORG_CMD);
    }

    @Override
    public void generateGenesis(String networkCode,List<BlockChainMember> orgs) {//定义了系统通道配置，用来创建系统通道所需初始区块文件
        //configtx的存储名称以及存储路径
        String fileDir = MessageFormat.format(CONFIGTX_PATH, networkCode);
        yamlUtil.folderExists(fileDir);
        String fileName = "configtx.yaml";
        String filePath = fileDir + fileName;
        Map<String,Object> configs=yamlUtil.readYamlAsMap("src/main/resources/templates/configtx.yaml");

        //生成相应peer节点的部分
        Map<String,Object> peerConfigs=yamlUtil.readYamlAsMap("src/main/resources/templates/configtx-org.yaml");
        peerConfigs=yamlUtil.replacePeerConfigtx(peerConfigs,orgs.get(0));

        //把configtx里的Organizations[0]换成固定的orderer，然后往后面加peerConfigs
        //然后替换configtx里的orderer.address和 orderer.EtcdRaft.Consenters
        configs=yamlUtil.addConsortiumPeer(configs,peerConfigs);

        //然后生成configtx到指定目录
        yamlUtil.writeMapToYamlFile(configs,filePath);

        //创世区块的存储位置
        String blockPath="/home/lan/network/channel-artifacts/";
        yamlUtil.folderExists(blockPath);
        execUtil.executeSudo(EXP_FILE_PATH,"");
        execUtil.execute("mv /home/lan/blc-scm-pass-backend/"+ORDERER_GENESIS+" "+blockPath);
    }

    public void generateAnchorUpdateFile(){

    }

    public boolean peersExist(){
        File organizationsDir = new File("organizations/peerOrganizations");
        return organizationsDir.exists();
    }

    @Override
    public void networkUp(String networkCode){
        //配置compose文件,先生成org部分
        String name="peer0."+networkCode+".example.com";
        Map<String,Object> composeOrg=yamlUtil.readYamlAsMap("src/main/resources/templates/compose-org.yaml");
        composeOrg=yamlUtil.replacePeerCompose(composeOrg,networkCode);
        Map<String,Object> compose=yamlUtil.readYamlAsMap("src/main/resources/templates/compose-test-net.yaml");
        compose=yamlUtil.replaceCompose(compose,name,composeOrg);
        //存储compose
        String fileDir = MessageFormat.format(DOCKER_COMPOSE_PATH, networkCode);
        yamlUtil.folderExists(fileDir);
        String fileName = "compose-test-net.yaml";
        String filePath = fileDir + fileName;
        yamlUtil.writeMapToYamlFile(compose,filePath);
        //生成docker-compose文件
        Map<String,Object> dockerCompose=yamlUtil.readYamlAsMap("src/main/resources/templates/docker-compose-test-net.yaml");
        Map<String,Object> dockerOrgCompose=yamlUtil.readYamlAsMap("src/main/resources/templates/docker-compose-org.yaml");
        Map<String,Object> newPeer=yamlUtil.replaceDockerPeer(dockerOrgCompose,name);
        dockerCompose=yamlUtil.replaceDocker(dockerCompose,name,newPeer);
        //存储docker-compose文件
        String dockerDir=fileDir+"docker/";
        yamlUtil.folderExists(dockerDir);
        String dockerFileName = "docker-compose-test-net.yaml";
        String dockerFilePath = dockerDir + dockerFileName;
        yamlUtil.writeMapToYamlFile(dockerCompose,dockerFilePath);
        //执行docker命令（core.yaml复制之后关闭网络然后重新启动了一遍）
        String path=MessageFormat.format("/home/lan/network/{0}/",networkCode);
        execUtil.execute("cp /home/lan/blc-scm-pass-backend/src/main/resources/templates/sudoScripts/docker.exp " +path);
        execUtil.execute("chmod +x "+path+ "docker.exp");
        execUtil.executeDocker(path+ "docker.exp",path);
    }
}
