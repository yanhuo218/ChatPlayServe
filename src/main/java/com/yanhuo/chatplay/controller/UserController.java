package com.yanhuo.chatplay.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanhuo.chatplay.common.R;
import com.yanhuo.chatplay.common.ResultCode;
import com.yanhuo.chatplay.common.exceptionhandler.ChatPlayException;
import com.yanhuo.chatplay.common.utils.JwtUtils;
import com.yanhuo.chatplay.common.utils.RandomUtil;
import com.yanhuo.chatplay.entity.User;
import com.yanhuo.chatplay.entity.vo.LoginVo;
import com.yanhuo.chatplay.entity.vo.RegisterVo;
import com.yanhuo.chatplay.entity.vo.SettingMail;
import com.yanhuo.chatplay.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yanhuo
 * @since 2023-04-23
 */
@CrossOrigin
@RestController
@RequestMapping("/chatplay/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginVo login) {
        try {
            String Token = userService.login(login);
            return R.ok().data("token", Token);
        } catch (ChatPlayException e) {
            return R.error().message(e.getMsg()).code(e.getCode());
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterVo register) {
        try {
            register.setNickname(RandomUtil.getRandomName());
            Boolean is = userService.register(register);
            return is ? R.ok() : R.error();
        } catch (ChatPlayException e) {
            return R.error().message(e.getMsg()).code(e.getCode());
        }
    }

    @GetMapping("isUserExist/{mail}")
    public R isMailExist(@PathVariable String mail) {
        User isExist = userService.getOne(new QueryWrapper<User>().eq("mail", mail));
        return isExist == null ? R.ok() : R.error().message("账号已存在");
    }

    @GetMapping("/getInfoById/{id}")
    public R getInfoById(@PathVariable String id, HttpServletRequest request) {
        try {
            if (null == JwtUtils.getMemberIdByJwtToken(request)) {
                throw new ChatPlayException(ResultCode.NOT_LOGGED_IN, "请先登录");
            }
            User userInfo = userService.getByIdUserInfo(id);
            if (userInfo != null) {
                userInfo.setPassword(null);
                return R.ok().data("user", userInfo);
            } else {
                throw new ChatPlayException(ResultCode.SUCCESS, "信息不存在");
            }
        } catch (ChatPlayException e) {
            return R.error().message(e.getMsg()).code(e.getCode());
        }
    }

    @PostMapping("/updateUser")
    public R updateUser(@RequestBody User user, HttpServletRequest request) {
        try {
            if (null == JwtUtils.getMemberIdByJwtToken(request)) {
                throw new ChatPlayException(ResultCode.NOT_LOGGED_IN, "请先登录");
            }
            userService.updateUserById(user);
            return R.ok();
        } catch (ChatPlayException e) {
            return R.error().message(e.getMsg()).code(e.getCode());
        }
    }

    @PostMapping("/updateMail")
    public R updateMail(@RequestBody SettingMail mail, HttpServletRequest request){
        try {
            String id = JwtUtils.getMemberIdByJwtToken(request);
            if (null == id || mail.getMail() == null || mail.getCode() == null) {
                throw new ChatPlayException(ResultCode.NOT_LOGGED_IN, "出现问题");
            }
            userService.updateMail(mail,id);
            return R.ok();
        } catch (ChatPlayException e) {
            return R.error().message(e.getMsg()).code(e.getCode());
        }
    }
}
