package com.example.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.InfoXXXDao;
import com.example.dao.UserInfoDao;
import com.example.domain.POJO.InfoXXX;
import com.example.domain.POJO.UserInfo;
import com.example.util.GetUserAuthRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/10 10:08
 * @description
 */
@Slf4j
@RestController
@Component
@EnableScheduling
public class ScheduleMessage {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InfoXXXDao infoXXXDao;


    @Scheduled(cron = "0 0 23 * * ?")
//    @GetMapping("/123")
    public void sendMessage() {
        Date date = new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);

        List<UserInfo> userInfoList = userInfoDao.getOrdinaryUserList(); //非管理员用户，用于判断是否提交
        List<UserInfo> notSubmitList = new ArrayList<>(); //没有提交的用户

        String finalTime = time;
        userInfoList.forEach(userInfo -> {
            String college = userInfo.getCollege();
            InfoXXX infoXXX = infoXXXDao.getInfoXXXByDate1(college, finalTime, userInfo.getType());
            if(null == infoXXX) {
                notSubmitList.add(userInfo);
            }
        });
        String msg = "您的学院还没有提交信息，请尽快提交";
        if(notSubmitList.size() == 0) { //没有人没有提交
            return;
        }

        System.out.println("发送消息");
        //获取时间，规定格式为 yyyy-MM-dd HH:mm:ss
        date = new Date();
        sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = sdf.format(date);

//        List<String> openIdList = userInfoDao.getOpenIdList();

        JSONArray json = new JSONArray();
        JSONObject json1 = new JSONObject();
        json1.put("value", time);
        JSONObject json2 = new JSONObject();
        json2.put("value", msg);
        JSONObject json3 = new JSONObject();
        json3.put("value", "系统提示");


        Map<String, JSONObject> map = new HashMap<>();
        map.put("date2", json1);
        map.put("thing4", json2);
        map.put("name3", json3);

        JSONObject accessToken = null;
        try {
            accessToken = GetUserAuthRes.getAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert accessToken != null;
        String access_token = accessToken.getString("access_token");

        notSubmitList.forEach(userInfo -> {
            try {
                if(null != userInfo.getOpenId()) {
                    log.info("openId: " + userInfo.getOpenId());
                    GetUserAuthRes.sendMessage(userInfo.getOpenId(), access_token, (JSONObject) JSON.toJSON(map));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        openIdList.forEach(openid -> {
//            try {
//                GetUserAuthRes.sendMessage(openid, access_token, (JSONObject) JSON.toJSON(map));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }
}
