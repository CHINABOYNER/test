package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.domain.Result;
import com.example.util.GetUserAuthRes;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/4 10:20
 * @description the class is message controller, which is used to send message to corresponding user
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/wxlogin")
public class MessageController {
    /**
     * the method is used to get openid and session_key, which is used to return userAuthRes
     * @param data the data is code, which is used to get openid and session_key
     * @return the method return userAuthRes
     */
    @PostMapping
    public Result getUserAuthRes(@RequestBody JSONObject data) {
        String code = data.getString("code");
        JSONObject userAuthRes = null;
        try {
            userAuthRes = GetUserAuthRes.userAuth(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(200, "success", userAuthRes);
    }
}
