package com.wx.employment.service.impl;

import com.wx.employment.common.Result;
import com.wx.employment.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public Result uploadFile(MultipartFile file){
        String path = "/root/images/";
        if(!file.isEmpty()){
            String filename = file.getOriginalFilename();
            try {
                file.transferTo(new File(path+filename));
            } catch (IOException e) {
                return Result.executeFailure("上传失败");
            }
        }
        return Result.uploadSuccess("http://47.95.14.207:18888/"+file.getOriginalFilename());
    }
}
