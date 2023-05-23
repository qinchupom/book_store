package com.example.bookstoretest.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bookstoretest.utils.LoginUser;
import com.example.bookstoretest.commom.Result;
import com.example.bookstoretest.entity.*;
import com.example.bookstoretest.mapper.BookMapper;
import com.example.bookstoretest.mapper.LendRecordMapper;
import com.example.bookstoretest.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookstoretest/dashboard")
// @Api(value = "BookController|书籍管理")
public class DashboardController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LendRecordMapper lendRecordMapper;
    @Autowired
    private BookMapper bookMapper;

    @GetMapping
    // @ApiOperation(value = "添加图书", notes = "注入book实体类|图书编号互斥")
    // @ApiImplicitParam()
    public Result<?> bookstoretestrecords() {
        int visitCount = LoginUser.getVisitCount();
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        int userCount = Math.toIntExact(userMapper.selectCount(queryWrapper1));
        QueryWrapper<LendRecord> queryWrapper2 = new QueryWrapper<LendRecord>();
        int lendRecordCount = Math.toIntExact(lendRecordMapper.selectCount(queryWrapper2));
        QueryWrapper<Book> queryWrapper3 = new QueryWrapper<>();
        int bookCount = Math.toIntExact(bookMapper.selectCount(queryWrapper3));
        Map<String, Object> map = new HashMap<>();// 键值对形式
        map.put("visitCount", visitCount);// 放置visitCount到map中
        map.put("userCount", userCount);
        map.put("lendRecordCount", lendRecordCount);
        map.put("bookCount", bookCount);
        return Result.success(map);
    }

}
