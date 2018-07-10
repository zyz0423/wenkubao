package com.wenkubao.controller;

import com.wenkubao.entity.Userecord;
import com.wenkubao.service.UserrecordService;
import com.wenkubao.util.DateUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class DownloadController {

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private UserrecordService userrecordService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/download/email")
    @ResponseBody
    public String downloadEmail(String docid, String email, HttpServletRequest request) {
        if (docid.indexOf("wenku.baidu.com") >= 0 || docid.indexOf("wk.baidu.com") >= 0 || docid.indexOf("m.baidu.com") >= 0 || docid.indexOf("mbd.baidu.com") >= 0) {
        }else {
            return "请确保您提交的是百度文库的连接";
        }

        String ip = getIPAddress(request);
        int count = userrecordService.getUseCount(ip, DateUtil.getFormatDate("yyyy-MM-dd"));
        if(count >= 3){
            return "抱歉，每天只提供三次免费下载,如有需要可点击上方购买链接";
        }

        HttpGet httpGet = new HttpGet(docid);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity,"GBK");
            if(result.indexOf("购买文本")>0){
                return "抱歉，我们不能下载付费文档！";
            }
            if(result.indexOf("百度文库") > 0){
                Userecord userecord = new Userecord();
                userecord.setDocurl(docid);
                userecord.setGmtDate(new Date());
                userecord.setUseIp(ip);
                userecord.setStrDate(DateUtil.getFormatDate("yyyy-MM-dd"));
                userrecordService.insertRecord(userecord);

                return "http://39.108.149.27:9999/default.aspx";
            }else {
                return "请确保您的连接是百度文库的连接。";
            }
        } catch (Exception e) {
            return "http://39.108.149.27:9999/default.aspx";
        }
    }

    public String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

   /* public static void main(String[] args) throws Exception {
        HttpPost httpPost = new HttpPost("http://39.108.149.27:9999/default.aspx");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("txtUrl", "https://wenku.baidu.com/view/0394adb3a0116c175f0e4889.html?from=search"));
        params.add(new BasicNameValuePair("mail", "482081294@qq.com"));
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "utf-8");
        httpPost.setEntity(uefEntity);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        System.out.println(result);
    }*/
}
