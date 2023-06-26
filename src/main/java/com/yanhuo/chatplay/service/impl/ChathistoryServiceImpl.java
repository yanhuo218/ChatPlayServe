package com.yanhuo.chatplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanhuo.chatplay.entity.Chathistory;
import com.yanhuo.chatplay.mapper.ChathistoryMapper;
import com.yanhuo.chatplay.service.ChathistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yanhuo
 * @since 2023-04-23
 */
@Service
public class ChathistoryServiceImpl extends ServiceImpl<ChathistoryMapper, Chathistory> implements ChathistoryService {

    @Override
    @Cacheable(value = "chathistories", key = "#result", condition = "#result == null")
    public List<Chathistory> getAll() {
        List<Chathistory> chathistories = baseMapper.selectList(new QueryWrapper<>());
        return chathistories;
    }
}
