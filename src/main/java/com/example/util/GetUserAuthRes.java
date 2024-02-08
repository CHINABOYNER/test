package com.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/1 22:11
 * @description
 */
@Slf4j
public class GetUserAuthRes {
    public static JSONObject userAuth(String code) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        String urlStr = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=wx3614e6b8195488ec&secret=4f74e5b6c26e5945d70fdfe3d479b987&" +
                "js_code=" + code +"&grant_type=authorization_code";
        HttpGet httpGet = new HttpGet(urlStr);



        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000) //设置连接超时时间,单位毫秒
                .setConnectionRequestTimeout(1000) //设置从connect Manager获取Connection 超时时间，单位毫秒
                .setSocketTimeout(1000) //设置数据传输的最长时间，单位毫秒
                .build();
        //给请求设置请求信息
        httpGet.setConfig(requestConfig);

        // 响应
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpGet);

            StatusLine status = response.getStatusLine();
            if(HttpStatus.SC_OK == status.getStatusCode()){
                //获取响应结果
                HttpEntity entity = response.getEntity();

                String toStringResult = EntityUtils.toString(entity, StandardCharsets.UTF_8);

                JSONObject parse = (JSONObject) JSONObject.parse(toStringResult);
                System.out.println(parse);

                //确保流关闭
                EntityUtils.consume(entity);
                return parse;
            }else{
                System.out.println("响应失败，响应码：" + status.getStatusCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("请求失败");
            return null;
        }finally {
            if(closeableHttpClient != null){
                closeableHttpClient.close();
            }
            if(response != null){
                response.close();
            }
        }
        return null;
    }


    public static void sendMessage(String openId, String access_token, JSONObject data) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token;
        String templateId = "KlY4NhcN_Fq0x41mBmGKnW8KAzk_61JhpDFq-CTDgrY";
        String miniprogram_state = "trial"; //developer为开发版；trial为体验版；formal为正式版；默认为正式版
        String lang = "zh_CN"; //默认为中文

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", access_token);
        jsonObject.put("template_id", templateId);
        jsonObject.put("touser", openId);
        jsonObject.put("data", data);
        jsonObject.put("miniprogram_state", miniprogram_state);
        jsonObject.put("lang", lang);

        log.info("发送消息参数：{}", jsonObject);


        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        //创建一个HttpPost对象,并指定请求的URL地址
        HttpPost httpPost = new HttpPost(url);
        // 给httppost对象设置json格式的参数
        StringEntity httpEntity = new StringEntity(jsonObject.toString(),"utf-8");
        // 设置请求格式
        httpPost.setHeader("Content-type","application/json");
        httpPost.setEntity(httpEntity);

        // 发送请求，并获取返回值
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            String toString = EntityUtils.toString(entity, "utf-8");
            // 将response转成String并存储在result中
            log.info("发送消息返回结果：{}", toString);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static JSONObject getAccessToken() throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        String urlStr = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&" +
                "appid=wx3614e6b8195488ec&secret=4f74e5b6c26e5945d70fdfe3d479b987";
        HttpGet httpGet = new HttpGet(urlStr);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000) //设置连接超时时间,单位毫秒
                .setConnectionRequestTimeout(1000) //设置从connect Manager获取Connection 超时时间，单位毫秒
                .setSocketTimeout(1000) //设置数据传输的最长时间，单位毫秒
                .build();
        //给请求设置请求信息
        httpGet.setConfig(requestConfig);

        // 响应
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpGet);

            StatusLine status = response.getStatusLine();
            if(HttpStatus.SC_OK == status.getStatusCode()){
                //获取响应结果
                HttpEntity entity = response.getEntity();

                String toStringResult = EntityUtils.toString(entity, StandardCharsets.UTF_8);

                JSONObject parse = (JSONObject) JSONObject.parse(toStringResult);
                System.out.println(parse);

                //确保流关闭
                EntityUtils.consume(entity);
                return parse;
            }else{
                System.out.println("响应失败，响应码：" + status.getStatusCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("请求失败");
            return null;
        }finally {
            if(closeableHttpClient != null){
                closeableHttpClient.close();
            }
            if(response != null){
                response.close();
            }
        }
        return null;
    }
}
