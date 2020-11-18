package com.edaochina.shopping.domain.constants;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @BelongsProject: customize
 * @BelongsPackage: com.edaochina.shopping.domain.constants
 * @Author: Jason
 * @CreateTime: 2018-11
 * @Description: ${Description}
 */
public interface RedisConstants {
    /**
     * JWT token 前缀 cust
     */
    String CUST_TOKEN = "JWT:TOKEN:CUST:";

    /**
     * JWT token 前缀 measuring
     */
    String MEASURING_TOKEN = "JWT:TOKEN:MEASURING:";
    /**
     * JWT token 前缀 sys
     */
    String SYS_TOKEN = "CD:TOKEN:SYS_";

    /**
     * 刷新 token 前缀 cust
     */
    String CUST_REFRESH_TOKEN = "REFRESH:TOKEN:CUST:";

    /**
     * 刷新 token 前缀 measuring
     */
    String MEASURING_REFRESH_TOKEN = "REFRESH:TOKEN:MEASURING:";

    /**
     * 刷新 token 前缀 sys
     */
    String SYS_REFRESH_TOKEN = "REFRESH:TOKEN:SYS:";

    /**
     * RefreshToken过期时间 30天
     */
    long REFRESH_TOKEN_EXPIRE = 3600 * 24 * 30L;

    /**
     * jwt过期时间 两小时
     */
    long JWT_EXPIRE = 60 * 60 * 2L;

    /**
     * role 前缀
     */
    String ROLE = "CD:ROLE:MENU_";

    /**
     * 微信token
     */
    String WX_TOKEN = "WX:TOKEN:";

    /**
     * 微信access_token
     */
    String WX_ACCESS_TOKEN = "WX:ACCESS_TOKEN:";

    /**
     * 短信验证码
     */
    String REGISTER_CODE = "CODE:PHONE:";

    /**
     * 地址下拉
     */
    String ADDRESS = "COMMON:ADDRESS";

    /**
     * 每分钟发验证码上限数量
     */
    long CODE_MINUTES_NUM = 2;
    /**
     * 每小时发验证码上限数量
     */
    long CODE_HOUR_NUM = 5;
    /**
     * 每天发验证码上限数量
     */
    long CODE_DAY_NUM = 10;

    /**
     * 每分钟发验证码次数（存redis）
     */
    String CODE_MINUTES_COUNT = "COUNT:PHONE:MINUTES:";
    /**
     * 每小时发验证码次数（存redis）
     */
    String CODE_HOUR_COUNT = "COUNT:PHONE:HOUR:";
    /**
     * 每天发验证码次数（存redis）
     */
    String CODE_DAY_COUNT = "COUNT:PHONE:DAY:";
    /**
     * 进行商品距离排序
     */
    String GOODS_DISTANCE = "GOODS:DISTANCE:";
    /**
     * 商品提示
     */
    String GOODS_TIPS = "GOODS:TIPS:";
    /**
     * 商品图片
     */
    String GOODS_IMAGES = "GOODS:IMAGES:";

    /**
     * app商品列表
     */
    String APP_GOODS_LIST = "APP:GOODS:LIST:";
}
