package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.domain.Result;
import com.example.util.GetUserAuthRes;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/1 18:15
 * @description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/test")
public class Test {
    @GetMapping
    public void export(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/msexcel");
        String fileName = "test.xlsx";
        fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        httpServletResponse.addHeader("Content-Disposition", "filename=" + fileName);

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
            titleCell.setCellValue("9.4日查寝情况");
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

            headerRow.createCell(2).setCellValue("本科生");
            headerRow.createCell(3).setCellValue("研究生");
            headerRow.createCell(4).setCellValue("合计");

            String[] content = {"在校人数", "当日离校人数", "当日返校人数"};
            // 创建数据行
            for (int i = 0; i < 3; i++) {
                Row dataRow = sheet.createRow(i + 2);
                dataRow.createCell(0).setCellValue(i + 1);
                dataRow.createCell(1).setCellValue(content[i]);

                Cell dataCell1 = dataRow.createCell(2);
                dataCell1.setCellValue(400);

                Cell dataCell2 = dataRow.createCell(3);
                dataCell2.setCellValue(500);

                Cell dataCell3 = dataRow.createCell(4);
                dataCell3.setCellValue(900);
            }
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @PutMapping
    public Result test2(@RequestBody JSONObject data) {
        //获取时间，规定格式为 yyyy-MM-dd HH:mm:ss
        Date date = new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);

        String openid = data.getString("openid");
        JSONArray json = new JSONArray();
        JSONObject json1 = new JSONObject();
        json1.put("value", time);
        JSONObject json2 = new JSONObject();
        json2.put("value", "测试");
        JSONObject json3 = new JSONObject();
        json3.put("value", "梁靖松");


        Map<String, JSONObject> map = new HashMap<>();
        map.put("date2", json1);
        map.put("thing4", json2);
        map.put("name3", json3);

        JSONObject accessToken = null;
        try {
            accessToken = GetUserAuthRes.getAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert accessToken != null;
        String access_token = accessToken.getString("access_token");

        try {
            GetUserAuthRes.sendMessage(openid, access_token, (JSONObject) JSON.toJSON(map));
            System.out.println("发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PatchMapping
    public Result test3() {
        try {
            JSONObject accessToken = GetUserAuthRes.getAccessToken();
            return new Result(200, "success", accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
