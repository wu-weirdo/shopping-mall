package com.edaochina.shopping.api.service.sys;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zzk
 * @since 2018-11
 */
public interface CommonService {
    /**
     * 图片上传
     *
     * @param file 文件
     * @return 路径
     */
    String uploadImage(MultipartFile file);

    String appUploadImage(String fileUrl);

    String getMerchantQrImage(String merchantId);

    String getMerchantQrOnlyImage(String merchantId);

    String getGoodsQrImage(String goodsId);
}
