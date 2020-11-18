package com.edaochina.shopping.common.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2018\4\3 0003.
 */
public class HttpClientUtilsTool {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtilsTool.class); // 日志记录
    private static RequestConfig requestConfig = null;

    static {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
    }

    /**
     * post请求传输json参数
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            //请求发送成功，并得到响应
            jsonResult = getJsonObject(url, jsonResult, result);
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url    url地址
     * @param params 参数
     * @return
     */
    public static JSONObject httpPost(String url, Map<String, Object> params) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (!params.isEmpty()) {
                StringBuffer strParam = new StringBuffer();
                //获取请求参数，并组合成
                String strParams = getParams(params, strParam);

                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParams, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            jsonResult = getJsonObject(url, jsonResult, result);
            logger.info("请求参数:" + JSON.toJSONString(params));
            logger.info("返回参数:" + JSON.toJSONString(jsonResult));
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url url地址
     * @param obj 参数
     * @return
     */
    public static JSONObject httpPostForRequestBody(String url, Object obj) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        try {
            HttpPost request = new HttpPost(url);
            String json = JSONObject.toJSONString(obj);
            StringEntity params = new StringEntity(json);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            // 请求发送成功，并得到响应
            jsonResult = getJsonObject(url, jsonResult, response);
            logger.info("请求参数:" + JSON.toJSONString(obj));
            logger.info("返回参数:" + JSON.toJSONString(jsonResult));
        } catch (Exception ex) {
            logger.error("post请求提交失败:" + url, ex);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = client.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
                logger.info(JSON.toJSONString(jsonResult));
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        } finally {
            request.releaseConnection();
        }
        return jsonResult;
    }


    /**
     * 请求发送成功，并得到响应
     */
    private static JSONObject getJsonObject(String url, JSONObject jsonResult, CloseableHttpResponse result) {
        if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String str = "";
            try {
                // 读取服务器返回过来的json字符串数据
                str = EntityUtils.toString(result.getEntity(), "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(str);
            } catch (Exception e) {
                logger.error("post请求提交失败:" + url, e);
            }
        }
        return jsonResult;
    }

    /**
     * 获取请求参数，并组合成
     *
     * @param params
     * @param strParam
     * @return
     */
    static String getParams(Map<String, Object> params, StringBuffer strParam) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            strParam.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return strParam.toString().substring(0, strParam.toString().length() - 1);
    }

}