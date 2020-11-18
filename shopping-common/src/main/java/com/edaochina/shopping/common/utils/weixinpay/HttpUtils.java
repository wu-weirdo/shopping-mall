package com.edaochina.shopping.common.utils.weixinpay;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Http请求工具
 *
 * @author zzk
 * @created 2018-12-12 18:57:56
 * @since v1.0
 */
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 发送post
     *
     * @param url
     * @param paramEntity
     * @return
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v1.0
     */
    public static String post(String url, HttpEntity paramEntity) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
        try {
            httppost.setEntity(paramEntity);
            log.info("executing request {}", httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    log.info("--------------------------------------");
                    log.info("Response content: {}", result);
                    log.info("--------------------------------------");
                    return result;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * post xml数据
     *
     * @param url
     * @param xml
     * @return
     * @author zzk
     * @created 2018-12-12 18:57:56
     * @since v1.0
     */
    public static String postXml(String url, String xml) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("ContentType", "text/xml;charset=UTF-8");
        try {
            httppost.setEntity(new StringEntity(xml, "UTF-8"));
            log.info("executing request {}", httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    log.info("--------------------------------------");
                    log.info("Response content: {}", result);
                    log.info("--------------------------------------");
                    return result;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return null;
    }


    public static String get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 实例化HTTP方法
        HttpGet request = new HttpGet(url);
        try {
            log.info("executing request {}", request.getURI());
            CloseableHttpResponse response = httpclient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    log.info("--------------------------------------");
                    log.info("Response content: {}", result);
                    log.info("--------------------------------------");
                    return result;
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return null;
    }

    public static File downloadFileByUrl(String url, String localFileName) {
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        File storeFile = new File(localFileName);
        InputStream is = null;
        OutputStream out = null;
        try {
            storeFile.getParentFile().mkdirs();
            out = new FileOutputStream(storeFile);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            IOUtils.copy(is, out);
            return storeFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 调用 API
     *
     * @return str
     */
    public static String postJson(String apiURL, String params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(apiURL);
        String body = null;
        try {
            // 建立一个NameValuePair数组，用于存储欲传送的参数
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if (params != null) {
                // 设置字符集
                StringEntity stringEntity = new StringEntity(params, "utf-8");
                // 设置参数实体
                httpPost.setEntity(stringEntity);
            }

            HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed:" + response.getStatusLine());
            }

            // Read the response body
            body = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (IOException e) {
            // 网络错误
            log.error("http post exception :{}", e);
        }
        return body;
    }

    /**
     * 请求地址下载内容
     *
     * @param url
     * @param param
     * @param fileName
     */
    public static void postJsonDownloadFile(String url, String param, String fileName) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        InputStream is = null;
        OutputStream out = null;
        try {
            File storeFile = new File(fileName);
            storeFile.getParentFile().mkdirs();
            out = new FileOutputStream(storeFile);
            // 建立一个NameValuePair数组，用于存储欲传送的参数
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if (param != null) {
                // 设置字符集
                StringEntity stringEntity = new StringEntity(param, "utf-8");
                // 设置参数实体
                httpPost.setEntity(stringEntity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.error("Method failed:" + response.getStatusLine());
            }

            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            IOUtils.copy(is, out);
        } catch (IOException e) {
            // 网络错误
            log.error("http post exception :{}", e);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(out);
        }
    }
}
