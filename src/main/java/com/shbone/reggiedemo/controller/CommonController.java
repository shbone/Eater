package com.shbone.reggiedemo.controller;

import com.shbone.reggiedemo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author sunhb
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //file是临时文件，需要转存到指定位置
        log.info("file:{}",file.toString());

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //创建目录
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String  fileName = basePath+UUID.randomUUID().toString()+suffix;
        try {
            file.transferTo(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * 下载文件
     * @param name
     * @param response
     * @return
     */
    @RequestMapping("/download")
    public R<String> downloadFile(byte[] name, HttpServletResponse response){
        String fileName = null;
        try {
            fileName = URLDecoder.decode(new String(name),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("文件名字:{}",fileName);
        //输入流，通过输入流读取文件
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
        //输出流，通过输出流上传文件
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            byte[] bytes = new byte[1024];
            int len;
            while ( (len =fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
