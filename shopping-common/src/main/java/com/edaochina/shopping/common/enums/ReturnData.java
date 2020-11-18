package com.edaochina.shopping.common.enums;


/**
 * 请求返回码
 * 00开头的和3位数字的编码为系统相关
 * 10阿里云通信相关
 * 11开头商品相关的
 * 12开头的订单相关的
 * 13开头的用户信息相关的
 * 14开头的交易相关的
 */
public enum ReturnData {

    /**
     * 00开头的和3位数字的编码为系统相关
     */
    SUCCESS(200, "请求成功"),
    REGISTER_SUCCESS(200, "注册成功"),
    BUSSISING(201, "系统繁忙请稍后重试"),
    FAIL(555, "请求失败"),
    WRONG_INPUT(400, "输入错误"),
    TYPE_MISMATCH(400, "参数类型不匹配"),
    MISSING_SERVLET_REQUEST_PARAMETER(400, "请求参数缺失"),
    ERROR_SERVLET_REQUEST_PARAMETER(400, "请求参数错误"),
    CODE_WORING(401, "验证码错误或已过期"),
    LOGIN_DENY(402, "账号已被禁用"),
    CODE_NOT_SEND(403, "验证码发送失败！"),
    NOT_FIND_ACTION(404, "未找到Api"),
    NOT_USER(405, "未找到该用户"),
    USER_WITHOUT_MENU(406, "未分配角色菜单"),
    NOT_MENU(407, "未找到菜单"),
    GENERATE_USER_ERROR(408, "创建用户失败"),
    ERROR_CODE(418, "无效的微信code"),
    WX_ALREARY_IN_USER(419, "微信已经被使用"),
    WX_UNBAND(420, "微信没有绑定手机号码"),
    USERNAME_EXIST(421, "用户名已存在"),
    ACCOUNT_OR_PASSWORD_WRONG(422, "账号或密码错误"),
    TOO_MUCH_LOGIN(423, "登录频繁,已被禁止登录20分钟"),
    ERROR_GET_TOKEN(424, "获取token失败"),
    SYSTEM_ERROR(501, "系统错误"),
    SYSTEM_BUSY(503, "系统繁忙"),
    INVALID_SESSION(561, "无效的session"),
    UNAUTHORIZED(582, "未授权"),
    INVALID_PRIVILEGE(583, "无效的权限"),
    SYSTEM_BIZ_LIMIT(584, "业务受限"),
    UPLOAD_FAIL(585, "上传失败"),
    LOGIN_EXPIRED(600, "登录过期"),
    REFLECT_ERROR(700, "实例化异常"),
    DATE_ERROR(800, "错误的时间格式"),
    UPLOAD_ERROR(801, "上传失败"),
    NO_LOGIN_ERROR(802, "请先登录"),
    NOTICE_ONLY_ONE_SHOW(803, "通知信息只有一条显示"),
    OVER_ONE_USER(804, "该手机号被多次绑定，请联系管理人员确认"),
    USER_IS_CREATING(805, "该微信号正在生产用户中"),
    PAGE_OVER_TIME(806, "页面已过期"),
    HAS_HANDLERED(807, "已处理过"),

    /**
     * 用户注册登录返回码
     */
    LOGIN_WITHOUT_PHONE(901, "没有绑定手机号码"),
    LOGIN_WITHOUT_WX(902, "没有绑定微信"),
    CREATE_SYS_USER_ROLE_FAIL(903, "创建系统用户角色失败"),
    MD5_ERROR(904, "加密异常"),
    SUPER_MANAGER_ERROR(905, "无法对超级管理员及其角色进行修改"),
    BASE_CUST_MEMBER_NOT_DELETE(906, "基础会员等级不可删除"),
    EXCEL_DATA_ERROR(907, "excel表格中存在重复的账户"),
    UPDATE_SCORE_ERROR(908, "积分更新失败"),
    IDENTITY_NO_ERROR(909, "身份证号不正确"),
    PHONE_ERROR(910, "手机号不正确"),
    SPECIAL_USER_EXIT(911, "提现特殊用户已存在"),
    UPDATE_PASSWOED_ACCOUNT_NOT_EXIST(912, "修改密码的账号不存在"),

    /**
     * 10阿里云通信
     */
    OK(1000, "OK"),
    // phone error
    ERROR_PHONE_NUM(1050, "非法手机号"),
    SEND_ERROR(1051, "短信发送失败"),
    PHONE_USED(1052, "账号已被注册"),
    PHONE_NEVER_USED(1053, "手机号码未注册"),
    ERROR_TEMPLATE_CODE(1054, "未知的短信模板"),
    ERROR_PHONE_SIZE(1055, "超出发送数量限制"),
    NULL_PHONE_SIZE(1056, "发送手机号码为空"),
    PHONE_CODE_BUSY(1057, "发送验证码过于频繁，请稍后再试"),


    /**
     * 11商品请求返回码
     */
    PRODUCTTYPE_NOEXIST(1101, "未找到该商品类型"),
    PRODUCTCOLOR_NOEXIST(1102, "未找到该商品颜色"),
    PRODUCTCOLOR_EXIST(1103, "该商品颜色已存在"),
    PRODUCTMATERIA_EXIST(1104, "该商品材料已存在"),
    PRODUCTMATERIA_NOEXIST(1105, "未找到该商品材料"),
    PRODUCTPART_EXIST(1106, "该订制部位已存在,名称或编码重复"),
    PRODUCTPART_NOEXIST(1107, "未找到该订制部位"),
    PRODUCTPARTCOF_EXIST(1108, "该订制部位值已存在"),
    PRODUCTSTYLE_NOEXIST(1109, "未找到该商品款式"),
    PRODUCTSTYLE_EXIST(1110, "该商品款式已存在"),
    FILE_IMPORT_ERROR(1111, "文件导入失败,请按照模板要求导入"),
    PRODUCTMATERIA_REPEAT(1112, "表格中存在重复材料"),
    BODYPART_REPEAT(1113, "表格中存在重复量体部位"),
    ADD_SHOPSPRODUCT_ERROR(1114, "添加商品失败"),
    SHOPSPRODUCT_NOEXIST(1115, "未找到该商品"),
    GET_SHOPSPRODUCT_ERROR(1116, "查询商品列表失败"),
    DEL_SHOPSPRODUCT_ERROR(1117, "删除商品列表失败"),
    DEL_TYPE_ERROR(1118, "删除商品类型失败，已有款式关联"),
    DEL_STYLE_ERROR(1119, "删除商品款式失败，已有商品关联"),
    DEL_COLOR_ERROR(1120, "删除商品颜色失败，已在使用该颜色"),
    DEL_MATER_ERROR(1121, "删除商品材料失败，已在使用该材料"),
    DEL_PART_ERROR(1122, "删除订制部位失败，已在使用该部位"),
    DEL_PARTCOF_ERROR(1123, "删除订制部位值失败，已在使用该部位值"),
    DEL_BODYPART_ERROR(1124, "删除量体部位失败，已在使用该部位"),
    GOODS_TYPE_PARAM_EMPTY(1125, "商品类型参数缺失"),
    GOODS_PARAM_EMPTY(1126, "商品参数缺失"),
    GOODS_IS_NOT_EXIST(1127, "该商品不存在或者已经失效"),
    GOODS_NUM_IS_NOT_ENOUGH(1128, "商品库存不足"),
    SUBSTRACT_GOODS_NUM_FAIL(1129, "商品去库存失败,请重新下单"),
    OVER_GOODS_STICK_NUM(1130, "超过小区商品置顶数量限制"),
    NO_COMMUNITY(1131, "添加商品商家无小区信息"),
    GOODS_HAS_DEL(1132, "该商品已下架"),
    OVER_GOODS_NUM(1133, "库存不足"),
    COLLECT_GOODS_CANNOT_EDIT(1134, "拼团中商品不允许修改"),
    UNSELECTED_COMMUNITY(1135, "请先选择小区"),
    GOODS_MERCHANT_NOT_EXIT(1135, "添加商品的商家id不存在"),
    GOODS_MERCHANT_NOT_BELONG_TO(1136, "添加商品的商家id不属于该代理商"),
    MISS_COLLECT_INFO(1137, "审核的拼团商品缺少拼团信息，请先完善"),
    COLLECT_GOODS_HAS_END(1138, "该商品拼团已结束，不允许修改其拼团信息"),

    /**
     * 12订单请求返回码
     */
    ORDER_PARAM_NOT_EMPTY(1201, "订单参数不能为空"),
    ORDER_NOT_RIGHT(1202, "请选择同一个人的商品"),
    ORDER_TYPE_NOT_RIGHT(1203, "请选择同一个类型的商品"),
    FACTORY_INSERT_ERROR(1204, "工厂端单子插入失败"),
    TYAP_AND_STYLE_ERROR(1205, "请输入正确的款式"),
    DO_NOT_USE_SCORE(1206, "等级不够无法使用积分"),
    SINGLE_PRODUCT_NOT_RIGHT(1207, "积分超过当前上限了"),
    SCORE_NOT_RIGHT(1208, "请输入正确的积分"),
    SCORE_DOWN(1209, "低于积分下限"),
    SCORE_UP(1210, "请输入正确的积分"),
    STYLE_IS_EMPTY(1211, "商品款式不能为空"),
    CUSTMEMBER_IS_EMPTY(1212, "会员资料不存在"),
    ORDER_PARAM_EMPTY(1213, "订单参数缺失"),
    ORDER_PRICE_ERROR(1214, "订单金额不对"),
    PAY_GOODS_IS_NOT_EXIT(1215, "支付的订单商品不存在"),
    SHOPPING_NULL_ERROR(1216, "您选择支付的购物车为空，请重新选择"),
    ERROR_ORDER_EXIST(1217, "异常订单已存在"),
    ERROR_ORDER_REASON_ERROR(1219, "只支持输入文字字符"),
    ADD_SHOPPING_CART_ERROR(1218, "添加失败，购物车已满"),
    REFUND_HAS_HANDLER(1219, "退款订单已处理完成，已无法撤销，请耐心等待"),
    REFUND_CANNOT_REFUSE(1220, "退款订单已被处理，不允许拒绝退款"),
    REFUND_CANNOT_AGREE(1220, "退款订单已被处理，不允许同意退款"),
    WRITE_OFF_TIME_NOT_VALID(1221, "核销时间不在规定时间段内"),
    HAS_BUY_THREE_TIME(1222, "您已享受了2次优惠，如需购买则需购买会员"),

    /**
     * 13用户信息
     */
    UPD_USER_ERROR(1301, "修改用户失败"),
    USER_NOT_EXIST(1302, "用户不存在"),
    PHONE_HAS_BINDING(1303, "该手机号已被绑定"),
    CITY_AGENT_EXIST(1304, "该县已存在代理商,不能重复添加"),
    CITY_AGENT_NOT_EXIST(1305, "该县不存在代理商,请添加后重试"),
    USER_MEMBR_ERROR(1307, "用户已支付过会员费,请稍等"),
    BALANCE_MONEY_NOT_ENOUGH(1308, "账户余额小于提现金额和手续费"),
    MERCHANT_CHECK_STATUS_ERROR(1309, "用户未审核不允许提现"),
    WITHDRAWAL_APPLY_ALREADY_EXIST(1310, "当前账户下存在未处理的提现申请，请勿频繁提交"),
    CART_USER_ID_ERROR(1311, "当前用户与购物车用户不符"),
    ADD_COMM_ERROR(1312, "创建小区失败"),
    COMM_NOT_EXIT_ERROR(1313, "小区不存在"),
    UPDATE_COMM_ERROR(1314, "小区已绑定可用商户，不可删除也不可修改区县信息"),
    WITHDRAWAL_MONEY_ERROR(1315, "提现金额要大于0"),
    CART_HAS_DEL(1316, "购物车已失效，请重新选购"),
    IDENTITY_CODE_ERROR(1317, "请确认邀请码正确，或入驻的区县无该群社合伙人"),


    /**
     * 14交易信息
     */

    OUT_OF_TEAM_NUM(1401, "超出成团人数，无法拼单"),

    LIMIT_BUY_NUM(1402, "已经限购，超出限购数量"),

    NO_JOIN_TEAM(1403, "您已经拼过团，无法再次拼团"),

    TEAM_IS_DELETE(1404, "该团已经不存在，无法参与拼单"),

    ORDER_IS_NOT_RIGHT(1405, "发起拼单人订单信息不正确，无法参与拼单"),

    OUT_OF_TIME(1406, "开团时间已经超过了24小时，无法参加拼团"),

    TIME_ERROR(1407, "拼团失败，请重新拼团"),

    USER_NOT_MEMBER(1408, "当前用户不是会员,不支持购物车支付"),

    REFUND_ERROR(1409, "已提货不可退款"),

    WITHDRAW_ERROR(1410, "该账号不可提现"),

    REFUND_HAS_ERROR(1411, "该订单已有退款或者该订单退款已被拒绝"),

    /**
     * 15法大大的
     */
    CREATE_CONTRACT_FAIL(1502, "生成合同文件失败，请重试");


    private int code;
    private String msg;

    ReturnData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
