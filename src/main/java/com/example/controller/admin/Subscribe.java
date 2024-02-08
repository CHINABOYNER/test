package com.example.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.UserInfoDao;
import com.example.domain.Result;
import com.example.util.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/8 22:51
 * @description
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/subscribe")
public class Subscribe {
    @Autowired
    UserInfoDao userInfoDao;

    @PostMapping
    public Result subscribe(@RequestHeader(value = "token", required = false) String token, @RequestBody(required = false) JSONObject data) {
        if(token == null) {
            return new Result(201, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        log.info("token:{}", parseToken);
        if(parseToken == null) {
            return new Result(201, "请先登录");
        }
        String username = parseToken.getString("username");
        String openId = data.getString("openid");
        if(openId == null) {
            return new Result(401, "openid错误");
        }
        userInfoDao.insertOpenId(username, openId);
        System.out.println(data);
        return new Result(200, "订阅成功");
    }
}
