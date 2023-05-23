package com.example.bookstoretest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// import javax.annotation.Resource;
import java.util.List;

import com.example.bookstoretest.commom.Result;
import com.example.bookstoretest.entity.Book;
import com.example.bookstoretest.entity.Bookwithuser;
import com.example.bookstoretest.mapper.BookMapper;
import com.example.bookstoretest.mapper.BookwithuserMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@RestController
@RequestMapping("/bookstoretest/book")
@Api(value = "BookController|书籍管理")
public class BookController {
    @Autowired
    BookMapper BookMapper;

    @Autowired
    BookwithuserMapper bookWithUserMapper;

    // 添加图书
    @PostMapping
    @ApiOperation(value = "添加图书", notes = "注入book实体类|图书编号互斥")
    @ApiImplicitParam(dataType = "Book", name = "book", value = "书籍", required = true)
    public Result<?> save(@RequestBody Book book) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getIsbn, book.getIsbn());
        Book selectOne = BookMapper.selectOne(wrapper);
        if (selectOne != null) {
            return Result.error("-1", "图书编号已存在!");
        }
        BookMapper.insert(book);
        return Result.success();
    }

    // 更新图书或添加新图书或编辑图书的条件条件是isbn条件，不然执行添加操作
    @PutMapping
    public Result<?> update(@RequestBody Book book) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getIsbn, book.getIsbn()).ne(Book::getId, book.getId());
        Book selectOne = BookMapper.selectOne(wrapper);
        if (selectOne != null) {
            return Result.error("-1", "图书编号已存在!");
        }
        BookMapper.updateById(book);
        return Result.success();
    }

    // 批量删除图书
    @PostMapping("/deleteBatch")
    @Transactional
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Book::getId, id);
            Book book = BookMapper.selectOne(wrapper);
            LambdaQueryWrapper<Bookwithuser> wrapper1 = Wrappers.lambdaQuery();
            wrapper1.eq(Bookwithuser::getIsbn, book.getIsbn());
            Bookwithuser bookWithUser = bookWithUserMapper.selectOne(wrapper1);
            if (bookWithUser != null) {
                return Result.error("-1", "书籍在借阅中,无法下架");
            }
        }
        BookMapper.deleteBatchIds(ids);
        return Result.success();
    }

    // 下架图书
    @DeleteMapping("/{id}")
    @Transactional
    public Result<?> delete(@PathVariable Long id) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getId, id);
        Book book = BookMapper.selectOne(wrapper);
        LambdaQueryWrapper<Bookwithuser> wrapper1 = Wrappers.lambdaQuery();
        wrapper1.eq(Bookwithuser::getIsbn, book.getIsbn());
        Bookwithuser bookWithUser = bookWithUserMapper.selectOne(wrapper1);
        if (bookWithUser != null) {
            return Result.error("-1", "书籍在借阅中,无法下架");
        }
        BookMapper.deleteById(id);
        return Result.success();
    }

    // 图书分页
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search1,
            @RequestParam(defaultValue = "") String search2,
            @RequestParam(defaultValue = "") String search3) {
        LambdaQueryWrapper<Book> wrappers = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(search1)) {
            wrappers.like(Book::getIsbn, search1);
        }
        if (StringUtils.isNotBlank(search2)) {
            wrappers.like(Book::getName, search2);
        }
        if (StringUtils.isNotBlank(search3)) {
            wrappers.like(Book::getAuthor, search3);
        }
        wrappers.orderByDesc(Book::getBorrownum); // 按借阅次数排序
        Page<Book> BookPage = BookMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return Result.success(BookPage);
    }

    @PostMapping("/uploadImg")
    @ApiOperation(value = "上传文件并保存", notes = "上传功能")
    public Result<?> uploadImg(@RequestParam MultipartFile file, @RequestParam String imgType) {
        System.out.println("imgType "+imgType);
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename " + originalFilename);
        return Result.success();
    }
}
