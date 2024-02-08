package com.example.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.example.dao.InfoXXXDao;
import com.example.dao.UserInfoDao;
import com.example.domain.POJO.InfoXXX;
import com.example.domain.POJO.UserInfo;
import com.example.domain.Result;
import com.example.util.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/10 15:22
 * @description
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/alldata")
public class UserController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InfoXXXDao infoXXXDao;

    @GetMapping
    public Result getAllData(@RequestHeader(value = "token", required = false) String token) {
        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
        Map<String, Map<String, Integer>> result = new HashMap<>();
        Map<String, Integer> collegeMap = new HashMap<>();
        collegeMap.put("minus", 0);
        collegeMap.put("addition", 0);
        collegeMap.put("sum", 0);
        collegeMap.put("other", 0);
        collegeMap.put("collegesum", 0);
        Map<String, Integer> masterMap = new HashMap<>();
        masterMap.put("minus", 0);
        masterMap.put("addition", 0);
        masterMap.put("sum", 0);
        masterMap.put("other", 0);
        masterMap.put("collegesum", 0);
        Map<String, Integer> allMap = new HashMap<>();
        allMap.put("minus", 0);
        allMap.put("addition", 0);
        allMap.put("sum", 0);
        allMap.put("other", 0);
        allMap.put("collegesum", 0);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = simpleDateFormat.format(date);
//        String toDay = "2023-10-14";

        Map<String, Boolean> flagMap1 = new HashMap<>(); //本科生去重
        Map<String, Boolean> flagMap2 = new HashMap<>(); //研究生去重
        for(UserInfo userInfo : ordinaryUserList) {
            String college = userInfo.getCollege();
//            InfoXXX infoXXX = infoXXXDao.getInfoXXX(username);
            InfoXXX infoXXX = infoXXXDao.getInfoXXXByDate1(college, toDay, userInfo.getType());
            if(null == infoXXX) {
                continue;
            }
            if(infoXXX.getType() == 1) { //本科生
                if(flagMap1.get(college) == null) {
                    flagMap1.put(college, true);
                } else {
                    continue;
                }
                collegeMap.put("minus", collegeMap.get("minus") + infoXXX.getMinus());
                collegeMap.put("addition", collegeMap.get("addition") + infoXXX.getAddition());
                collegeMap.put("sum", collegeMap.get("sum") + infoXXX.getSum());
                collegeMap.put("other", collegeMap.get("other") + infoXXX.getOther());
                collegeMap.put("collegesum", collegeMap.get("collegesum") + infoXXX.getCollegesum());
            } else { //研究生
                if(flagMap2.get(college) == null) {
                    flagMap2.put(college, true);
                } else {
                    continue;
                }
                masterMap.put("minus", masterMap.get("minus") + infoXXX.getMinus());
                masterMap.put("addition", masterMap.get("addition") + infoXXX.getAddition());
                masterMap.put("sum", masterMap.get("sum") + infoXXX.getSum());
                masterMap.put("other", masterMap.get("other") + infoXXX.getOther());
                masterMap.put("collegesum", masterMap.get("collegesum") + infoXXX.getCollegesum());
            }
            allMap.put("minus", collegeMap.get("minus") + masterMap.get("minus"));
            allMap.put("addition", collegeMap.get("addition") + masterMap.get("addition"));
            allMap.put("sum", collegeMap.get("sum") + masterMap.get("sum"));
            allMap.put("other", collegeMap.get("other") + masterMap.get("other"));
            allMap.put("collegesum", collegeMap.get("collegesum") + masterMap.get("collegesum"));
        }
        result.put("college", collegeMap);
        result.put("master", masterMap);
        result.put("all", allMap);
        return new Result(200, "获取成功", result);
    }


    @PostMapping
    public Result modifyPassword(@RequestHeader(value = "token", required = false) String token, @RequestBody JSONObject data) {
        log.info("token:{}", token);
        log.info("data:{}", data);
        if(token == null) {
            return new Result(201, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        if(parseToken == null) {
            return new Result(201, "请先登录");
        }
        String username = parseToken.getString("username");
        String origin_password = data.getString("origin_password");
        if(userInfoDao.isExisted(username, origin_password) == null) {
            return new Result(201, "原密码错误");
        }
        String change_password = data.getString("change_password");
        String confirm_password = data.getString("confirm_password");
        if(!change_password.equals(confirm_password)) {
            return new Result(201, "两次密码不一致");
        }
        userInfoDao.updatePassword(username, change_password);
        return new Result(200, "修改成功", null);
    }
}
