package com.example.dao;

import com.example.domain.POJO.InfoXXX;
import org.apache.ibatis.annotations.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 15:16
 * @description
 */
@Mapper
public interface InfoXXXDao {
//    //获取最新数据
//    InfoXXX getInfoXXX(String username);
//
//    InfoXXX getInfoXXXByDate(String username, String date);
//
//    void updateInfoXXX(@Param("username") String username, @Param("infoXXX") InfoXXX infoXXX);
//
//    void createInfoXXX(@Param("username") String username);
//
//    void insertInfoXXX(@Param("username") String username, @Param("infoXXX") InfoXXX infoXXX);
//
//    //DROP TABLE `scuec`.`info_2346857`
//    void dropInfoXXX(@Param("username") String username);

    //获取最新数据
    InfoXXX getInfoXXX1(String tableName);
    InfoXXX getInfoXXX2(String tableName, int type);

    InfoXXX getInfoXXXByDate1(String tableName, String date, int type);
    InfoXXX getInfoXXXByDate2(String tableName, String date);

    void updateInfoXXX1(@Param("tableName") String tableName, @Param("infoXXX") InfoXXX infoXXX);

    void createInfoXXX1(@Param("tableName") String tableName);

    void insertInfoXXX1(@Param("tableName") String tableName, @Param("infoXXX") InfoXXX infoXXX);

    void dropInfoXXX1(@Param("tableName") String tableName);
}
