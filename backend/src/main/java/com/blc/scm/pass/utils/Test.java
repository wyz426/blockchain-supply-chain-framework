package com.blc.scm.pass.utils;

import cn.dev33.satoken.secure.BCrypt;

import java.text.MessageFormat;

/**
 * @author Elaine Huang
 * @date 2024/3/21 6:09 PM
 * @signature Do it while you can!
 */
public class Test {
    public static void main(String[] args) {
        String url2 = "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String context2 = MessageFormat.format(url2, "127.0.0.1", "3306", "test001");

        String filePath = "/Users/zerohuang/{0}/organizations/cryptogen/";
        String path = MessageFormat.format(filePath,"net");

        YamlUtil util = new YamlUtil();
        String file = "/Users/zerohuang/blc/fabric-operations-console/fabric-samples/test-network/organizations/cryptogen/crypto-config-org1.yaml";
        System.out.println(util.fileExists(filePath));
        String CRYPTO_CMD = "cryptogen generate --config={0} --output={1}";
        String cmd = MessageFormat.format(CRYPTO_CMD, file, file);
//        Map<String, Object> map = util.readYaml();
        System.out.println(cmd);
        System.out.println(BCrypt.hashpw("hyl1214"));

    }
}
