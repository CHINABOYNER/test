package com.example.domain.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 14:44
 * @description
 */
@Data
public class UserInfo {
    //username, college, type, name, phone_number
    private String username;
    @JsonIgnore
    private String password;
    private String college;
    private int type;
    private String name;
    private String phone_number;
    private boolean power;
    private String token;
    private String openId;
}
