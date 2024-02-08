package com.example.controller.admin.history;

import org.apache.ibatis.annotations.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/11 21:13
 * @description
 */
@Mapper
public interface HistoryDao {
    @Select("select sumben,minusben,additionben,otherben,collegesumben,sumyan,minusyan,additionyan,otheryan,collegesumyan,date from history where date = #{date}")
    History getHistory(String date);

    @Insert("insert into history (sumben,minusben,additionben,otherben,collegesumben,sumyan,minusyan,additionyan,otheryan,collegesumyan,date)" +
            " values (#{sumben},#{minusben},#{additionben},#{otherben},#{collegesumben},#{sumyan}," +
            "#{minusyan},#{additionyan},#{otheryan},#{collegesumyan},#{date})")
    void insertHistory(History h);

    @Update("update history set sumben=#{sumben},minusben=#{minusben},additionben=#{additionben},otherben=#{otherben},collegesumben=#{collegesumben}," +
            "sumyan=#{sumyan},minusyan=#{minusyan},additionyan=#{additionyan},otheryan=#{otheryan},collegesumyan=#{collegesumyan} where date=#{date}")
    void updateHistory(History h);
}
