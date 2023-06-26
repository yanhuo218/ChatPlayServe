package com.yanhuo.chatplay.controller;

import com.yanhuo.chatplay.common.R;
import com.yanhuo.chatplay.common.exceptionhandler.ChatPlayException;
import com.yanhuo.chatplay.common.utils.RandomUtil;
import com.yanhuo.chatplay.common.utils.RedisUtil;
import com.yanhuo.chatplay.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/chatplay/common")
public class CommonController {
    private final RedisUtil redisUtil;
    private final CommonService commonService;

    @Autowired
    public CommonController(CommonService commonService, RedisUtil redisUtil) {
        this.commonService = commonService;
        this.redisUtil = redisUtil;
    }

    @GetMapping("/getCode/{mail}")
    public R getCode(@PathVariable String mail) {
        try {
            if (!ObjectUtils.isEmpty(redisUtil.get(mail))) return R.ok();
            String code = RandomUtil.getFourBitRandom();
            commonService.sendCode(mail, code);
            log.info(mail + ":--:" + code);
            redisUtil.set(mail, code, 5 * 60);
            return R.ok();
        } catch (ChatPlayException e) {
            return R.error().code(e.getCode()).message(e.getMsg());
        }
    }
}
