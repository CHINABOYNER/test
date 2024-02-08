package com.example.domain;

import lombok.Data;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/1 18:15
 * @description
 */
@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
