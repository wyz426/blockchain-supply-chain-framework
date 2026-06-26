package com.blc.scm.pass;

import com.blc.scm.pass.models.BlockChainMember;
import com.blc.scm.pass.services.ChannelService;
import com.blc.scm.pass.services.Impl.ChainCodeServiceImpl;
import com.blc.scm.pass.services.Impl.ChannelServiceImpl;
import com.blc.scm.pass.services.Impl.FabricServiceImpl;
import com.blc.scm.pass.utils.YamlUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class fabricTest {

    @Test
    public void testFabricService() {
        // 准备测试数据
        List<BlockChainMember> orgs=new ArrayList<>();
        BlockChainMember org1=new BlockChainMember();
        org1.setId(1);
        org1.setName("org1");
        org1.setStatus(0);
        org1.setDescription("ok");
        org1.setBlockchain_id("org1");
        orgs.add(org1);

        FabricServiceImpl fabricService = new FabricServiceImpl();
        YamlUtil yamlUtil=new YamlUtil();
        // 调用要测试的函数
//        fabricService.setOrderOrg("org1");
//        fabricService.setPeerOrg("org1",orgs);
        fabricService.generateGenesis("org1",orgs);
        fabricService.networkUp("org1");
        String EXP_FILE_PATH = "/home/lan/blc-scm-pass-backend/src/main/resources/sudo_channel_script.exp";
        String fileName="configtx.yaml";
        String channelName="channel3";
        String blockName="channel1.block";
        ChannelServiceImpl channelService=new ChannelServiceImpl();
//        channelService.createChannelGenesisBlock(channelName,"org1","/home/lan/network/channel-artifacts/",orgs);
//        channelService.createChannel("org1",channelName,orgs);
        ChainCodeServiceImpl chainCodeService=new ChainCodeServiceImpl();
        String chaincode="testCC";
        //chainCodeService.packageChaincode(chaincode,"org1");
        // 使用断言验证函数的行为是否符合预期
        //Assertions.assertEquals(15, result);
    }

}
