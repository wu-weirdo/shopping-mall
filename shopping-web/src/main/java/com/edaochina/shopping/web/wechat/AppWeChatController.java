package com.edaochina.shopping.web.wechat;

import com.alibaba.fastjson.JSONObject;
import com.edaochina.shopping.api.service.sys.CommonService;
import com.edaochina.shopping.common.result.AjaxResult;
import com.edaochina.shopping.common.result.BaseResult;
import com.edaochina.shopping.domain.dto.wechat.MiniAppQrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/app/wechat")
public class AppWeChatController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/miniapp/qr", method = RequestMethod.POST)
    public AjaxResult getminiappQr(@RequestBody MiniAppQrDTO dto) {
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + dto.getAccess_token());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(JSONObject.toJSONString(dto));
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedImage bufferedImage = ImageIO.read(httpURLConnection.getInputStream());
            File outputfile = new File("D:/saved.png");
            ImageIO.write(bufferedImage, "png", outputfile);
            return BaseResult.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.failedResult();
        } finally {
        }
    }

    @RequestMapping("createMerchantCode")
    public AjaxResult createMerchantCode(String merchantId) {
        return BaseResult.successResult(commonService.getMerchantQrOnlyImage(merchantId));
    }
}
