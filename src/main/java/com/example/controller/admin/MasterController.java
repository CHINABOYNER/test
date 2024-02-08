package com.example.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.InfoXXXDao;
import com.example.dao.UserInfoDao;
import com.example.domain.POJO.InfoXXX;
import com.example.domain.POJO.UserInfo;
import com.example.domain.Result;
import com.example.util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/10 17:40
 * @description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/master")
public class MasterController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InfoXXXDao infoXXXDao;

    /**
     * 已经提交
     * @return
     */
    @PostMapping
    public Result getMasterData(@RequestHeader(value = "token", required = false) String token) {
        if(token == null) {
            return new Result(201, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        if(parseToken == null) {
            return new Result(201, "请先登录");
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = simpleDateFormat.format(date);

        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
        List<JSONObject> collegeData = new ArrayList<>();

        Map<String, Boolean> myMap = new HashMap<>(); //去重学院
        for (UserInfo userInfo : ordinaryUserList) {
            if(myMap.get(userInfo.getCollege()) != null || userInfo.getType() != 2) {
                continue;
            } else {
                myMap.put(userInfo.getCollege(), true);
            }

            InfoXXX infoXXXByDate = infoXXXDao.getInfoXXXByDate1(userInfo.getCollege(), toDay, userInfo.getType());
            if(infoXXXByDate == null || userInfo.getType() != 2) {
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("college", userInfo.getCollege());
            jsonObject.put("minus", infoXXXByDate.getMinus());
            jsonObject.put("addition", infoXXXByDate.getAddition());
            jsonObject.put("sum", infoXXXByDate.getSum());
            collegeData.add(jsonObject);
        }
        return new Result(200, "获取成功", collegeData);
    }

    @PutMapping
    public Result getNotSubmitMaster(@RequestHeader(value = "token", required = false) String token) {
        if(token == null) {
            return new Result(201, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        if(parseToken == null) {
            return new Result(201, "请先登录");
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = simpleDateFormat.format(date);

        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
        List<String> collegeData = new ArrayList<>();

        Map<String, Boolean> myMap = new HashMap<>(); //去重学院
        for (UserInfo userInfo : ordinaryUserList) {
            if(myMap.get(userInfo.getCollege()) != null || userInfo.getType() != 2) {
                continue;
            } else {
                myMap.put(userInfo.getCollege(), true);
            }

            InfoXXX infoXXXByDate = infoXXXDao.getInfoXXXByDate1(userInfo.getCollege(), toDay, userInfo.getType());
            if(infoXXXByDate != null || userInfo.getType() != 2) {
                continue;
            }
            collegeData.add(userInfo.getCollege());
        }
        return new Result(200, "获取成功", collegeData);
    }
}
