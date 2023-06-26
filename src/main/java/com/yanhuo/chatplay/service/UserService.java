package com.yanhuo.chatplay.service;

import com.yanhuo.chatplay.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanhuo.chatplay.entity.vo.LoginVo;
import com.yanhuo.chatplay.entity.vo.RegisterVo;
import com.yanhuo.chatplay.entity.vo.SettingMail;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yanhuo
 * @since 2023-04-23
 */
public interface UserService extends IService<User> {

    String login(LoginVo login);

    Boolean register(RegisterVo register);

    User getByIdUserInfo(String id);

    void updateUserById(User user);

    void updateMail(SettingMail mail,String id);
}
