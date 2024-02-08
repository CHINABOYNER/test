//package com.example;
//
//
//import com.example.dao.InfoXXXDao;
//import com.example.dao.UserInfoDao;
//import com.example.domain.POJO.InfoXXX;
//import com.example.domain.POJO.UserInfo;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.lang.annotation.Target;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@SpringBootTest
//public class ClockInApplicationTests {
//
//    /*
//    public static void main(String[] args) {
//        try {
//            // 创建工作簿
//            XSSFWorkbook workbook = new XSSFWorkbook();
//
//            // 创建工作表
//            XSSFSheet sheet = workbook.createSheet("查寝情况");
//
//            // 设置标题样式
//            CellStyle titleStyle = workbook.createCellStyle();
//            titleStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中
//            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直居中
//
//            // 创建合并的单元格
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
//            Row titleRow = sheet.createRow(0);
//            titleRow.setHeightInPoints(25); // 25磅的行高
//            Cell titleCell = titleRow.createCell(0);
//            titleCell.setCellValue("9.4日查寝情况");
//            titleCell.setCellStyle(titleStyle);
//            sheet.setColumnWidth(1, 20 * 256); // 设置列宽（第二列）
//
//            // 创建标题行
//            Row headerRow = sheet.createRow(1);
//            headerRow.createCell(0).setCellValue("序号");
//            headerRow.setHeightInPoints(30); // 30磅的行高
//
//            // 创建一个单元格并设置斜线样式，将单元格分为左下角和右上角
//            Cell headerCell2 = headerRow.createCell(1);
//            headerCell2.setCellValue("                  学生类别\n项目");
//            XSSFCellStyle cellStyle = workbook.createCellStyle();
//            cellStyle.setWrapText(true);
//            headerCell2.setCellStyle(cellStyle);
//
//            CreationHelper helper = workbook.getCreationHelper();
//            XSSFDrawing drawing = sheet.createDrawingPatriarch();
//            ClientAnchor anchor = helper.createClientAnchor();
//            // 设置斜线的开始位置
//            anchor.setCol1(1);
//            anchor.setRow1(1);
//            // 设置斜线的结束位置
//            anchor.setCol2(2);
//            anchor.setRow2(2);
//            XSSFSimpleShape shape = drawing.createSimpleShape((XSSFClientAnchor) anchor);
//            // 设置形状类型为线型
//            shape.setShapeType(ShapeTypes.LINE);
//            // 设置线宽
//            shape.setLineWidth(0.5);
//            // 设置线的风格
//            shape.setLineStyle(0);
//            // 设置线的颜色
//            shape.setLineStyleColor(0, 0, 0);
//
//            headerRow.createCell(2).setCellValue("本科生");
//            headerRow.createCell(3).setCellValue("研究生");
//            headerRow.createCell(4).setCellValue("合计");
//
//            String[] content = {"在校人数", "当日离校人数", "当日返校人数"};
//            // 创建数据行
//            for (int i = 0; i < 3; i++) {
//                Row dataRow = sheet.createRow(i + 2);
//                dataRow.createCell(0).setCellValue(i + 1);
//                dataRow.createCell(1).setCellValue(content[i]);
//
//                Cell dataCell1 = dataRow.createCell(2);
//                dataCell1.setCellValue(400);
//
//                Cell dataCell2 = dataRow.createCell(3);
//                dataCell2.setCellValue(500);
//
//                Cell dataCell3 = dataRow.createCell(4);
//                dataCell3.setCellValue(900);
//            }
//
//            // 保存工作簿到文件
//            FileOutputStream fileOut = new FileOutputStream("D:\\data\\temp\\查寝情况.xlsx");
//            workbook.write(fileOut);
//            fileOut.close();
//
//            System.out.println("Excel文件已成功创建！");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    */
//
//
//    @Test
//    public void test() {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//        String format = sdf.format(date);
////        String format = "10-11";
//        String[] split = format.split("-");
//        //去除前面的0
//        String month = split[0].replaceAll("^(0+)", "");
//        String day = split[1].replaceAll("^(0+)", "");
//
//        System.out.println(month + "." + day);
//    }
//
//    @Autowired
//    UserInfoDao userInfoDao;
//    @Autowired
//    InfoXXXDao infoXXXDao;
//    @Test
//    public void test2() {
//        UserInfo existed = userInfoDao.isExisted("20211234", "123456");
//        System.out.println(existed);
//    }
//
//    @Test
//    public void test3() {
//        Date date = new Date();
//        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("今天的日期："+df.format(date));
//        System.out.println("6天前的日期：" + df.format(new Date(date.getTime() - 6 * 24 * 60 * 60 * 1000)));
//        System.out.println("三天后的日期：" + df.format(new Date(date.getTime() + 3 * 24 * 60 * 60 * 1000)));
//    }
//
//
//    @Test
//    public void test4() {
//        try {
//            // 创建工作簿
//            XSSFWorkbook workbook = new XSSFWorkbook();
//
//            // 创建工作表
//            XSSFSheet sheet = workbook.createSheet("查寝情况");
//
//            // 设置标题样式
//            CellStyle titleStyle = workbook.createCellStyle();
//            titleStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中
//            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直居中
//
//            // 创建合并的单元格
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
//            Row titleRow = sheet.createRow(0);
//            titleRow.setHeightInPoints(25); // 25磅的行高
//            Cell titleCell = titleRow.createCell(0);
//            titleCell.setCellValue("9.4日本科生查寝情况");
//            titleCell.setCellStyle(titleStyle);
//            for(int i = 0; i < 8; i++) {
//                sheet.setColumnWidth(i, 20 * 256); // 设置列宽
//            }
//
//            // 创建标题行
//            Row headerRow = sheet.createRow(1);
//            headerRow.setHeightInPoints(25); // 25磅的行高
//
//            headerRow.createCell(0).setCellValue("学院");
//            headerRow.createCell(1).setCellValue("填写人");
//            headerRow.createCell(2).setCellValue("联系电话");
//
//            headerRow.createCell(3).setCellValue("今日在校人数");
//            headerRow.createCell(4).setCellValue("今日离校人数");
//            headerRow.createCell(5).setCellValue("今日返校人数");
//            headerRow.createCell(6).setCellValue("学院总人数");
//            headerRow.createCell(7).setCellValue("备注");
////
////            String[] content = {"在校人数", "当日离校人数", "当日返校人数"};
////            // 创建数据行
////            for (int i = 0; i < 3; i++) {
////                Row dataRow = sheet.createRow(i + 2);
////                dataRow.createCell(0).setCellValue(i + 1);
////                dataRow.createCell(1).setCellValue(content[i]);
////
////                Cell dataCell1 = dataRow.createCell(2);
////                dataCell1.setCellValue(400);
////
////                Cell dataCell2 = dataRow.createCell(3);
////                dataCell2.setCellValue(500);
////
////                Cell dataCell3 = dataRow.createCell(4);
////                dataCell3.setCellValue(900);
////            }
//
//            for(int i = 0; i < 10; i++) {
//                Row dataRow = sheet.createRow(i + 2);
//                dataRow.createCell(0).setCellValue("计算机学院");
//                dataRow.createCell(1).setCellValue("张三");
//                dataRow.createCell(2).setCellValue("15566668888");
//                dataRow.createCell(3).setCellValue(677);
//                dataRow.createCell(4).setCellValue(400);
//                dataRow.createCell(5).setCellValue(500);
//                dataRow.createCell(6).setCellValue(900);
//                dataRow.createCell(7).setCellValue("无");
//            }
//
//            // 保存工作簿到文件
//            FileOutputStream fileOut = new FileOutputStream("D:\\data\\temp\\查寝情况.xlsx");
//            workbook.write(fileOut);
//            fileOut.close();
//
//            System.out.println("Excel文件已成功创建！");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test5() {
//        Integer integer = Integer.valueOf("55");
//        System.out.println(integer);
//    }
//}
//
