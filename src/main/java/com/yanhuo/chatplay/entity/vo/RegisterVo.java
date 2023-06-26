package com.yanhuo.chatplay.entity.vo;

import lombok.Data;

@Data
public class RegisterVo {
    private String mail;
    private String code;
    private String password;
    private String nickname;
}
