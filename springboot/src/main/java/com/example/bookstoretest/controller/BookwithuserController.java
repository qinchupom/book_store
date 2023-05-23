package com.example.bookstoretest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bookstoretest.commom.Result;
import com.example.bookstoretest.entity.Book;
import com.example.bookstoretest.entity.Bookwithuser;
import com.example.bookstoretest.entity.LendRecord;
import com.example.bookstoretest.mapper.BookMapper;
import com.example.bookstoretest.mapper.BookwithuserMapper;
import com.example.bookstoretest.mapper.LendRecordMapper;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@RestController
@RequestMapping("/bookstoretest/bookwithuser")
// @Api(value = "BookController|书籍管理")
public class BookwithuserController {
    @Autowired
    BookwithuserMapper BookwithuserMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    LendRecordMapper lendRecordMapper;


    @PostMapping("/insertNew")
    // @ApiOperation(value = "添加图书", notes = "注入book实体类|图书编号互斥")    
    // @ApiImplicitParam(dataType = "Bookwithuser", name = "Bookwithuser", value = "Bookwithuser", required = true)
    public Result<?> insertNew(@RequestBody Bookwithuser Bookwithuser){
        BookwithuserMapper.insert(Bookwithuser);
        return Result.success();
    }
    @PostMapping
    public Result<?> update(@RequestBody Bookwithuser Bookwithuser){
        UpdateWrapper<Bookwithuser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("isbn",Bookwithuser.getIsbn()).eq("id",Bookwithuser.getId());
        BookwithuserMapper.update(Bookwithuser, updateWrapper);
        return Result.success();
    }
//删除一条记录
    @PostMapping("/deleteRecord")
    @Transactional
    public  Result<?> deleteRecord(@RequestBody Bookwithuser Bookwithuser){
        Map<String,Object> map = new HashMap<>();
        map.put("isbn",Bookwithuser.getIsbn());
        map.put("id",Bookwithuser.getId());
        BookwithuserMapper.deleteByMap(map);
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Book::getIsbn,Bookwithuser.getIsbn());
        Book book = bookMapper.selectOne(wrapper);
        book.setStatus("1"); //归还
        bookMapper.updateById(book);
        return Result.success();
    }

    @PostMapping("/deleteRecords")
    @Transactional
    public Result<?> deleteRecords(@RequestBody List<Bookwithuser> Bookwithusers){
        Date date = new java.util.Date();//获取当前时间对象，也可以直接传入Date的对象
        for (Bookwithuser curRecord : Bookwithusers) {
            Map<String, Object> map = new HashMap<>();
            map.put("isbn", curRecord.getIsbn());
            map.put("id", curRecord.getId());
            BookwithuserMapper.deleteByMap(map);
            LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Book::getIsbn,curRecord.getIsbn());
            Book book = bookMapper.selectOne(wrapper);
            book.setStatus("1"); //归还
            bookMapper.updateById(book);
            LambdaQueryWrapper<LendRecord> wrapper1 = Wrappers.lambdaQuery();
            wrapper1.eq(LendRecord::getReaderId,curRecord.getId()).eq(LendRecord::getIsbn,curRecord.getIsbn()).eq(LendRecord::getStatus,"0");
            LendRecord lendRecord = lendRecordMapper.selectOne(wrapper1);
            lendRecord.setStatus("1");  //归还
            lendRecord.setReturnTime(date);
            lendRecordMapper.updateById(lendRecord);
        }
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search2,
                              @RequestParam(defaultValue = "") String search3){
        LambdaQueryWrapper<Bookwithuser> wrappers = Wrappers.<Bookwithuser>lambdaQuery();
        if(StringUtils.isNotBlank(search1)){
            wrappers.like(Bookwithuser::getIsbn,search1);
        }
        if(StringUtils.isNotBlank(search2)){
            wrappers.like(Bookwithuser::getBookName,search2);
        }
        if(StringUtils.isNotBlank(search3)){
            wrappers.like(Bookwithuser::getId,search3);
        }
        wrappers.orderByDesc(Bookwithuser::getLendtime);   //按借阅时间排序
        Page<Bookwithuser> BookPage =BookwithuserMapper.selectPage(new Page<>(pageNum,pageSize), wrappers);
        return Result.success(BookPage);
    }
}
