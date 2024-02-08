package com.example.controller;

import com.example.controller.admin.history.History;
import com.example.controller.admin.history.HistoryDao;
import com.example.dao.InfoXXXDao;
import com.example.dao.UserInfoDao;
import com.example.domain.POJO.InfoXXX;
import com.example.domain.POJO.UserInfo;
import com.example.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 9:11
 * @description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InfoXXXDao infoXXXDao;
    @Autowired
    HistoryDao historyDao;

    @GetMapping
    public void Download(HttpServletResponse httpServletResponse) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = simpleDateFormat.format(date);
        String dateString = "2023-11-26";

        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
        List<Map<String, Integer>> result = new ArrayList<>();
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

        Map<String, Boolean> myMap = new HashMap<>(); //去重学院(本科生)
        Map<String, Boolean> myMap1 = new HashMap<>(); //去重学院(研究生)
        for(UserInfo userInfo : ordinaryUserList) {
            String college = userInfo.getCollege();
            if(userInfo.getType() == 1 && myMap.get(college) == null) {
                myMap.put(college, true);
            } else if(userInfo.getType() == 2 && myMap1.get(college) == null) {
                myMap1.put(college, true);
            } else {
                continue;
            }

//            InfoXXX infoXXX = infoXXXDao.getInfoXXX1(college);
            InfoXXX infoXXX = infoXXXDao.getInfoXXXByDate1(college, dateString, userInfo.getType());
            if(null == infoXXX) {
                continue;
            }
            if(infoXXX.getType() == 1) { //本科生
                collegeMap.put("minus", collegeMap.get("minus") + infoXXX.getMinus());
                collegeMap.put("addition", collegeMap.get("addition") + infoXXX.getAddition());
                collegeMap.put("sum", collegeMap.get("sum") + infoXXX.getSum());
                collegeMap.put("other", collegeMap.get("other") + infoXXX.getOther());
                collegeMap.put("collegesum", collegeMap.get("collegesum") + infoXXX.getCollegesum());
            } else { //研究生
                masterMap.put("minus", masterMap.get("minus") + infoXXX.getMinus());
                masterMap.put("addition", masterMap.get("addition") + infoXXX.getAddition());
                masterMap.put("sum", masterMap.get("sum") + infoXXX.getSum());
                masterMap.put("other", masterMap.get("other") + infoXXX.getOther());
                masterMap.put("collegesum", masterMap.get("collegesum") + infoXXX.getCollegesum());
            }
        }
        result.add(collegeMap);
        result.add(masterMap);


        httpServletResponse.setContentType("application/msexcel");
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = simpleDateFormat.format(date);

        new Thread(() -> {
            History history = new History();
            history.setSumben(collegeMap.get("sum"));
            history.setSumyan(masterMap.get("sum"));
            history.setMinusben(collegeMap.get("minus"));
            history.setMinusyan(masterMap.get("minus"));
            history.setAdditionben(collegeMap.get("addition"));
            history.setAdditionyan(masterMap.get("addition"));
            history.setOtherben(collegeMap.get("other"));
            history.setOtheryan(masterMap.get("other"));
            history.setCollegesumben(collegeMap.get("collegesum"));
            history.setCollegesumyan(masterMap.get("collegesum"));
            history.setDate(dateString);
            history.SUMALL();
            if(historyDao.getHistory(dateString) != null) {
                historyDao.updateHistory(history);
            } else {
                historyDao.insertHistory(history);
            }
        }).start();

        String fileName = dateString + "晚查寝情况统计表(总表).xlsx";
        httpServletResponse.setHeader("Content-Disposition", "filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));

        DownloadService downloadService = new DownloadService();
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        downloadService.download(outputStream, result);
    }

    @GetMapping("/college")
    public void downloadPartCollege(HttpServletResponse httpServletResponse) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = simpleDateFormat.format(date);
        String dateString = "2024-01-13";

        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
        List<List<String>> result = new ArrayList<>();

        Map<String, Boolean> myMap = new HashMap<>(); //去重学院
        for(UserInfo userInfo : ordinaryUserList) {
            String college = userInfo.getCollege();
            if(myMap.get(college) != null || userInfo.getType() != 1) {
                continue;
            } else {
                myMap.put(college, true);
            }

            InfoXXX infoXXX = infoXXXDao.getInfoXXXByDate1(college, dateString, userInfo.getType());
            if(null == infoXXX) {
                continue;
            }
            if(infoXXX.getType() == 1) { //本科生
                List<String> list = new ArrayList<>();
                list.add(infoXXX.getCollege());
                list.add(infoXXX.getHead_name());
                list.add(infoXXX.getHead_phone());
                list.add(String.valueOf(infoXXX.getSum()));
                list.add(String.valueOf(infoXXX.getMinus()));
                list.add(String.valueOf(infoXXX.getAddition()));
                list.add(String.valueOf(infoXXX.getCollegesum()));
                list.add(String.valueOf(infoXXX.getOther()));
                list.add(String.valueOf(infoXXX.getTime()));
                list.add(String.valueOf(infoXXX.getRemarks()));
                result.add(list);
            }
        }
        httpServletResponse.setContentType("application/msexcel");
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateString = simpleDateFormat.format(date);
        String fileName = dateString + "晚本科生查寝情况统计表(各学院).xlsx";
        httpServletResponse.setHeader("Content-Disposition", "filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));

        DownloadService downloadService = new DownloadService();
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        downloadService.downloadPart(outputStream, result, 1);
    }

    @GetMapping("/master")
    public void downloadPartMaster(HttpServletResponse httpServletResponse) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = simpleDateFormat.format(date);
        String dateString = "2024-01-13";

        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
        List<List<String>> result = new ArrayList<>();

        Map<String, Boolean> myMap = new HashMap<>(); //去重学院
        for(UserInfo userInfo : ordinaryUserList) {
            String college = userInfo.getCollege();
            if(myMap.get(college) != null || userInfo.getType() != 2) {
                continue;
            } else {
                myMap.put(college, true);
            }

            InfoXXX infoXXX = infoXXXDao.getInfoXXXByDate1(college, dateString, userInfo.getType());
            if(null == infoXXX) {
                continue;
            }
            if(infoXXX.getType() == 2) { //研究生
                List<String> list = new ArrayList<>();
                list.add(infoXXX.getCollege());
                list.add(infoXXX.getHead_name());
                list.add(infoXXX.getHead_phone());
                list.add(String.valueOf(infoXXX.getSum()));
                list.add(String.valueOf(infoXXX.getMinus()));
                list.add(String.valueOf(infoXXX.getAddition()));
                list.add(String.valueOf(infoXXX.getCollegesum()));
                list.add(String.valueOf(infoXXX.getOther()));
                list.add(String.valueOf(infoXXX.getTime()));
                list.add(String.valueOf(infoXXX.getRemarks()));
                result.add(list);
            }
        }
        httpServletResponse.setContentType("application/msexcel");
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateString = simpleDateFormat.format(date);
        String fileName = dateString + "晚研究生查寝情况统计表(各学院).xlsx";
        httpServletResponse.setHeader("Content-Disposition", "filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));

        DownloadService downloadService = new DownloadService();
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        downloadService.downloadPart(outputStream, result, 2);
    }

}
