package com.edaochina.shopping.api.service.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.service.user.UserService;
import com.edaochina.shopping.common.wxpay.config.WxPayConfig;
import com.edaochina.shopping.common.wxtencent.util.WxTencentConnectionUtil;
import com.edaochina.shopping.domain.entity.order.OrderMain;
import com.edaochina.shopping.domain.entity.trade.PayRefundApply;
import com.edaochina.shopping.domain.entity.user.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 微信通知消息服务类
 *
 * @author jintian
 * @date 2019/7/31 18:23
 */
@Service
public class WxTencentService {

    @Autowired
    private UserService userService;

    public void sendPayRefundMsg(PayRefundApply refundApply) {
        // 事物提交后处理打款请求
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 获取退款用户信息
                SysUser user = userService.getById(refundApply.getUserId());
                if (StringUtils.isBlank(user.getTencentOpenId())) {
                    return;
                }
                // 发送退款消息给用户
                JSONObject req = new JSONObject();
                req.put("touser", user.getTencentOpenId());
                req.put("template_id", "-Blh7NtGg51cU0HhPg5wokGFAjU0SpvgA98UXbs4FZk");
                req.put("appid", WxPayConfig.getAppId());
                // TODO 拼装数据
                String data = "";
                req.put("data", data);
                WxTencentConnectionUtil.sendMsg(req);
            }
        });
    }


    public void sendConnectSuccessMsg(OrderMain orderMain) {
        // 事物提交后处理打款请求
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 获取退款用户信息
                SysUser user = userService.getById(orderMain.getUserId());
                if (StringUtils.isBlank(user.getTencentOpenId())) {
                    return;
                }
                // 发送退款消息给用户
                JSONObject req = new JSONObject();
                req.put("touser", user.getTencentOpenId());
                req.put("template_id", "HpCdBgei3KeSCRRrTDq42U39QBN-lhFwFfRyTaR2JYA");
                req.put("appid", WxPayConfig.getAppId());
                // TODO 拼装数据
                String data = "";
                req.put("data", data);
                WxTencentConnectionUtil.sendMsg(req);
            }
        });
    }


    public void sendConnectFailMsg(OrderMain orderMain) {
        // 事物提交后处理打款请求
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 获取退款用户信息
                SysUser user = userService.getById(orderMain.getUserId());
                if (StringUtils.isBlank(user.getTencentOpenId())) {
                    return;
                }
                // 发送退款消息给用户
                JSONObject req = new JSONObject();
                req.put("touser", user.getTencentOpenId());
                req.put("template_id", "k7-I9ORjWhMcBhs0qL6q8Sb58b5okEvyRipr4Ijjwnk");
                req.put("appid", WxPayConfig.getAppId());
                // TODO 拼装数据
                String data = "";
                req.put("data", data);
                WxTencentConnectionUtil.sendMsg(req);
            }
        });
    }

    private void sendMsg(JSONObject req) {
        // 事物提交后处理打款请求
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                WxTencentConnectionUtil.sendMsg(req);
            }
        });
    }

    public void updateUsersTencentOpenId() {
        String nextOpenId = "";
        do {
            JSONObject jsonObject = WxTencentConnectionUtil.getTencentUsers(nextOpenId);
            nextOpenId = jsonObject.getString("next_openid");
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray openIds = data.getJSONArray("openid");
            String openId = "";
            for (int i = 0; i < openIds.size(); i++) {
                openId = openIds.get(i).toString();
                updateUserTencentOpenId(openId);
            }
        } while (StringUtils.isNotBlank(nextOpenId));
    }


    public void updateUserTencentOpenId(String tencentOpenId) {
        String uid = WxTencentConnectionUtil.getUid(tencentOpenId);
        userService.updateUserTencetOpenIdByUid(tencentOpenId, uid);
    }
}
