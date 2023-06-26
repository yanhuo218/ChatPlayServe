package com.yanhuo.chatplay;

import com.alibaba.fastjson.JSON;
import com.yanhuo.chatplay.common.utils.MD5;
import com.yanhuo.chatplay.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


class ChatPlayResetApplicationTests {


     Object contextLoads(Class object) {
         User user = new User();
         user.setNickname("aaa");
         user.setPassword("bbb");
         String x = JSON.toJSONString(user);
        if (x.getClass() == object){
            return x;
        }else {
            return JSON.parseObject(x, object);
        }
    }

    public static void main(String[] args) {
        String encrypt = MD5.encrypt("1");
        System.out.println(encrypt);
    }

}
