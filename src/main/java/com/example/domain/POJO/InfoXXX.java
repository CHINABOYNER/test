package com.example.domain.POJO;

import lombok.Data;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 15:13
 * @description
 */
@Data
public class InfoXXX {
    //id, minus, addition, sum, college, type, head, date
    private int minus; //离校人数
    private int addition; //返校人数
    private int sum; //在校人数
    private int collegesum; //学院总人数
    private int other; //其他
    private String college; //学院名
    private int type; //类型
    private String head; //负责人账号
    private String head_name; //负责人姓名
    private String head_phone; //负责人电话
    private String remarks; //备注
    private String date; //日期填写
    private String time; //时间填写
}
