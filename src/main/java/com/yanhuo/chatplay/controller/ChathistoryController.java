package com.yanhuo.chatplay.controller;

import com.yanhuo.chatplay.common.R;
import com.yanhuo.chatplay.entity.Chathistory;
import com.yanhuo.chatplay.service.ChathistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yanhuo
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/chatplay/chathistory")
public class ChathistoryController {

    @Autowired
    ChathistoryService chathistoryService;

    @PostMapping("get")
    public R getAll(){
        List<Chathistory> chathistory = chathistoryService.getAll();
        return R.ok();
    }
}
