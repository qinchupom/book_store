package com.example.bookstoretest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("lend_record")
@ApiModel(value = "LendRecord对象", description = "借阅记录")
public class LendRecord implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("读者id")
    @TableField("reader_id")
    private Long readerId;

    @ApiModelProperty("图书编号")
    @TableField("isbn")
    private String isbn;

    @ApiModelProperty("图书名")
    @TableField("bookname")
    private String bookname;

    @ApiModelProperty("借书日期")
    @TableField("lend_time")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date lendTime;

    @ApiModelProperty("还书日期")
    @TableField("return_time")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    @ApiModelProperty("0：未归还 1：已归还")
    @TableField("status")
    private String status;

    @ApiModelProperty("此书被借阅次数")
    @TableField("borrownum")
    private Integer borrownum;








}
