package com.blc.scm.pass.FabricClient;

import com.blc.scm.pass.mapper.BlockChainMemberMapper;
import com.blc.scm.pass.models.BlockChainMember;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;

/**
 * @author Elaine Huang
 * @date 2024/4/26 12:47 AM
 * @signature Do it while you can!
 */
@Service
public class FabricClientService {
    @Autowired
    BlockChainMemberMapper blockChainMemberMapper;
    //更新成本地存文件对应的路径
    private final String NET_CONFIG_PATH = "/home/lan/network/{0}/organizations/peerOrganizations/{1}/connection-{2}.yaml";
    private final String CERT_PATH = "/home/lan/network/{0}/organizations/peerOrganizations/{1}/users/Admin@{2}/msp/signcerts/cert.pem";
    private final String PRIKEY_PATH = "/home/lan/network/{0}/organizations/peerOrganizations/{1}/users/Admin@{2}/msp/keystore/priv_sk";

    private Gateway connect(String network, Integer orgId) throws Exception{
        BlockChainMember org = blockChainMemberMapper.selectById(orgId);
        String orgName = org.getName();
        String certificatePath = MessageFormat.format(CERT_PATH, network, org.getName()+".example.com", org.getName());
        String PrivateKeyPath = MessageFormat.format(PRIKEY_PATH, network, org.getName()+".example.com", org.getName());
        String networkConfigPath = MessageFormat.format(NET_CONFIG_PATH, network, org.getName()+".example.com", org.getName());;
        //获取区块链成员的加密材料地址
        X509Certificate certificate = readX509Certificate(Paths.get(certificatePath));
        PrivateKey privateKey = getPrivateKey(Paths.get(PrivateKeyPath));
        Wallet wallet = Wallets.newInMemoryWallet();
        wallet.put(orgName, Identities.newX509Identity("Org" + orgId + "MSP",   certificate, privateKey));
        // ...
//      Path walletPath = Paths.get("wallet");
//    	Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
//        Path networkConfigPath = Paths.get("..", "..", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");
        Gateway.Builder builder = Gateway.createBuilder();
        //连接到指定的Fabric区块链网络
        builder.identity(wallet, orgName).networkConfig(Paths.get(networkConfigPath)).discovery(true);
        return builder.connect();
    }

    public void submitTransaction(String network, Integer orgId, FabricRequest request) throws Exception {
        //连接到Fabric区块链网络
        Gateway gateway = connect(network, orgId);
        //获取指定通道
        Network channel = gateway.getNetwork(request.getChannelName());
        //获取指定链码
        Contract contract = channel.getContract(request.getChaincodeName());
        //调用链码中的函数并传入参数
        contract.submitTransaction(request.getFuncName(), request.getDataId(), request.getData());
    }

    public byte[] evaluateTransaction(String network, Integer orgId, FabricRequest request) throws Exception {
        byte[] result;
        Gateway gateway = connect(network, orgId);
        // get the network and contract
        Network channel = gateway.getNetwork(request.getChannelName());
        Contract contract = channel.getContract(request.getChaincodeName());
        result = contract.evaluateTransaction(request.getFuncName(), request.getDataId(), request.getData());
        return result;
    }

    private X509Certificate readX509Certificate(final Path certificatePath) throws IOException, CertificateException {
        try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
            return Identities.readX509Certificate(certificateReader);
        }
    }

    private PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
        try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
            return Identities.readPrivateKey(privateKeyReader);
        }
    }
}
