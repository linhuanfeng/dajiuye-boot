package com.lhf.dajiuye.service.user;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface OssFileService {
    String delete(String fileName);
    String download(String fileName, HttpServletResponse response) throws UnsupportedEncodingException;
    String upload(MultipartFile uploadFile,String fileName);
}
