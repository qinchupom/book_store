package com.example.bookstoretest.service.impl;

import com.example.bookstoretest.entity.Book;
import com.example.bookstoretest.mapper.BookMapper;
import com.example.bookstoretest.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
