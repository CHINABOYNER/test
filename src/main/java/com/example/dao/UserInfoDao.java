package com.example.dao;

import com.example.domain.POJO.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 14:45
 * @description
 */
@Mapper
public interface UserInfoDao {
    @Select("select username, college, type, name, phone_number, power, open_id openId from user_info where username = #{username}")
    UserInfo findByUsername(String username);

    @Select("select username, college, type, name, phone_number, power, open_id openId from user_info where (username = #{username} or phone_number = #{username})and password = #{password};")
    UserInfo isExisted(String username, String password);

    void insertUserInfo(UserInfo userInfo);

    @Select("select open_id from user_info where open_id is not null")
    List<String> getOpenIdList();

    @Insert("update user_info set open_id = #{openId} where username = #{username} or phone_number = #{username}")
    void insertOpenId(String username, String openId);

    @Select("select username, college, type, name, phone_number, power, open_id openId from user_info where power = 0")
    List<UserInfo> getOrdinaryUserList();

    @Delete("delete from user_info where username = #{username}")
    void deleteUser(String username);

    @Update("update user_info set password = #{password} where username = #{username}")
    void updatePassword(String username, String password);
}
