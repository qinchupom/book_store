package com.example.bookstoretest.mapper;

import com.example.bookstoretest.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
