package com.blc.scm.pass.services.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.ChaincodeMapper;
import com.blc.scm.pass.mapper.ChannelChainMapper;
import com.blc.scm.pass.models.Chaincode;
import com.blc.scm.pass.services.ChaincodeService;
import com.blc.scm.pass.utils.ExecUtil;
import com.blc.scm.pass.utils.YamlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;

@Service
public class ChainCodeServiceImpl extends ServiceImpl<ChaincodeMapper, Chaincode> implements ChaincodeService {
    @Autowired
    private YamlUtil yamlUtil=new YamlUtil();
    @Autowired
    private ExecUtil execUtil=new ExecUtil();

    private final String chaincodePath="/home/lan/network/chaincode/chaincode-go/";
    private final String configPath="/home/lan/network/config/";
    private final String packagedCCPath="/home/lan/network/chaincode/";
    private final String mspPath="/home/lan/network/{0}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem";
    private final String packagedCCName="{0}.tar.gz";

    public void downloadChaincode(){//下载标准链码（只用做一次）
        execUtil.execute("cd "+chaincodePath);//进入network/chaincode/chaincode-go
        execUtil.executeSudo("src/main/resources/templates/sudoScripts/downloadCC.exp",chaincodePath);
    }

    public void loadCore(){
        //把core.yaml复制到network/config文件夹（只用做一次）
        yamlUtil.folderExists(configPath);
        Map<String,Object> core=yamlUtil.readYamlAsMap("src/main/resources/templates/core.yaml");
        yamlUtil.writeMapToYamlFile(core,configPath+"core.yaml");
    }

    @Override
    public void installChaincode(String chaincodeName){
        String exp=yamlUtil.readExp("src/main/resources/templates/sudoScripts/installCC.exp");
        exp=MessageFormat.format(exp,chaincodeName);
        String filePath=packagedCCPath+"installCC_"+chaincodeName+".exp";
        yamlUtil.writeExp(filePath,exp);
        execUtil.execute("chmod +x "+filePath);
        execUtil.executeSudo(filePath,configPath);
    }

    @Override
    public void packageChaincode(String chaincodeName){
        //在config目录下执行脚本
        String exp=yamlUtil.readExp("src/main/resources/templates/sudoScripts/packageCC.exp");
        exp=MessageFormat.format(exp,chaincodeName);
        String filePath=packagedCCPath+"packageCC_"+chaincodeName+".exp";
        yamlUtil.writeExp(filePath,exp);
        execUtil.execute("chmod +x "+filePath);
        execUtil.executeSudo(filePath,configPath);
    }
    @Override
    public void approveChaincode(String chaincodeName, String network,String channelName){
        String exp=yamlUtil.readExp("src/main/resources/templates/sudoScripts/approveCC.exp");
        exp=MessageFormat.format(exp,network,channelName,chaincodeName);
        String filePath=packagedCCPath+"approveCC_"+chaincodeName+".exp";
        yamlUtil.writeExp(filePath,exp);
        execUtil.execute("chmod +x "+filePath);
        execUtil.executeSudo(filePath,configPath);
    }
    @Override
    public void commitChaincode(String chaincodeName, String network,String channelName){
        String mspPATH=MessageFormat.format(mspPath,network);
        execUtil.execute("export ORDERER_CA="+mspPATH);
        String exp=yamlUtil.readExp("src/main/resources/templates/sudoScripts/commitCC.exp");
        exp=MessageFormat.format(exp,channelName,chaincodeName);
        String filePath=packagedCCPath+"commitCC_"+chaincodeName+".exp";
        yamlUtil.writeExp(filePath,exp);
        execUtil.execute("chmod +x "+filePath);
        execUtil.executeSudo(filePath,configPath);
    }

//    deployChaincode()
}
