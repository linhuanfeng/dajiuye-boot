package com.lhf.dajiuye.service.user.impl;

//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.model.OSSObject;
//import com.aliyun.oss.model.ObjectMetadata;
//import com.lhf.dajiuye.api.bean.StatusCode;
//import com.lhf.dajiuye.api.service.user.OssFileService;
//import com.lhf.dajiuye.web.app.config.AliYunOssConfig;
//import com.lhf.dajiuye.web.app.utils.FileNameUtil;
import com.lhf.dajiuye.service.user.OssFileService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * dubbo不适合大文件或大报文按传输
 *  因为是单连接轮询处理channel,会导致netty写线程阻塞太久，并且一次传输完成，内存放不下那么多
 *  feign是httpClient，每次传输很少所以不会内存问题
 */
@Service
public class OssFileServiceImpl implements OssFileService {
    // 允许上传文件(图片)的格式
    private static final String[] IMAGE_TYPE = new String[]{".pdf",".jpg",".png",".jpeg"};

//    @Autowired
//    private OSS ossClient;// 注入阿里云oss文件服务器客户端
//    @Autowired
//    private AliYunOssConfig aliyunOssConfig;// 注入阿里云OSS基本配置类

    @Override
    public String upload(MultipartFile uploadFile,String fileName) {
        // 获取oss的Bucket名称
//        String bucketName = aliyunOssConfig.getBucketName();
//        // 获取oss的地域节点
//        String endpoint = aliyunOssConfig.getEndPoint();
//        // 获取oss的AccessKeySecret
//        String accessKeySecret = aliyunOssConfig.getAccessKeySecret();
//        // 获取oss的AccessKeyId
//        String accessKeyId = aliyunOssConfig.getAccessKeyId();
//        // 获取oss目标文件夹
//        String filehost = aliyunOssConfig.getFileHost();
        // 返回图片上传后返回的url
//        String returnImgeUrl = "";

        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
//        if (!isLegal) {// 如果图片格式不合法
//            return StatusCode.ERROR.getMsg();
//        }

        // 获取文件原名称
        String originalFilename = uploadFile.getOriginalFilename();

        // 生产唯一名字，防止覆盖
//        String imgName = FileNameUtil.getRandomImgName(fileName);

//        // 获取文件类型
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
//        // 新文件名称
//        String newFileName = UUID.randomUUID().toString() + fileType;
//        // 构建日期路径, 例如：OSS目标文件夹/2020/10/31/文件名
//        String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
//        // 文件上传的路径地址
//        String uploadImgeUrl = filehost + "/" + filePath + "/" + newFileName;

        // 获取文件输入流
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 下面两行代码是重点坑：
         * 现在阿里云OSS 默认图片上传ContentType是image/jpeg
         * 也就是说，获取图片链接后，图片是下载链接，而并非在线浏览链接，
         * 因此，这里在上传的时候要解决ContentType的问题，将其改为image/jpg
         */
//        ObjectMetadata meta = new ObjectMetadata();
//        if("pdf".equals(fileType)){
//            meta.setContentType("application/pdf");
//        }else if("png".equals(fileType)){
//            meta.setContentType("image/jpg");
//        }else if("jpg".equals(fileType)||"jpeg".equals(fileType)){
//            meta.setContentType("image/jpeg");
//        }

        //文件上传至阿里云OSS
//        ossClient.putObject(bucketName, imgName, inputStream, meta);

        // 获取文件上传后的图片返回地址
//        imgName = "http://" + bucketName + "." + endpoint + "/" + imgName;

        return "imgName";
    }
    /*
     * 文件下载
     * @param: fileName
     * @param: outputStream
     * @return: void
     * @create: 2020/10/31 16:19
     * @author: csp1999
     */
    @Override
    public String download(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
//        // 设置响应头为下载
//        response.setContentType("application/x-download");
//        // 设置下载的文件名
//        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
//        response.setCharacterEncoding("UTF-8");
        // 文件名以附件的形式下载
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

//        // 获取oss的Bucket名称
//        String bucketName = aliyunOssConfig.getBucketName();
//        // 获取oss目标文件夹
//        String filehost = aliyunOssConfig.getFileHost();
        // 日期目录
        // 注意，这里虽然写成这种固定获取日期目录的形式，逻辑上确实存在问题，但是实际上，filePath的日期目录应该是从数据库查询的
        String filePath = new DateTime().toString("yyyy/MM/dd");

//        String fileKey = filehost + "/" + filePath + "/" + fileName;
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
//        OSSObject ossObject = ossClient.getObject(bucketName, fileKey);
//        try {
            // 读取文件内容。
//            InputStream inputStream = ossObject.getObjectContent();
//            BufferedInputStream in = new BufferedInputStream(inputStream);// 把输入流放入缓存流
//            ServletOutputStream outputStream = response.getOutputStream();
//            BufferedOutputStream out = new BufferedOutputStream(outputStream);// 把输出流放入缓存流
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = in.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//            }
//            if (out != null) {
//                out.flush();
//                out.close();
//            }
//            if (in != null) {
//                in.close();
//            }
//            return StatusCode.SUCCESS.getMsg();
//        } catch (Exception e) {
//            return StatusCode.ERROR.getMsg();
//        }
            return "";
    }

    /*
     * 文件删除
     * @param: objectName
     * @return: java.lang.String
     * @create: 2020/10/31 16:50
     * @author: csp1999
     */
    @Override
    public String delete(String fileName) {
        return "";
//        }
//        // 获取oss的Bucket名称
//        String bucketName = aliyunOssConfig.getBucketName();
//        // 获取oss的地域节点
//        String endpoint = aliyunOssConfig.getEndPoint();
//        // 获取oss的AccessKeySecret
//        String accessKeySecret = aliyunOssConfig.getAccessKeySecret();
//        // 获取oss的AccessKeyId
//        String accessKeyId = aliyunOssConfig.getAccessKeyId();
//        // 获取oss目标文件夹
//        String filehost = aliyunOssConfig.getFileHost();
//        // 日期目录
//        // 注意，这里虽然写成这种固定获取日期目录的形式，逻辑上确实存在问题，但是实际上，filePath的日期目录应该是从数据库查询的
//        String filePath = new DateTime().toString("yyyy/MM/dd");
//
//        try {
//            /**
//             * 注意：在实际项目中，不需要删除OSS文件服务器中的文件，
//             * 只需要删除数据库存储的文件路径即可！
//             */
//            // 建议在方法中创建OSSClient 而不是使用@Bean注入，不然容易出现Connection pool shut down
//            OSSClient ossClient = new OSSClient(endpoint,
//                    accessKeyId, accessKeySecret);
//            // 根据BucketName,filetName删除文件
//            // 删除目录中的文件，如果是最后一个文件fileoath目录会被删除。
//            String fileKey = filehost + "/" + filePath + "/" + fileName;
//            ossClient.deleteObject(bucketName, fileKey);
//
//            try {
//            } finally {
//                ossClient.shutdown();
//            }
//            System.out.println("文件删除！");
//            return StatusCode.SUCCESS.getMsg();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return StatusCode.ERROR.getMsg();
//        }
    }
}
