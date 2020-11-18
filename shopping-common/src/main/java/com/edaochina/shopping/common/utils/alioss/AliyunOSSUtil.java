package com.edaochina.shopping.common.utils.alioss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云OSS工具 Created by hanley on 2016/7/1.
 */
@UtilityClass
public class AliyunOSSUtil {

    private static String endPoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAICMwGCOilPdvp";
    private static String accessKeySecret = "uExR2qdvyhoruBScYByzcnsxIqNRfF";
    public static String imgUrl = "http://cd-shopping.oss-cn-hangzhou.aliyuncs.com";
    private static String bucketName = "cd-shopping";
    private static final OSSClient OSS_CLIENT = new OSSClient(endPoint, accessKeyId, accessKeySecret);

    public static String getBucketName() {
        return bucketName;
    }

    public static void setBucketName(String bucketName) {
        AliyunOSSUtil.bucketName = bucketName;
    }

    public static OSSClient getInstance() {
        return OSS_CLIENT;
    }

    /**
     * 上传资源到OSS指定路径下
     *
     * @param input 资源输入流对象
     * @param key   资源key, 包含路径+文件名
     * @param size  该文件的大小
     * @throws Exception
     */
    public static void uploadFile(InputStream input, String key, long size) throws Exception {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(size);
            getInstance().putObject(bucketName, key, input, meta);
        } finally {
            if (input != null) {
                input.close();
            }
        }

    }

    /**
     * 通过资源key删除该资源
     *
     * @param key 资源key, 包含路径+文件名
     */
    public static void deleteFile(String key) {
        getInstance().deleteObject(bucketName, key);
    }

    /**
     * 获取OSS路径下的所有资源的key的集合
     *
     * @param prefix 路径
     * @return
     */
    public static List<String> listFile(String prefix) {
        List<String> list = new ArrayList<String>();
        ObjectListing olist = getInstance().listObjects(bucketName, prefix);
        // 遍历所有Object
        for (OSSObjectSummary objectSummary : olist.getObjectSummaries()) {
            list.add(objectSummary.getKey());
        }
        return list;
    }

    /**
     * 从OSS中下载文件
     *
     * @param key          资源key, 包含路径+文件名
     * @param downloadPath 下载文件存放路径
     * @throws IOException
     */
    public static void download(String key, String downloadPath) throws IOException {
        OSSObject object = getInstance().getObject(bucketName, key);
        InputStream input = null;
        FileOutputStream out = null;
        try {
            input = object.getObjectContent();
            File file = new File(downloadPath);
            if (file.exists()) {
                file.delete();
            }
            out = new FileOutputStream(file);
            int i = 1;
            while (i >= 0) {
                i = input.read();
                out.write(i);
            }
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * 判断上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return
     */
    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals("BMP") || FilenameExtension.equals("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals("GIF") || FilenameExtension.equals("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals("JPEG") || FilenameExtension.equals("jpeg") || FilenameExtension.equals("JPG")
                || FilenameExtension.equals("jpg") || FilenameExtension.equals("PNG")
                || FilenameExtension.equals("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals("HTML") || FilenameExtension.equals("html")) {
            return "text/html";
        }
        if (FilenameExtension.equals("TXT") || FilenameExtension.equals("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals("VSD") || FilenameExtension.equals("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals("PPTX") || FilenameExtension.equals("pptx") || FilenameExtension.equals("PPT")
                || FilenameExtension.equals("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals("DOCX") || FilenameExtension.equals("docx") || FilenameExtension.equals("DOC")
                || FilenameExtension.equals("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals("XML") || FilenameExtension.equals("xml")) {
            return "text/xml";
        }
        return "text/html";
    }

}