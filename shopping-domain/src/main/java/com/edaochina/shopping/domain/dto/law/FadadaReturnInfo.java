package com.edaochina.shopping.domain.dto.law;

/**
 * @author jintian
 * @date 2019/5/31 10:56
 */
public class FadadaReturnInfo {

    /**
     * 交易号
     */
    private String transaction_id;

    /**
     * 签署结果
     */
    private String result_code;

    /**
     * 签署结果描述
     */
    private String result_desc;

    /**
     * 下载地址
     */
    private String download_url;

    /**
     * 查看签署后合同
     */
    private String viewpdf_url;

    private String timestamp;

    /**
     * 摘要
     */
    private String msg_digest;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getViewpdf_url() {
        return viewpdf_url;
    }

    public void setViewpdf_url(String viewpdf_url) {
        this.viewpdf_url = viewpdf_url;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsg_digest() {
        return msg_digest;
    }

    public void setMsg_digest(String msg_digest) {
        this.msg_digest = msg_digest;
    }
}
