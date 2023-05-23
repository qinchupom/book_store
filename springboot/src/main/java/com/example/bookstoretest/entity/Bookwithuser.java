package com.example.bookstoretest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import java.util.Date;
/**
 * <p>
 * 
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@Getter
@Setter
@Data
@TableName("bookwithuser")
@ApiModel(value = "Bookwithuser对象", description = "")
public class Bookwithuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 读者id
     */
    @ApiModelProperty("读者id")
    @TableField("id")
    private Long id;

    /**
     * 图书编号
     */
    @ApiModelProperty("图书编号")
    @TableField("isbn")
    private String isbn;

    /**
     * 图书名
     */
    @ApiModelProperty("图书名")
    @TableId("book_name")
    private String bookName;

    /**
     * 读者姓名
     */
    @ApiModelProperty("读者姓名")
    @TableField("nick_name")
    private String nickName;

    /**
     * 借阅时间
     */
    @ApiModelProperty("借阅时间")
    @TableField("lendtime")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date lendtime;

    /**
     * 应归还时间
     */
    @ApiModelProperty("应归还时间")
    @TableField("deadtime")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date deadtime;

    /**
     * 续借次数
     */
    @ApiModelProperty("续借次数")
    @TableField("prolong")
    private Integer prolong;


}
