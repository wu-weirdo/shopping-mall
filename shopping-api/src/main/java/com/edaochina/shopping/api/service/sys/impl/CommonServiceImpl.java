package com.edaochina.shopping.api.service.sys.impl;


import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.common.enums.ReturnData;
import com.edaochina.shopping.common.exception.YidaoException;
import com.edaochina.shopping.common.utils.LoggerUtils;
import com.edaochina.shopping.common.utils.alioss.AliyunOSSUtil;
import com.edaochina.shopping.common.utils.date.DateUtil;
import com.edaochina.shopping.common.utils.date.LocalDateUtil;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.weixinpay.HttpUtils;
import com.edaochina.shopping.common.wxpay.util.WXConnectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zzk
 * @since 2018-11
 */
@Service
public class CommonServiceImpl implements CommonService {

    private static String MERCHANT_ORDER_QR = "pages/index/index";

    private static String GOODS_QR = "pages/index/goodsDetail/goodsDetail";

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            return uploadToAliyun(fileName, inputStream);
        } catch (YidaoException e) {
            throw e;
        } catch (Exception e) {
            LoggerUtils.error(CommonServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.UPLOAD_ERROR);
        }
    }

    private String uploadToAliyun(String fileName, InputStream in) {
        try {
            if (!StringUtils.isEmpty(fileName)) {
                String[] index = fileName.split("\\.");
                if (index.length > 0) {
                    String key = LocalDateUtil.localDateTime2String(LocalDateTime.now()) + "/" + IdUtils.nextId() + "." + index[index.length - 1];
                    AliyunOSSUtil.uploadFile(in, key, in.available());
                    return AliyunOSSUtil.imgUrl + "/" + key;
                }
            }
            throw new YidaoException(ReturnData.MISSING_SERVLET_REQUEST_PARAMETER);
        } catch (Exception e) {
            LoggerUtils.error(CommonServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.UPLOAD_ERROR);
        }
    }

    /**
     * 小程序上传图片
     *
     * @param fileUrl 小程序上图片地址
     * @return 阿里云图片地址
     */
    @Override
    public String appUploadImage(String fileUrl) {
        String fileName = IdUtils.nextId() + fileUrl.substring(fileUrl.lastIndexOf("."));
        File file = HttpUtils.downloadFileByUrl(fileUrl, "/temp/" + fileName);
        try {
            InputStream inputStream = new FileInputStream(file);
            return uploadToAliyun(fileName, inputStream);
        } catch (YidaoException e) {
            throw e;
        } catch (Exception e) {
            LoggerUtils.error(CommonServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.UPLOAD_ERROR);
        } finally {
            if (file != null) {
                // 上传之后文件删除
                file.delete();
            }
        }
    }

    @Override
    public String getMerchantQrImage(String merchantId) {
        String fileName = "/temp/" + merchantId + DateUtil.formatDate(new Date()) + ".jpg";
        // 图片下载到本地
        WXConnectionUtil.getQrImage(merchantId, MERCHANT_ORDER_QR, fileName);
        File file = new File(fileName);
        try {
            InputStream inputStream = new FileInputStream(file);
            return uploadToAliyun(fileName, inputStream);
        } catch (YidaoException e) {
            throw e;
        } catch (Exception e) {
            LoggerUtils.error(CommonServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.UPLOAD_ERROR);
        } finally {
            if (file != null) {
                // 上传之后文件删除
                file.delete();
            }
        }
    }

    @Override
    public String getGoodsQrImage(String goodsId) {
        String fileName = "/temp/" + goodsId + DateUtil.formatDate(new Date()) + ".jpg";
        // 图片下载到本地
        WXConnectionUtil.getGoodsQrImage(goodsId, GOODS_QR, fileName);
        File file = new File(fileName);
        try {
            InputStream inputStream = new FileInputStream(file);
            return uploadToAliyun(fileName, inputStream);
        } catch (YidaoException e) {
            throw e;
        } catch (Exception e) {
            LoggerUtils.error(CommonServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.UPLOAD_ERROR);
        } finally {
            if (file != null) {
                // 上传之后文件删除
                file.delete();
            }
        }
    }


    public String getMerchantQrOnlyImage(String merchantId) {
        String fileName = "/temp/" + merchantId + DateUtil.formatDate(new Date()) + ".jpg";
        // 图片下载到本地
        WXConnectionUtil.getQrOnlyImage("shoperId=" + merchantId, "/pages/picking/picking", fileName);
        File file = new File(fileName);
        try {
            InputStream inputStream = new FileInputStream(file);
            return uploadToAliyun(fileName, inputStream);
        } catch (YidaoException e) {
            throw e;
        } catch (Exception e) {
            LoggerUtils.error(CommonServiceImpl.class, e.getMessage());
            throw new YidaoException(ReturnData.UPLOAD_ERROR);
        } finally {
            if (file != null) {
                // 上传之后文件删除
                file.delete();
            }
        }
    }

}
