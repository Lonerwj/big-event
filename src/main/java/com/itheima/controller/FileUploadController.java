package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString()+originalFileName.substring(originalFileName.lastIndexOf("."));
        String url = AliOssUtil.uploadFile(fileName,file.getInputStream());
        return Result.success(url);
    }
}
