//package com.example;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.dao.InfoXXXDao;
//import com.example.dao.UserInfoDao;
//import com.example.domain.POJO.InfoXXX;
//import com.example.domain.POJO.UserInfo;
//import com.example.domain.Result;
//import com.example.util.JWT;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
///**
// * @author LiangJingSong
// * @version 1.0
// * @date 2023/10/3 23:26
// * @description
// */
//@SpringBootTest
//@Slf4j
//public class DataFileTest {
//    @Autowired
//    UserInfoDao userInfoDao;
//    @Autowired
//    InfoXXXDao infoXXXDao;
//
//    @Test
//    public void createAccount() throws Exception {
//        File file = new File("D:\\比特工场\\晚签系统\\test\\信息模板.xlsx");
//
//        FileInputStream fis = new FileInputStream(file);
//
//        Workbook workbook = WorkbookFactory.create(fis);
//
//        Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
//
//        boolean i = false;
//        for (Row row : sheet) {
//            if(!i) {
//                i = true;
//                continue;
//            }
//            String name = null;
//            String phone_number = null;
//            String college = null;
//            int type = 0;
//            try {
//                name = row.getCell(0).getStringCellValue();
//                phone_number = row.getCell(1).getStringCellValue();
//                college = row.getCell(2).getStringCellValue();
//                type = (int) row.getCell(3).getNumericCellValue();
//                System.out.println(name + " " + phone_number + " " + college + " " + type);
//            } catch (Exception e) {
//                break;
//            }
//            /*
//            for (Cell cell : row) {
//                // 根据单元格类型处理数据
//                switch (cell.getCellType()) {
//                    case STRING:
//                        System.out.print(cell.getStringCellValue() + "\t");
//                        break;
//                    case NUMERIC:
//                        System.out.print((long)cell.getNumericCellValue() + "\t");
//                        break;
//                    // 其他类型可以根据需要添加处理逻辑
//                }
//            }
//            */
//            String password = "scuec123456";
//            String username = generateRandomString(7);
//            UserInfo byUsername = userInfoDao.findByUsername(username);
//            while(byUsername != null) {
//                byUsername = userInfoDao.findByUsername(username);
//                System.out.println("添加失败");
//            }
//            UserInfo userInfo = new UserInfo();
//            userInfo.setUsername(username);
//            userInfo.setPassword(password);
//            userInfo.setCollege(college);
//            userInfo.setType(type);
//            userInfo.setName(name);
//            userInfo.setPhone_number(phone_number);
//            userInfoDao.insertUserInfo(userInfo);
//            infoXXXDao.createInfoXXX1(college);
//            System.out.println("添加成功");
//        }
//
//        fis.close();
//        workbook.close();
//    }
//
//    @Transactional
//    @Test
//    public void deleteAccount() {
//        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
//        ordinaryUserList.forEach(userInfo -> {
//            userInfoDao.deleteUser(userInfo.getUsername());
//            infoXXXDao.dropInfoXXX1(userInfo.getCollege());
//            System.out.println("删除成功");
//        });
//    }
//
//    @Test
//    public void insertData() throws Exception {
////        File file = new File("D:\\比特工场\\晚签系统\\test\\数据模板.xlsx");
//        File file = new File("D:\\比特工场\\晚签系统\\test\\数据模板(研究生).xlsx");
//
//        FileInputStream fis = new FileInputStream(file);
//
//        Workbook workbook = WorkbookFactory.create(fis);
//
//        Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
//
//        int i = 0;
//        for (Row row : sheet) {
//            if(i < 2) {
//                i++;
//                continue;
//            }
//            String college = row.getCell(0).getStringCellValue();
//            String name = row.getCell(1).getStringCellValue();
//            String phoneNumber = row.getCell(2).getStringCellValue();
//            int sum = (int) row.getCell(3).getNumericCellValue();
//            int minus = (int) row.getCell(4).getNumericCellValue();
//            int addition = (int) row.getCell(5).getNumericCellValue();
//            int collegesum = (int) row.getCell(6).getNumericCellValue();
//            String theTime = row.getCell(7).getStringCellValue();
//            String date = theTime.split(" ")[0];
////            String date = "2023-10-08";
//            String remarks = row.getCell(8).getStringCellValue();
//            InfoXXX infoXXX = new InfoXXX();
//            infoXXX.setMinus(minus);
//            infoXXX.setAddition(addition);
//            infoXXX.setSum(sum);
//            infoXXX.setCollegesum(collegesum);
//            infoXXX.setCollege(college);
////            infoXXX.setType(1);
//            infoXXX.setType(2);
//            infoXXX.setHead("test");
//            infoXXX.setHead_name(name);
//            infoXXX.setHead_phone(phoneNumber);
//            infoXXX.setRemarks(remarks);
//            infoXXX.setDate(date);
//            infoXXX.setTime(theTime);
//            infoXXXDao.insertInfoXXX1(college, infoXXX);
//            System.out.println("添加成功");
////            for(Cell cell : row) {
////                switch (cell.getCellType()) {
////                    case STRING:
////                        System.out.print(cell.getStringCellValue() + "(字符串)\t");
////                        break;
////                    case NUMERIC:
////                        System.out.print((long)cell.getNumericCellValue() + "(数值)\t");
////                        break;
////                    // 其他类型可以根据需要添加处理逻辑
////                }
////            }
//        }
//    }
//
//    @Test
//    public void getAccount() {
//        List<UserInfo> ordinaryUserList = userInfoDao.getOrdinaryUserList();
//        ordinaryUserList.forEach(userInfo -> {
//            System.out.println(userInfo.getUsername() + " " + userInfo.getType() + " " + userInfo.getName() + " " + userInfo.getCollege() + " " + userInfo.getPhone_number());
//        });
//    }
//
//
//
//    public static String generateRandomString(int length) {
//        String characters = "0123456789";
//        StringBuilder stringBuilder = new StringBuilder(length);
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(characters.length());
//            stringBuilder.append(characters.charAt(index));
//        }
//        return stringBuilder.toString();
//    }
//}
