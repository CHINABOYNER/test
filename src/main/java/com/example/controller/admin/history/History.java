package com.example.controller.admin.history;

import lombok.Data;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/11 21:12
 * @description
 */
@Data
public class History {
    //sumben	minusben	additionben	sumyan	minusyan	additionyan	date
    private Integer sumben;
    private Integer minusben;
    private Integer additionben;
    private Integer otherben;
    private Integer collegesumben;
    private Integer sumyan;
    private Integer minusyan;
    private Integer additionyan;
    private Integer otheryan;
    private Integer collegesumyan;
    private int sum;
    private int minus;
    private int addition;
    private int other;
    private int collegesum;
    private String date;

    public void SUMALL() {
        sum = sumben + sumyan;
        minus = minusben + minusyan;
        addition = additionben + additionyan;
        other = otherben + otheryan;
        collegesum = collegesumben + collegesumyan;
    }
}
