package com.example;

import com.example.controller.admin.history.History;
import com.example.controller.admin.history.HistoryDao;
import com.example.dao.InfoXXXDao;
import com.example.domain.POJO.InfoXXX;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LiangJingSong
 * @version 1.0
 * @date 2023/10/7 23:50
 * @description
 */
@SpringBootTest
public class MyMapperTest {
    @Autowired
    InfoXXXDao infoXXXDao;
    @Autowired
    HistoryDao historyDao;

}
