package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.ChannelMapper;
import com.blc.scm.pass.models.BlockChainMember;
import com.blc.scm.pass.models.Channel;
import com.blc.scm.pass.services.ChannelService;
import com.blc.scm.pass.utils.ExecUtil;
import com.blc.scm.pass.utils.YamlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Elaine Huang
 * @date 2024/3/25 10:16 PM
 * @signature Do it while you can!
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {

    @Autowired
    private FabricServiceImpl fabricServiceImpl=new FabricServiceImpl();

    private final String CONFIGTX_PATH = "/home/lan/network/{0}/configtx/";
    private final String CONFIGTX_FILENAME="configtx-{0}.yaml";
    private final String CRYPTO_PATH = "/home/lan/network/{0}/compose/";
    private final String CHANNEL_File_PATH = "/home/lan/network/channel-artifacts/";
    private final String EXP_FILE_PATH = "/home/lan/blc-scm-pass-backend/src/main/resources/templates/sudoScripts/sudo_channel_script.exp";
    private final String ORDERER_CA="../organizations/ordererOrganizations/example.com/tlsca/tlsca.example.com-cert.pem";
    private final String ORDERER_CERT="../organizations/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt";
    private final String ORDERER_PRIVATE_KEY="../organizations/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.key";

    @Autowired
    private YamlUtil yamlUtil=new YamlUtil();
    @Autowired
    private ExecUtil execUtil=new ExecUtil();
    @Override
    public void createChannel(String network, String channelName, List<BlockChainMember> orgs) {
        //创建channel-artifacts文件夹
        yamlUtil.folderExists(CHANNEL_File_PATH);
        //调用创建通道创世区块的方法
        //createChannelGenesisBlock(channelName,network,CHANNEL_File_PATH,orgs);
        //order加入通道
        String cmd=yamlUtil.readExp("src/main/resources/templates/sudoScripts/createChannel.exp");
        cmd=MessageFormat.format(cmd,channelName,CHANNEL_File_PATH+channelName+".block",ORDERER_CA,ORDERER_CERT,ORDERER_PRIVATE_KEY,ORDERER_CA,ORDERER_CERT,ORDERER_PRIVATE_KEY);
        String workPath=MessageFormat.format(CRYPTO_PATH,network);
        yamlUtil.writeExp(workPath+"createChannel.exp",cmd);
        execUtil.execute("chmod +x "+workPath+"createChannel.exp");
        execUtil.executeSudo(workPath+"createChannel.exp",workPath);
    }

    public void createChannelGenesisBlock(String channelName,String networkCode,String channelBlockPath,List<BlockChainMember>orgs){
        //configtx的存储名称以及存储路径
        String fileDir = MessageFormat.format(CONFIGTX_PATH, networkCode);
        yamlUtil.folderExists(fileDir);
        String filePath = fileDir + "configtx.yaml";
        Map<String,Object> configs=yamlUtil.readYamlAsMap("src/main/resources/templates/configtx-channel.yaml");
        //生成相应peer节点的部分
        Map<String,Object> peerConfigs=yamlUtil.readYamlAsMap("src/main/resources/templates/configtx-org.yaml");
        peerConfigs=yamlUtil.replacePeerConfigtx(peerConfigs,orgs.get(0));
        //添加application的organizations
        configs=yamlUtil.setApplicationOrganization(configs,peerConfigs);
        //把这个模板加进configtx
        Map<String,Object> oldConfigs=yamlUtil.readYamlAsMap(filePath);
        oldConfigs=yamlUtil.addApplicationProfile(oldConfigs,configs,channelName+"UsingRaft");
        //然后生成configtx到指定目录
        yamlUtil.writeMapToYamlFile(oldConfigs,filePath);
        //根据通道名称配置exp脚本
        String blockName=channelName + ".block";
        String file= yamlUtil.readExp(EXP_FILE_PATH);
        file=MessageFormat.format(file,fileDir,channelName,channelName+"UsingRaft",blockName);
        String path=MessageFormat.format(CRYPTO_PATH,networkCode);
        yamlUtil.writeExp(path+ "sudo_channel_script.exp",file);
        execUtil.execute("chmod +x "+path+ "sudo_channel_script.exp");
        //执行脚本，创建通道创世区块
        execUtil.executeSudo(path+ "sudo_channel_script.exp","");
        execUtil.execute("mv /home/lan/blc-scm-pass-backend/"+blockName+" "+ channelBlockPath);
    }

//    @Override
//    public void createChannel(String network, String channelName, List<BlockChainMember> orgs) {
//        //获取configtx配置文件的地址
//        String configtxPath = MessageFormat.format(CONFIGTX_PATH, network);
//        String txFile = CHANNEL_File_PATH + channelName + ".tx";
//        generateConfigtx(channelName, txFile, orgs);
//        Map<String, Object> configs = yamlUtil.readYamlAsMap(configtxPath);
//        Map<String, Object> profiles = (Map<String, Object>) configs.get("Profiles");
//        Map<String, Object> newProfile = Map.of(
//                // 新Profile的内容
//                channelName+"Profile", Map.of(
//                        "<<", "SomeValue",
//                        "Orderer", Map.of(
//                                // 根据需要构造新的Profile结构
//                        ),
//                        "Application", Map.of(
//                                // 根据需要构造新的Profile结构
//                        )
//                )
//        );
//        //        profiles
//        String[] cmd =
//                {
//                        "peer", "channel", "create",
////                        "-o", orderService,
//                        "-c", channelName,
//                        "-f", txFile,
//                        "-outputBlock" , CHANNEL_File_PATH + channelName + ".block",
//                        "--timeout", "30s",
//                        "--tls",
////                        "--cafile", orderCAFile
//                };
//        //        configtxgen \
//        //        -configPath ./ \
//        //        -channelID ${APP_CHANNEL} \
//        //        -profile ${APP_CHANNEL_PROFILE} \
//        //        -asOrg Org1MSP \
//        //        -outputAnchorPeersUpdate ${UPDATE_ANCHOR_ORG1_TX}
//        execUtil.executeCommand(Arrays.toString(cmd));
//    }

    @Override
    public void joinChannel(String network, String channelName, List<BlockChainMember> orgs) {
        //获取configtx配置文件的地址
        String configtxPath = MessageFormat.format(CONFIGTX_PATH, network);
        String txFile = CHANNEL_File_PATH + channelName + ".tx";
        generateConfigtx(channelName, txFile, orgs);
        Map<String, Object> configs = yamlUtil.readYamlAsMap(configtxPath);
        Map<String, Object> profiles = (Map<String, Object>) configs.get("Profiles");
        Map<String, Object> newProfile = Map.of(
                // 新Profile的内容
                channelName+"Profile", Map.of(
                        "<<", "SomeValue",
                        "Orderer", Map.of(
                                // 根据需要构造新的Profile结构
                        ),
                        "Application", Map.of(
                                // 根据需要构造新的Profile结构
                        )
                )
        );
        //        profiles
        String[] cmd =
                {
                        "peer", "channel", "create",
//                        "-o", orderService,
                        "-c", channelName,
                        "-f", txFile,
                        "-outputBlock" , CHANNEL_File_PATH + channelName + ".block",
                        "--timeout", "30s",
                        "--tls",
//                        "--cafile", orderCAFile
                };
        //        configtxgen \
        //        -configPath ./ \
        //        -channelID ${APP_CHANNEL} \
        //        -profile ${APP_CHANNEL_PROFILE} \
        //        -asOrg Org1MSP \
        //        -outputAnchorPeersUpdate ${UPDATE_ANCHOR_ORG1_TX}
        execUtil.executeCommand(Arrays.toString(cmd));
    }

    public void setAnchorPeer(){

    }
    @Override
    public void generateConfigtx(String channelName, String txFile, List<BlockChainMember> orgs) {
    }

    @Override
    public void updateChannel() {
    }

    @Override
    public void delChannel() {
    }
}
