package com.itheima.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.InputStream;

public class AliOssUtil {
    private static final String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI5tQFctmUxmvaiNXBp4oZ";
    private static final String ACCESS_KEY_SECRET = "3bJr7RwJ3O2WTgPKR3VzcvrkmXRYd7";
    private static final String BUCKET_NAME = "eduteacher-1010";

    public static String uploadFile(String objectName, InputStream in){
        //创建一个oss实例
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT,ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        String url = "";
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME,objectName,in);
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        url = "https://"+BUCKET_NAME+"."+ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)+"/"+objectName;
        return url;
    }
}
