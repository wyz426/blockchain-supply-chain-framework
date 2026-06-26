package com.blc.scm.pass.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.blc.scm.pass.exception.CustomError;
import com.blc.scm.pass.exception.LocalRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Elaine Huang
 * @date 2024/4/1 9:37 PM
 * @signature Do it while you can!
 */
@Component
public class ImageUtil {
    private static final long MAX_SIZE = 3145728;  //3MB

    //注入OSS密钥等配置信息
    @Value("${aliyun.oss.accesskey}")
    private String accessKey;
    @Value("${aliyun.oss.secretkey}")
    private String secretKey;
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    public String uploadFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        //判断文件类型是否符合要求
        checkFileType(contentType);
        //判断文件大小是否符合要求
        checkFileSize(file);
        String fileName = file.getOriginalFilename();
        //创建OSS客户端实例
        OSS oss = new OSSClientBuilder().build(endpoint, accessKey, secretKey);
        //上传文件到OSS，文件通过文件名称标识
        oss.putObject("blc-scm", fileName, new ByteArrayInputStream(file.getBytes()));
        //关闭OSS客户端实例
        oss.shutdown();
        //文件的访问链接
        String url = "https://blc-scm.oss-cn-shanghai.aliyuncs.com/" + fileName;
        return url;
    }

    private void checkFileSize(MultipartFile file) {
        if (file.getSize() > MAX_SIZE) {
            throw new LocalRuntimeException(CustomError.PIC_SIZE_EXCEED);
        }
    }

    private void checkFileType(String contentType) {
        if (!("image/jpeg").equals(contentType) && !("image/png").equals(contentType) && !("image/jpg").equals(contentType)) {
            throw new LocalRuntimeException(CustomError.IMAGE_TYPE_ERROR);
        }
    }
}
