package com.yanhuo.chatplay.entity.vo;

import lombok.Data;

@Data
public class MessageVo {
    private String id;
    private String avatar;
    private String nickname;
    private String message;
    private Data time;
}
