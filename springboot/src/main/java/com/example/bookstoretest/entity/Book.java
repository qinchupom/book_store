package com.example.bookstoretest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("book")
@ApiModel(value = "Book对象", description = "图书列表")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 图书编号
     */
    @ApiModelProperty("图书编号")
    @TableField("isbn")
    private String isbn;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    @TableField("price")
    private BigDecimal price;

    /**
     * 作者
     */
    @ApiModelProperty("作者")
    @TableField("author")
    private String author;

    /**
     * 出版社
     */
    @ApiModelProperty("出版社")
    @TableField("publisher")
    private String publisher;

    /**
     * 出版时间
     */
    @ApiModelProperty("出版时间")
    @TableField("create_time")
    @JsonFormat(locale="zh",timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date createTime;

    /**
     * 0：未归还 1：已归还
     */
    @ApiModelProperty("0：未归还 1：已归还")
    @TableField("status")
    private String status;

    /**
     * 此书被借阅次数
     */
    @ApiModelProperty("此书被借阅次数")
    @TableField("borrownum")
    private Integer borrownum;


}
