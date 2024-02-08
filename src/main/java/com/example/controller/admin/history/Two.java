package com.example.controller.admin.history;

import com.alibaba.fastjson.JSONObject;
import com.example.domain.Result;
import com.example.service.DownloadService;
import com.example.util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/9/11 20:52
 * @description
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/two")
public class Two {
    @Autowired
    HistoryDao historyDao;

    @GetMapping
    public void downloadTwo(HttpServletResponse httpServletResponse) throws IOException {
        Date today = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = df.format(new Date(today.getTime() - 2 * 24 * 60 * 60 * 1000));


        History history = historyDao.getHistory(yesterday);

        List<Map<String, Integer>> result = new ArrayList<>();
        Map<String, Integer> collegeMap = new HashMap<>();
        Map<String, Integer> masterMap = new HashMap<>();

        if(null != history) {
            collegeMap.put("minus", history.getMinusben());
            collegeMap.put("addition", history.getAdditionben());
            collegeMap.put("sum", history.getSumben());
            collegeMap.put("other", history.getOtherben());
            collegeMap.put("collegesum", history.getCollegesumben());

            masterMap.put("minus", history.getMinusyan());
            masterMap.put("addition", history.getAdditionyan());
            masterMap.put("sum", history.getSumyan());
            masterMap.put("other", history.getOtheryan());
            masterMap.put("collegesum", history.getCollegesumyan());

        } else {
            collegeMap.put("minus", 0);
            collegeMap.put("addition", 0);
            collegeMap.put("sum", 0);
            collegeMap.put("other", 0);
            collegeMap.put("collegesum", 0);

            masterMap.put("minus", 0);
            masterMap.put("addition", 0);
            masterMap.put("sum", 0);
            masterMap.put("other", 0);
            masterMap.put("collegesum", 0);
        }


        result.add(collegeMap);
        result.add(masterMap);

        httpServletResponse.setContentType("application/msexcel");
        String fileName = yesterday + "查寝记录(总表).xlsx";
        fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        httpServletResponse.addHeader("Content-Disposition", "filename=" + fileName);

        DownloadService downloadService = new DownloadService();
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        downloadService.download(outputStream, result, yesterday);
    }

    @PostMapping
    public Result showTwo(@RequestHeader(value = "token", required = false) String token) {
        if(null == token) {
            return new Result(201, "请先登录");
        }
        JSONObject parse = JWT.parse(token);
        if(null == parse) {
            return new Result(201, "请先登录");
        }
        Date today = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = df.format(new Date(today.getTime() - 2 * 24 * 60 * 60 * 1000));

        History history = historyDao.getHistory(yesterday);
        if(null == history) {
            history = new History();
            history.setMinusben(0);
            history.setAdditionben(0);
            history.setSumben(0);
            history.setOtherben(0);
            history.setCollegesumben(0);
            history.setMinusyan(0);
            history.setAdditionyan(0);
            history.setSumyan(0);
            history.setOtheryan(0);
            history.setCollegesumyan(0);
        }
        history.SUMALL();
        return new Result(200, "查询成功", history);
    }
}
