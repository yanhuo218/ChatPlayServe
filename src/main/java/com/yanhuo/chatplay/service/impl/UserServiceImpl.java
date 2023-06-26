package com.yanhuo.chatplay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanhuo.chatplay.common.ResultCode;
import com.yanhuo.chatplay.common.exceptionhandler.ChatPlayException;
import com.yanhuo.chatplay.common.utils.JwtUtils;
import com.yanhuo.chatplay.common.utils.MD5;
import com.yanhuo.chatplay.common.utils.RedisUtil;
import com.yanhuo.chatplay.entity.User;
import com.yanhuo.chatplay.entity.vo.LoginVo;
import com.yanhuo.chatplay.entity.vo.RegisterVo;
import com.yanhuo.chatplay.entity.vo.SettingMail;
import com.yanhuo.chatplay.mapper.UserMapper;
import com.yanhuo.chatplay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yanhuo
 * @since 2023-04-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final RedisUtil redisUtil;
    private final RedisTemplate<String, String> template;

    @Autowired
    public UserServiceImpl(RedisUtil redisUtil, RedisTemplate<String, String> template) {
        this.redisUtil = redisUtil;
        this.template = template;
    }

    @Override
    public String login(LoginVo login) {
        String mail = login.getMail();
        String password = login.getPassword();
        if (ObjectUtils.isEmpty(mail) || ObjectUtils.isEmpty(password)) {
            throw new ChatPlayException(ResultCode.FAIL, "账号格式错误");
        }
        User loginUser = baseMapper.selectOne(new QueryWrapper<User>().eq("mail", mail));
        if (loginUser == null) {
            throw new ChatPlayException(ResultCode.FAIL, "账号不存在");
        }
        if (!MD5.encrypt(password).equals(loginUser.getPassword())) {
            throw new ChatPlayException(ResultCode.FAIL, "密码错误");
        }
        if (loginUser.getIsDeleted()) {
            throw new ChatPlayException(ResultCode.FAIL, "账号已封禁");
        }
        return JwtUtils.getJwtToken(loginUser.getId(), loginUser.getNickname());
    }

    @Override
    public Boolean register(RegisterVo register) {
        String mail = register.getMail();
        String nickname = register.getNickname();
        String password = register.getPassword();
        String code = register.getCode();
        if (ObjectUtils.isEmpty(mail) || ObjectUtils.isEmpty(nickname) || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(code)) {
            throw new ChatPlayException(ResultCode.FAIL, "注册信息不完整");
        }
        if (!code.equals(redisUtil.get(mail))) {
            throw new ChatPlayException(ResultCode.FAIL, "验证码错误");
        }
        User user = new User();
        user.setMail(mail);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setIsDeleted(false);
        return baseMapper.insert(user) > 0;
    }

    @Override
    @Cacheable(value = "userInfo", key = "#id", condition = "#result == null")
    public User getByIdUserInfo(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Async
    public void updateUserById(User user) {
        try {
            user.setPassword(null);
            baseMapper.updateById(user);
        } catch (ChatPlayException e) {
            throw new ChatPlayException(ResultCode.SERVER_ERROR, "更新失败");
        }
    }

    @Override
    @Async
    public void updateMail(SettingMail mail, String id) {
        try {
            User user = baseMapper.selectById(id);
            if (!Objects.equals(template.opsForValue().get(mail.getMail()), mail.getCode())) {
                throw new ChatPlayException(ResultCode.ERROR, "验证码错误");
            }
            user.setMail(mail.getMail());
            baseMapper.updateById(user);
        } catch (ChatPlayException e) {
            throw new ChatPlayException(ResultCode.SERVER_ERROR, "更新失败");
        }
    }

}
