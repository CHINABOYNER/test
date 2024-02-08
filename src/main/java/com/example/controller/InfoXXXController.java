package com.example.controller;

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

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 15:43
 * @description
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/info")
public class InfoXXXController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InfoXXXDao infoXXXDao;

    /**
     * 填写晚签情况信息，把信息进行判断，如果符合规则就存入数据库
     * 1.判断是否已经填写过 如果已经填写过就返回已经填写过的code
     * 2.如果没有填写过，就判断昨天是否有信息
     *  如果昨天有信息就使用判断公式：目前学院人数=昨天填写的在校人数＋当日返校人数-当日离校人数
     *      符合这个规则，就填写成功
     *      否则提示：数据错误，提交失败
     *  如果昨天没有消息，就直接填写成功
     * @param data
     * @return 401:请先登录 201:数据错误，提交失败 200:提交成功
     */
    @PostMapping
    public Result fillInInfo(@RequestHeader(value = "token", required = false) String token, @RequestBody JSONObject data) {
        if(token == null) {
            return new Result(401, "请先登录");
        }
        JSONObject parseToken = JWT.parse(token);
        log.info("token:{}", parseToken);
        if(parseToken == null) {
            return new Result(401, "请先登录");
        }
        String username = parseToken.getString("username");
        if(data == null) {
            return new Result(201, "数据错误，提交失败");
        }
        UserInfo userInfo = userInfoDao.findByUsername(username);
        String college = userInfo.getCollege();
        Integer minus = data.getInteger("minus"); // 离校人数
        Integer sum = data.getInteger("sum"); // 在校人数
        Integer addition = data.getInteger("addition"); // 返校人数
        Integer collegesum = data.getInteger("collegesum"); // 学院总人数
        Integer other = data.getInteger("other"); // 其他
        String remarks = data.getString("remarks"); // 备注
        if(minus == null || sum == null || addition == null || collegesum == null) {
            return new Result(201, "数据错误，提交失败");
        }
        Date today = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = df.format(new Date(today.getTime() - 24 * 60 * 60 * 1000));

        SimpleDateFormat newdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = newdf.format(today);

        InfoXXX lastData = infoXXXDao.getInfoXXX2(college, userInfo.getType()); //获取最新数据
        InfoXXX yesterdayData = infoXXXDao.getInfoXXXByDate1(college, yesterday, userInfo.getType()); //获取昨天数据
        log.info("lastData:{}", lastData);
        log.info("yesterdayData:{}", yesterdayData);

        if(null == yesterdayData) {
            return new Result(201, "数据错误，提交失败");
        }

        /**
         * 昨天有数据
         * 需要满足公式：目前学院人数=昨天填写的在校人数＋当日返校人数-当日离校人数
         *   分为两种情况：今天已经填写过了 今天还没有填写
         * 符合这个规则，就填写成功
         * 否则提示：数据错误，提交失败
         */
        Integer yesterdayDataSum = yesterdayData.getSum();
        if(yesterdayDataSum + addition - minus == sum) {
            UserInfo user = userInfoDao.findByUsername(username);
            //符合公式
            InfoXXX infoXXX = new InfoXXX();
            infoXXX.setMinus(minus);
            infoXXX.setAddition(addition);
            infoXXX.setSum(sum);
            infoXXX.setCollegesum(collegesum);
            infoXXX.setOther(other);
            infoXXX.setCollege(user.getCollege());
            infoXXX.setType(user.getType());
            infoXXX.setHead(username);
            infoXXX.setHead_name(user.getName());
            infoXXX.setHead_phone(user.getPhone_number());
            infoXXX.setRemarks(remarks);
            infoXXX.setDate(df.format(today));
            infoXXX.setTime(time);
            if(null != lastData && df.format(today).equals(lastData.getDate())){ //今天填写过了
                infoXXXDao.updateInfoXXX1(college, infoXXX);
                return new Result(205, "更新成功");
            }
            infoXXXDao.insertInfoXXX1(college, infoXXX);
            return new Result(200, "提交成功");
        } else {
            return new Result(201, "数据错误，提交失败");
        }

        //不考虑昨天没有数据的情况
//        if(null == yesterdayData) { //说明昨天没有数据
//            /**
//             * 昨天没有数据
//             * 没有数据就不需要满足这个公式，把它当作一条新的数据填入数据库
//             *   先通过username查询出学院和类型，然后填入数据库
//             *      分为两种情况：今天已经填写过了 今天没有填写过
//             */
//            if(null != lastData && df.format(today).equals(lastData.getDate())) {
//                /**
//                 * 今天已经填写过了
//                 *  更新数据
//                 */
//                InfoXXX infoXXX = new InfoXXX();
//                infoXXX.setMinus(minus);
//                infoXXX.setAddition(addition);
//                infoXXX.setSum(sum);
//                infoXXX.setCollegesum(collegesum);
//                infoXXX.setCollege(lastData.getCollege());
//                infoXXX.setType(lastData.getType());
//                infoXXX.setHead(lastData.getHead());
//                infoXXX.setRemarks(remarks);
//                infoXXX.setDate(df.format(today));
//                infoXXXDao.updateInfoXXX(username, infoXXX);
//                return new Result(205, "更新成功");
//            }
//
//            UserInfo user = userInfoDao.findByUsername(username);
//            InfoXXX infoXXX = new InfoXXX();
//            infoXXX.setMinus(minus);
//            infoXXX.setAddition(addition);
//            infoXXX.setSum(sum);
//            infoXXX.setCollegesum(collegesum);
//            infoXXX.setCollege(user.getCollege());
//            infoXXX.setType(user.getType());
//            infoXXX.setHead(username);
//            infoXXX.setHead_name(user.getName());
//            infoXXX.setHead_phone(user.getPhone_number());
//            infoXXX.setRemarks(remarks);
//            infoXXX.setDate(df.format(today));
//            infoXXXDao.insertInfoXXX(username, infoXXX);
//            return new Result(200, "提交成功");
//        } else { //说明昨天有数据，需要满足公式
//            /**
//             * 昨天有数据
//             * 需要满足公式：目前学院人数=昨天填写的在校人数＋当日返校人数-当日离校人数
//             *   分为两种情况：今天已经填写过了 今天还没有填写
//             * 符合这个规则，就填写成功
//             * 否则提示：数据错误，提交失败
//             */
//            Integer yesterdayDataSum = yesterdayData.getSum();
//            if(yesterdayDataSum + addition - minus == sum) {
//                UserInfo user = userInfoDao.findByUsername(username);
//                //符合公式
//                InfoXXX infoXXX = new InfoXXX();
//                infoXXX.setMinus(minus);
//                infoXXX.setAddition(addition);
//                infoXXX.setSum(sum);
//                infoXXX.setCollegesum(collegesum);
//                infoXXX.setCollege(user.getCollege());
//                infoXXX.setType(user.getType());
//                infoXXX.setHead(username);
//                infoXXX.setHead_name(user.getName());
//                infoXXX.setHead_phone(user.getPhone_number());
//                infoXXX.setRemarks(remarks);
//                infoXXX.setDate(df.format(today));
//                if(df.format(today).equals(lastData.getDate())){ //今天填写过了
//                    infoXXXDao.updateInfoXXX(username, infoXXX);
//                    return new Result(205, "更新成功");
//                }
//                infoXXXDao.insertInfoXXX(username, infoXXX);
//                return new Result(200, "提交成功");
//            } else {
//                return new Result(201, "数据错误，提交失败");
//            }
//        }
    }
}
