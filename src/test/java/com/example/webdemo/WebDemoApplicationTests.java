package com.example.webdemo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebDemoApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(WebDemoApplicationTests.class);
    @Test
    void contextLoads() {
        logger.info("可以显示中文吗？");
        System.out.println(System.getProperty("APP_NAME"));
    }

}
