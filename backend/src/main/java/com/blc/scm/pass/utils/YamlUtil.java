package com.blc.scm.pass.utils;

import com.blc.scm.pass.models.BlockChainMember;
import lombok.Cleanup;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Elaine Huang
 * @date 2024/3/21 5:49 PM
 * @signature Do it while you can!
 */
@Component
public class YamlUtil {

    public void folderExists(String filePath){
        File folder = new File(filePath);
        if (!folder.exists() && !folder.isDirectory()) {
            boolean create=folder.mkdirs();
            if(create) System.out.println("创建文件夹成功");
            else System.out.println("创建文件夹失败");
        } else {
            System.out.println("文件夹已存在");
        }
    }

    public boolean fileExists(String path){
        File file = new File(path);
        return file.exists();
    }
    public String readExp(String filePath){
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n"); // 如果需要保留换行符，可以注释掉这一行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public void writeExp(String filePath, String content){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,Object> readYamlAsMap(String fileName){
        Map<String, Object> configs = new LinkedHashMap<>();
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setExplicitStart(true);
        options.setCanonical(false);
        options.setSplitLines(false);
        Yaml yaml = new Yaml(options);
        try {
            //@Cleanup InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);
            @Cleanup InputStream in = new FileInputStream(fileName);
            configs = yaml.loadAs(in, LinkedHashMap.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return configs;
    }

    public Map<String,Object> setValue(Map<String,Object> configs, String key,Object value) {
        //String template = (String) configs.get(key);
        configs.put(key, value);
        return configs;
    }
    public Map<String,Object> replaceDocker(Map<String,Object> dockerCompose,String name,Map<String,Object> newPeer){
        Map<String,Object> services= (Map<String, Object>) dockerCompose.get("services");
        services=setValue(services,name,newPeer.get(name));
        dockerCompose=setValue(dockerCompose,"services",services);
        return dockerCompose;
    }

    public Map<String,Object> replaceDockerPeer(Map<String,Object> dockerOrgCompose,String name){
        Map<String,Object> peerConfig= (Map<String, Object>) dockerOrgCompose.get("peer");
        peerConfig=setValue(peerConfig,"container_name",name);
        return setValue(new HashMap<>(),name,peerConfig);
    }

    public Map<String,Object> replacePeerCompose(Map<String,Object> composeOrg,String networkCode){
        String name="peer0."+networkCode+".example.com";
        Map<String,Object> nestedCompose= (Map<String, Object>) composeOrg.get("peerOrg");
        Map<String,Object> newComposeOrg=setValue(new HashMap<>(),name,nestedCompose);
        Map<String,Object> peer= (Map<String, Object>) newComposeOrg.get(name);
        peer=setValue(peer,"container_name",name);
        List<Object> volumes= (List<Object>) peer.get("volumes");
        volumes.set(0,"../organizations/peerOrganizations/"+networkCode+".example.com/peers/"+name+":/etc/hyperledger/fabric");
        volumes.set(1,name+":/var/hyperledger/production");
        peer=setValue(peer,"volumes",volumes);
        List<Object> env= (List<Object>) peer.get("environment");
        env.set(7,"CORE_PEER_ID="+name);
        env.set(8,"CORE_PEER_ADDRESS="+name+":9051");
        env.set(10,"CORE_PEER_CHAINCODEADDRESS="+name+":9052");
        env.set(12,"CORE_PEER_GOSSIP_EXTERNALENDPOINT="+name+":9051");
        env.set(13,"CORE_PEER_GOSSIP_BOOTSTRAP="+name+":9051");
        env.set(14,"CORE_PEER_LOCALMSPID="+networkCode+"MSP");
        env.set(16,"CORE_OPERATIONS_LISTENADDRESS="+name+":9445");
        env.set(18,"CHAINCODE_AS_A_SERVICE_BUILDER_CONFIG={\"peername\":\"peer0"+networkCode+"\"}");
        peer=setValue(peer,"environment",env);
        newComposeOrg=setValue(newComposeOrg,name,peer);
        return newComposeOrg;
    }

    public Map<String,Object> replaceCompose(Map<String,Object> compose,String name,Map<String,Object> composeOrg){
        Map<String,Object> volumes= (Map<String, Object>) compose.get("volumes");
        volumes.put(name,null);
        compose=setValue(compose,"volumes",volumes);
        Map<String,Object> services= (Map<String, Object>) compose.get("services");
        services=setValue(services,name,composeOrg.get(name));
        Map<String,Object> cli= (Map<String, Object>) services.get("cli");
        folderExists("../scripts");
        List<String> depends=(List<String>) cli.get("depends_on");
        if(depends.get(0)!=null)depends.add(name);
        else depends.set(0,name);
        cli=setValue(cli,"depends_on",depends);
        services=setValue(services,"cli",cli);
        compose=setValue(compose,"services",services);
        return compose;
    }

    public Map<String,Object> replacePeerConfigtx(Map<String,Object> configs,BlockChainMember org){
        String name=org.getName()+"MSP";
        configs=setValue(configs,"Name",name);
        configs=setValue(configs,"ID",org.getBlockchain_id()+"MSP");
        configs=setValue(configs,"MSPDir","../organizations/peerOrganizations/"+org.getName()+".example.com/msp");
        configs=setHostValue(configs,"Host","peer0."+org.getName()+".example.com");
        configs=setRuleValue(configs,"Readers","OR('"+name+".admin', '"+name+".peer', '"+name+".client')");
        configs=setRuleValue(configs,"Writers","OR('"+name+".admin', '"+name+".client')");
        configs=setRuleValue(configs,"Admins","OR('"+name+".admin')");
        configs=setRuleValue(configs,"Endorsement","OR('"+name+".member')");
        return configs;
    }

    public Map<String,Object> setRuleValue(Map<String,Object> configs, String key,Object value){
        Map<String,Object> policiesConfigs= (Map<String, Object>) configs.get("Policies");
        Map<String,Object> newConfigs= (Map<String, Object>) policiesConfigs.get(key);
        newConfigs=setValue(newConfigs,"Rule",value);
        policiesConfigs=setValue(policiesConfigs,key,newConfigs);
        return setValue(configs,"Policies",policiesConfigs);
    }

    public Map<String,Object> setHostValue(Map<String,Object> configs, String key,Object value){
        List<Object> hostConfigs= (List<Object>) configs.get("AnchorPeers");
        Map<String,Object> newConfigs= (Map<String, Object>) hostConfigs.get(0);
        newConfigs=setValue(newConfigs,key,value);
        hostConfigs.set(0,newConfigs);
        return setValue(configs,"AnchorPeers",hostConfigs);
    }

    public Map<String, Object> replaceListValue(Map<String, Object> configs, String key, Map<String, Object> peer) {
        List<Map<String, Object>> organizations = (List<Map<String, Object>>) configs.get(key);
        if(organizations.get(0)==null)organizations.set(0, peer);
        else organizations.add(peer);
        configs.put(key,organizations);
        return configs;
    }
    public Map<String,Object> setApplicationOrganization(Map<String, Object> configs, Map<String,Object>peer){
        Map<String,Object> applications=(Map<String, Object>) configs.get("Application");
        applications=replaceListValue(applications,"Organizations",peer);
        return setValue(configs,"Application",applications);
    }
    public Map<String,Object> addConsortiumPeer(Map<String, Object> configs, Map<String,Object>peer){
        Map<String, Object>Profiles=(Map<String, Object>) configs.get("Profiles");
        Map<String, Object>Genesis=(Map<String, Object>) Profiles.get("TwoOrgsOrdererGenesis");
        Map<String, Object> Consortiums =(Map<String, Object>)Genesis.get("Consortiums");
        Map<String, Object> SampleConsortiums = (Map<String, Object>) Consortiums.get("SampleConsortium");
        SampleConsortiums=replaceListValue(SampleConsortiums,"Organizations",peer);
        Consortiums=setValue(Consortiums,"SampleConsortium",SampleConsortiums);
        Genesis=setValue(Genesis,"Consortiums",Consortiums);
        Profiles=setValue(Profiles,"TwoOrgsOrdererGenesis",Genesis);
        return setValue(configs,"Profiles",Profiles);
    }
    public void writeMapToYamlFile(Map<String, Object> configs, String fileName) {
        try {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            FileWriter writer = new FileWriter(fileName);
            yaml.dump(configs, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Map<String,Object> addApplicationProfile(Map<String, Object> configs,Map<String,Object> applications , String raftName) {
        Map<String,Object> profiles=(Map<String, Object>) configs.get("Profiles");
        profiles.put(raftName,applications);
        configs=setValue(configs,"Profiles",profiles);
        return configs;
    }

}
