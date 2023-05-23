package com.example.bookstoretest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@Getter
@Setter
@TableName("user")
@ApiModel(value = "User对象", description = "用户信息")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("姓名")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("电话号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("角色、1：管理员 2：普通用户")
    @TableField("role")
    private Integer role;

    @ApiModelProperty("是否允许借阅")
    @TableField("alow")
    private String alow;


    @TableField(exist = false)  //表中没有token不会报错仍能编译运行
    private String token;

    @TableField(exist = false)
    private String code;
    
    @TableField(exist = false)
    private String confirm;

    @TableField(exist = false)
    private String totp;


}
