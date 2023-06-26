package com.yanhuo.chatplay;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = {"com.yanhuo"})
@MapperScan("com.yanhuo.chatplay.mapper")
public class ChatPlayResetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatPlayResetApplication.class, args);
        log.info("启动成功");
    }

}
