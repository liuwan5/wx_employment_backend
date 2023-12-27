package com.wx.employment.service;

import com.wx.employment.common.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Result uploadFile(MultipartFile file);

}
