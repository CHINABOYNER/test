package com.example.controller;

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
import java.util.Date;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 16:13
 * @description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InfoXXXDao infoXXXDao;
    @PostMapping
    public Result login(@RequestBody JSONObject data) {
        String username = data.getString("username");
        String password = data.getString("password");
        if(null == username || null == password) {
            return new Result(201, "用户名或密码不能为空", null);
        }
        UserInfo existed = userInfoDao.isExisted(username, password);
        if(null == existed) {
            return new Result(201, "用户名或密码错误", null);
        }
        String token = JWT.getToken(existed.getUsername(), existed.isPower());
        existed.setToken(token);
        return new Result(200, "登录成功", existed);
    }

    @GetMapping
    public Result JWTIsOk(@RequestHeader(value = "token", required = false) String token) {
        if(null == token) {
            return new Result(201, "token为空", null);
        }
        JSONObject jsonObject = JWT.parse(token);
        if(null == jsonObject) {
            return new Result(201, "token无效", null);
        }
        String username = jsonObject.getString("username");
        UserInfo byUsername = userInfoDao.findByUsername(username);
        return new Result(200, "token有效", byUsername);
    }

    @PutMapping
    public Result isOK(@RequestHeader(value = "token", required = false) String token) {
        if(token == null) {
            return new Result(201, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        if(parseToken == null) {
            return new Result(201, "请先登录");
        }

        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = sd.format(date);

        String username = parseToken.getString("username");
        UserInfo userInfo = userInfoDao.findByUsername(username);
        InfoXXX infoXXXByDate = infoXXXDao.getInfoXXXByDate1(userInfo.getCollege(), toDay, userInfo.getType());
        if(infoXXXByDate == null) {
            return new Result(201, "未打卡");
        }
        return new Result(200, "已打卡");
    }
}
