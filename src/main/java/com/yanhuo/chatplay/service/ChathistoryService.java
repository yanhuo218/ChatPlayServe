package com.yanhuo.chatplay.service;

import com.yanhuo.chatplay.entity.Chathistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yanhuo
 * @since 2023-04-23
 */
public interface ChathistoryService extends IService<Chathistory> {

    List<Chathistory> getAll();
}
