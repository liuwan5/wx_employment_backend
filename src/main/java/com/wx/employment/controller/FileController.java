package com.wx.employment.controller;

import com.wx.employment.common.Result;
import com.wx.employment.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    FileService fileService;

    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file){
        return fileService.uploadFile(file);
    }

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
}
