package com.blc.scm.pass.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Elaine Huang
 * @date 2024/3/20 9:35 PM
 * @signature Do it while you can!
 */
@Data
@Component
public class ExecUtil {
    //缓存session会话
    private Session session;

    //通道：执行命令
    private ChannelExec cmdExec;
    //通道：SFTP
    private ChannelSftp channelSftp;
    //通道：执行复杂Shell命令
    private ChannelShell cmdShell;

    private final String CONTAINER_CLI = "docker";

    private final String CONTAINER_CLI_COMPOSE = "docker-compose";

    private final String COMPOSE_FILE_BASE="compose-test-net.yaml";

    private final String DOCKER_SOCK = "/var/run/docker.sock";

    //登陆Linux服务器
    public void loginLinux(String username, String password, String host, Integer port) {
        try {
            //每次都会重新初始化session
            if (Objects.isNull(session) || !session.isConnected()) {
                JSch jsch = new JSch();
                session = jsch.getSession(username, host, port);
                session.setPassword(password);

                // 配置Session参数
                Properties config = new Properties();
                // 不进行公钥的检查
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                // 设置连接超时时间（s/秒）
                session.setTimeout(300);
            }
            if (!session.isConnected()) {
                // 连接到远程服务器
                session.connect();
            }
        }catch(Exception e){
            throw new RuntimeException("连接Linux失败：" + e.getMessage());
        }
    }
    public void execute(String cmd){
        try {
            // 创建ProcessBuilder对象，设置要执行的命令
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
            System.out.println(processBuilder.command());
            // 设置工作目录（可选）
            processBuilder.directory(new File("/opt/src/github.com/hyperledger/fabric/scripts/test"));
            // 启动进程
            Process process = processBuilder.start();
            // 获取进程输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 读取输出
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("命令执行完成，退出码：" + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void executeSudo(String scriptPath,String workPath){
        try {
            // 脚本路径
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash","-c", scriptPath);
            // 设置工作目录（可选）
            System.out.println(workPath);
            if(!Objects.equals(workPath, ""))processBuilder.directory(new File(workPath));
            Process process = processBuilder.start();
            // 获取脚本的标准输出流
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // 读取脚本输出
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // 等待脚本执行完成
            int exitCode = process.waitFor();
            System.out.println("脚本执行完成，退出码：" + exitCode);
            // 关闭流
            reader.close();
            inputStream.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    //要在compose的上层执行
    public void executeDocker(String path,String Dir){
        try {
            System.out.println(path);
            // 脚本路径
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash","-c", path);
            processBuilder.directory(new File(Dir));
            Process process = processBuilder.start();
            // 获取脚本的标准输出流
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // 读取脚本输出
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // 等待脚本执行完成
            int exitCode = process.waitFor();
            System.out.println("脚本执行完成，退出码：" + exitCode);
            // 关闭流
            reader.close();
            inputStream.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    //执行命令：可以多次执行，然后必须调用关闭接口
    public String executeCommand(String command) {
        StringBuilder result = new StringBuilder();
        BufferedReader buf = null;
        try {
            //每次执行都创建新的通道
            cmdExec = (ChannelExec) session.openChannel("exec");
            cmdExec.setCommand(command);

            //正确的流中没有数据就走错误流中去拿。
            InputStream in = cmdExec.getInputStream();
            InputStream errStream = cmdExec.getErrStream();
            cmdExec.connect();

            buf = new BufferedReader(new InputStreamReader(in));
            String msg;
            while ((msg = buf.readLine()) != null) {
                result.append(msg);
            }

            if (StringUtils.isBlank(result.toString())) {
                buf = new BufferedReader(new InputStreamReader(errStream));
                String msgErr;
                while ((msgErr = buf.readLine()) != null) {
                    result.append(msgErr);
                }
            }
        }catch(Exception e){
            throw new RuntimeException("关闭连接失败（执行命令）：" + e.getMessage());
        }finally {
            if (Objects.nonNull(buf)) {
                try {
                    buf.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * 执行复杂shell命令
     *
     * @param cmds 多条命令
     * @return 执行结果
     * @throws Exception 连接异常
     */
    public String execCmdByShell(List<String> cmds) {

        String result = "";
        try {
            cmdShell = (ChannelShell) session.openChannel("shell");

            InputStream inputStream = cmdShell.getInputStream();
            cmdShell.setPty(true);
            cmdShell.connect();
            OutputStream outputStream = cmdShell.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            for (String cmd : cmds) {
                printWriter.println(cmd);
            }
            printWriter.flush();
            byte[] tmp = new byte[1024];
            while (true) {
                while (inputStream.available() > 0) {
                    int i = inputStream.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    String s = new String(tmp, 0, i);
                    if (s.contains("--More--")) {
                        outputStream.write((" ").getBytes());
                        outputStream.flush();
                    }
                    System.out.println(s);
                }
                if (cmdShell.isClosed()) {
                    System.out.println("exit-status:" + cmdShell.getExitStatus());
                    break;
                }
                //间隔1s后再执行
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            outputStream.close();
            inputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    //下载除了云服务器的文件（你自己的服务器）：因为云服务器，像阿里云服务器下载文件好像是一段一段给你的，不是一起给你。
    public void downloadOtherFile(String remoteFileAbsolutePath, String fileName, HttpServletResponse response) {
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            //获取输入流
            InputStream inputStream = channelSftp.get(remoteFileAbsolutePath);
            //直接下载到本地文件
//            channelSftp.get(remoteFileAbsolutePath, "D:\\Develop\\Test\\studio-3t-x64.zip");

            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/octet-stream;charset=".concat(StandardCharsets.UTF_8.name()));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=".concat(
                            URLEncoder.encode(fileName, StandardCharsets.UTF_8.name())
                    ));
            ServletOutputStream out = response.getOutputStream();

            // 从InputStream输入流读取数据 并写入到ServletOutputStream输出流
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            out.close();
        }catch(Exception e){
            throw new RuntimeException("关闭连接失败（下载文件）：" + e.getMessage());
        }
    }

    //下载云服务器的文件（因为云服务器传文件是一段一段的，所以不能直接像操作我们的服务器一样直接下载）（阿里云为例）
    public void downloadCloudServerFile(String remoteFileAbsolutePath, String fileName, HttpServletResponse response) {
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            //获取输入流
            InputStream inputStream = channelSftp.get(remoteFileAbsolutePath);

            //阿里云应该是断点续传，后面研究……

        }catch(Exception e){
            throw new RuntimeException("关闭连接失败（下载文件）：" + e.getMessage());
        }
    }

    //ls命令：获取文件夹的信息
    public String ls(String path){
        StringBuilder sb = new StringBuilder();
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            Vector ls = channelSftp.ls(path);
            Iterator iterator = ls.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                System.out.println(next);
                sb.append(next);
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return sb.toString();
    }

    //关闭通道：释放资源
    private void closeChannel(){
        //不为空，且已经连接：关闭
        if (Objects.nonNull(cmdExec)) {
            cmdExec.disconnect();
        }
        if (Objects.nonNull(channelSftp)) {
            channelSftp.disconnect();
        }
        if (Objects.nonNull(cmdShell)) {
            cmdShell.disconnect();
        }
    }

    /** 关闭通道、关闭会话：释放资源
     * spring销毁前，关闭 所有会话 及 所有通道
     */
    @PreDestroy
    public void closeAll(){
        System.out.println("我被销毁了!");

        this.closeChannel();

        if (Objects.nonNull(session) && session.isConnected()) {
            session.disconnect();
        }
    }
}
