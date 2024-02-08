package com.example.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.UserInfoDao;
import com.example.domain.POJO.UserInfo;
import com.example.domain.Result;
import com.example.util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/8 22:01
 * @description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class Login {
    @Autowired
    UserInfoDao userInfoDao;
    @PostMapping
    public Result login(@RequestBody(required = false) JSONObject data) {
        //状态码：200成功，201请求参数为空，205权限不足
        if(null == data) {
            return new Result(201, "请求参数为空", null);
        }
        String username = data.getString("username");
        String password = data.getString("password");
        if(null == username || null == password) {
            return new Result(201, "请求参数为空", null);
        }
        UserInfo existed = userInfoDao.isExisted(username, password);
        if(null == existed) {
            return new Result(201, "用户名或密码错误", null);
        }
        boolean power = existed.isPower();
        if (!power) {
            return new Result(205, "权限不足", null);
        }
        String token = JWT.getToken(username, power);
        existed.setToken(token);
        return new Result(200, "登录成功", existed);
    }

    @GetMapping
    public Result JWTIsOk(@RequestHeader(value = "token", required = false) String token) {
        if(null == token) {
            return new Result(201, "token为空", null);
        }
        boolean isOk = JWT.checkToken(token);
        return new Result(isOk ? 200 : 201, isOk ? "token验证成功" : "token验证失败", null);
    }
}
