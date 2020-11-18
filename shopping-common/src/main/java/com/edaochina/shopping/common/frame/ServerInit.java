package com.edaochina.shopping.common.frame;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import com.edaochina.shopping.common.utils.idutil.IdUtils;
import com.edaochina.shopping.common.utils.ip.IPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 服务初始化
 *
 * @author jintian
 * @date 2019/8/19 11:59
 */
@Component
public class ServerInit {

    private static String SERVER_IP_SORT = "SERVER_IP_SORT";

    /**
     * 初始化生成id工具类的workId
     */
    public static void initWorkId() {
        String serverIpSort = RedisTool.get(SERVER_IP_SORT);
        String ip = IPUtils.getLocalIp();
        Long sort = 0L;
        if (StringUtils.isNotBlank(serverIpSort)) {
            JSONObject jsonObject = JSONObject.parseObject(serverIpSort);
            sort = jsonObject.getLong(ip);
            if (sort == null) {
                sort = jsonObject.getLong("sort") + 1;
                jsonObject.put("sort", sort);
                jsonObject.put(ip, sort);
                RedisTool.set(SERVER_IP_SORT, jsonObject.toJSONString());
            } else if (sort > 15) {
                // 超过15直接重置掉
                sort = 0L;
            }
        } else {
            JSONObject json = new JSONObject();
            json.put(ip, sort);
            json.put("sort", sort);
            RedisTool.set(SERVER_IP_SORT, json.toJSONString());
        }
        if (sort / 32 > 0) {
            IdUtils.setDataCenterId(sort / 32);
            sort = sort - (sort / 32) * 32;
        }
        IdUtils.setWorkId(sort);
    }
}
