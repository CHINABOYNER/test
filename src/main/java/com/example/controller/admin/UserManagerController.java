package com.example.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.InfoXXXDao;
import com.example.dao.UserInfoDao;
import com.example.domain.POJO.UserInfo;
import com.example.domain.Result;
import com.example.util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/10 18:09
 * @description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usermanager")
public class UserManagerController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InfoXXXDao infoXXXDao;

    @Transactional
    @PostMapping
    public Result addUser(@RequestHeader(value = "token", required = false) String token, @RequestBody JSONObject data) {
        if(token == null) {
            return new Result(201, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        if(parseToken == null) {
            return new Result(201, "请先登录");
        }
        String password = data.getString("password");
        String college = data.getString("college");
        Integer type = data.getInteger("type");
        String name = data.getString("name");
        String phone_number = data.getString("phone_number");
        String username = generateRandomString(7);
        UserInfo byUsername = userInfoDao.findByUsername(username);
        if(byUsername != null) {
            return new Result(201, "添加失败");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setCollege(college);
        userInfo.setType(type);
        userInfo.setName(name);
        userInfo.setPhone_number(phone_number);
        userInfoDao.insertUserInfo(userInfo);
        infoXXXDao.createInfoXXX1(college);
        return new Result(200, "添加成功", username);
    }

    @Transactional
    @DeleteMapping
    public Result deleteUser(@RequestHeader(value = "token", required = false) String token, @RequestBody JSONObject data) {
        if(token == null) {
            return new Result(201, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        if(parseToken == null) {
            return new Result(201, "请先登录");
        }
        String username = data.getString("username");
        UserInfo userInfo = userInfoDao.findByUsername(username);
        userInfoDao.deleteUser(username);
        infoXXXDao.dropInfoXXX1(userInfo.getCollege());
        return new Result(200, "删除成功");
    }



    public static String generateRandomString(int length) {
        String characters = "0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }
        return stringBuilder.toString();
    }
}
