package com.yanhuo.chatplay.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author yanhuo
 * @since 2023-04-23
 */
@Getter
@Setter
@ToString
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 邮箱
     */
    @TableField("mail")
    private String mail;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 账号密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别 1 女，2 男
     */
    @TableField("sex")
    private Byte sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Byte age;

    /**
     * 头像地址
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 签名
     */
    @TableField("sign")
    private String sign;

    /**
     * 是否禁用 1（true）已禁用，  0（false）未禁用
     */
    @TableField("is_disabled")
    private Boolean isDisabled;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
