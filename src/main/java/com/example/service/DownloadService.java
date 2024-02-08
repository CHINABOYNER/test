package com.example.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/6 9:14
 * @description
 */
public class DownloadService {
    public void download(OutputStream outputStream, List<Map<String, Integer>> result){
        download(outputStream, result, null);
    }

    public void download(OutputStream outputStream, List<Map<String, Integer>> result, String theDate) {
        if(null == theDate) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String format = sdf.format(date);
            String[] split = format.split("-");
            //去除前面的0
            String month = split[0].replaceAll("^(0+)", "");
            String day = split[1].replaceAll("^(0+)", "");
            theDate = month + "." + day;
        } else {
            String[] split = theDate.split("-");
            //去除前面的0
            String month = split[1].replaceAll("^(0+)", "");
            String day = split[2].replaceAll("^(0+)", "");
            theDate = month + "." + day;
        }


        try {
            // 创建工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 创建工作表
            XSSFSheet sheet = workbook.createSheet("查寝情况");

            // 设置标题样式
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直居中

            // 创建合并的单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            Row titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(25); // 25磅的行高
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(theDate + "日晚查寝情况统计表");
            titleCell.setCellStyle(titleStyle);
            sheet.setColumnWidth(1, 20 * 256); // 设置列宽（第二列）

            // 创建标题行
            Row headerRow = sheet.createRow(1);
            headerRow.createCell(0).setCellValue("序号");
            headerRow.setHeightInPoints(30); // 30磅的行高

            // 创建一个单元格并设置斜线样式，将单元格分为左下角和右上角
            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("                  学生类别\n项目");
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setWrapText(true);
            headerCell2.setCellStyle(cellStyle);

            CreationHelper helper = workbook.getCreationHelper();
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            // 设置斜线的开始位置
            anchor.setCol1(1);
            anchor.setRow1(1);
            // 设置斜线的结束位置
            anchor.setCol2(2);
            anchor.setRow2(2);
            XSSFSimpleShape shape = drawing.createSimpleShape((XSSFClientAnchor) anchor);
            // 设置形状类型为线型
            shape.setShapeType(ShapeTypes.LINE);
            // 设置线宽
            shape.setLineWidth(0.5);
            // 设置线的风格
            shape.setLineStyle(0);
            // 设置线的颜色
            shape.setLineStyleColor(0, 0, 0);

            headerRow.createCell(2).setCellValue("本预科生");
            headerRow.createCell(3).setCellValue("研究生");
            headerRow.createCell(4).setCellValue("合计");

            String[] content = {"在校人数", "当日离校人数", "当日返校人数", "其他", "学生总人数"};
            int[][] content2 = {
                    {result.get(0).get("sum"), result.get(1).get("sum")},
                    {result.get(0).get("minus"), result.get(1).get("minus")},
                    {result.get(0).get("addition"), result.get(1).get("addition")},
                    {result.get(0).get("other"), result.get(1).get("other")},
                    {result.get(0).get("collegesum"), result.get(1).get("collegesum")}
            };

            // 创建数据行
            for (int i = 0; i < 5; i++) {
                Row dataRow = sheet.createRow(i + 2);
                dataRow.createCell(0).setCellValue(i + 1);
                dataRow.createCell(1).setCellValue(content[i]);

                Cell dataCell1 = dataRow.createCell(2);
                dataCell1.setCellValue(content2[i][0]);

                Cell dataCell2 = dataRow.createCell(3);
                dataCell2.setCellValue(content2[i][1]);

                Cell dataCell3 = dataRow.createCell(4);
                dataCell3.setCellValue(content2[i][0] + content2[i][1]);
            }
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void downloadPart(OutputStream outputStream, List<List<String>> result, int flag) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String format = sdf.format(date);
        String[] split = format.split("-");
        //去除前面的0
        String month = split[0].replaceAll("^(0+)", "");
        String day = split[1].replaceAll("^(0+)", "");

        String theDate = month + "." + day;

        try {
            // 创建工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();

            // 创建工作表
            XSSFSheet sheet = workbook.createSheet("查寝情况");

            // 设置标题样式
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER); //水平居中
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直居中

            // 创建合并的单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            Row titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(25); // 25磅的行高
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(theDate + "日" + (flag == 1 ? "本预科生" : "研究生") +"查寝情况");
            titleCell.setCellStyle(titleStyle);
            for(int i = 0; i < 10; i++) {
                sheet.setColumnWidth(i, 20 * 256); // 设置列宽
            }

            // 创建标题行
            Row headerRow = sheet.createRow(1);
            headerRow.setHeightInPoints(25); // 25磅的行高

            headerRow.createCell(0).setCellValue("学院");
            headerRow.createCell(1).setCellValue("填写人");
            headerRow.createCell(2).setCellValue("联系电话");

            headerRow.createCell(3).setCellValue("今日在校人数");
            headerRow.createCell(4).setCellValue("今日离校人数");
            headerRow.createCell(5).setCellValue("今日返校人数");
            headerRow.createCell(6).setCellValue("学院总人数");
            headerRow.createCell(7).setCellValue("其他");
            headerRow.createCell(8).setCellValue("填写时间");
            headerRow.createCell(9).setCellValue("备注");

            int length = result.size();
            for(int i = 0; i < length; i++) {
                Row dataRow = sheet.createRow(i + 2);
                dataRow.createCell(0).setCellValue(result.get(i).get(0)); //学院名
                dataRow.createCell(1).setCellValue(result.get(i).get(1)); //负责人姓名
                dataRow.createCell(2).setCellValue(result.get(i).get(2)); //负责人电话
                dataRow.createCell(3).setCellValue(Integer.parseInt(result.get(i).get(3))); //今日在校人数
                dataRow.createCell(4).setCellValue(Integer.parseInt(result.get(i).get(4))); //今日离校人数
                dataRow.createCell(5).setCellValue(Integer.parseInt(result.get(i).get(5))); //今日返校人数
                dataRow.createCell(6).setCellValue(Integer.parseInt(result.get(i).get(6))); //学院总人数
                dataRow.createCell(7).setCellValue(Integer.parseInt(result.get(i).get(7))); //其他
                dataRow.createCell(8).setCellValue(result.get(i).get(8)); //填写时间
                dataRow.createCell(9).setCellValue(result.get(i).get(9)); //备注
            }

            // 保存工作簿到文件
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
