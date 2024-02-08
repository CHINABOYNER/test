package com.example.domain.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/1 22:10
 * @description
 */
@Data
public class UserAuthRes {
    private String openid; //用户唯一标识openid


    private String session_key; //用户会话密钥session_key


    private String unionid; //用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回


    private String errcode; //错误码


    private String errmsg; //错误信息
}
