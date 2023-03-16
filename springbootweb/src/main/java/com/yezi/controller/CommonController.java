package com.yezi.controller;

import com.yezi.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
//    文件上传
    @PostMapping("/upload")
    public R<String> fileUpload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+suffix;
        try {
            file.transferTo(new File("D:\\"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    };
//    文件下载
    @GetMapping("/download")
    public R<String> fileDownload(String name, HttpServletResponse response){
        try {
            if(name == ""){
                return R.error("失败");
            }
            FileInputStream fileInputStream  = new FileInputStream(new File("D:\\img\\"+name));
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }
            outputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success("下载成功");
    }
}
