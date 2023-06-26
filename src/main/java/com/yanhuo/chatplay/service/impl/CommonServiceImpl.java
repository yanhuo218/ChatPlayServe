package com.yanhuo.chatplay.service.impl;

import com.yanhuo.chatplay.common.ResultCode;
import com.yanhuo.chatplay.common.exceptionhandler.ChatPlayException;
import com.yanhuo.chatplay.service.CommonService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {


    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public CommonServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendCode(String mail, String code) {
        try {
            if (ObjectUtils.isEmpty(mail)) throw new ChatPlayException(ResultCode.ERROR, "邮箱号为空");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(mail);
            String subject = "Chat Play验证码";
            mimeMessageHelper.setSubject(subject);
            String text = "<h2>你的验证码为:[<span style='color:red;'>" + code + "</span>] 请在5分钟内使用</h2>";
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSentDate(new Date());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (ChatPlayException e) {
            throw new ChatPlayException(e.getCode(), e.getMsg());
        } catch (MessagingException ignored) {}
    }
}
