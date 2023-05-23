package com.example.bookstoretest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bookstoretest.commom.Result;
import com.example.bookstoretest.entity.LendRecord;
import com.example.bookstoretest.mapper.LendRecordMapper;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@RestController
@RequestMapping("/bookstoretest/lendRecord")
// @Api(value = "BookController|书籍管理")
public class LendRecordController {
    @Autowired
    LendRecordMapper LendRecordMapper;

    @DeleteMapping("/{isbn}")
    // @ApiOperation(value = "添加图书", notes = "注入book实体类|图书编号互斥")    
    // @ApiImplicitParam(dataType = "string", name = "isbn", value = "isbn", required = true)
    public Result<?> delete(@PathVariable String isbn) {
        Map<String, Object> map = new HashMap<>();
        map.put("isbn", isbn);
        LendRecordMapper.deleteByMap(map);
        return Result.success();
    }

    // 删除一条记录
    @PostMapping("/deleteRecord")
    public Result<?> deleteRecord(@RequestBody LendRecord LendRecord) {
        Map<String, Object> map = new HashMap<>();
        map.put("isbn", LendRecord.getIsbn());
        map.put("borrownum", LendRecord.getBorrownum());
        LendRecordMapper.deleteByMap(map);
        return Result.success();
    }

    @PostMapping("/deleteRecords")
    public Result<?> deleteRecords(@RequestBody List<LendRecord> LendRecords) {
        for (LendRecord curRecord : LendRecords) {
            Map<String, Object> map = new HashMap<>();
            map.put("isbn", curRecord.getIsbn());
            map.put("borrownum", curRecord.getBorrownum());
            LendRecordMapper.deleteByMap(map);
        }
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody LendRecord LendRecord) {
        LendRecordMapper.insert(LendRecord);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search1,
            @RequestParam(defaultValue = "") String search2,
            @RequestParam(defaultValue = "") String search3) {
        LambdaQueryWrapper<LendRecord> wrappers = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(search1)) {
            wrappers.like(LendRecord::getIsbn, search1);
        }
        if (StringUtils.isNotBlank(search2)) {
            wrappers.like(LendRecord::getBookname, search2);
        }
        if (StringUtils.isNotBlank(search3)) {
            wrappers.like(LendRecord::getReaderId, search3);
        }
        // System.out.println(user);
        wrappers.orderByDesc(LendRecord::getLendTime); // 按照借阅时间最新在前排序
        Page<LendRecord> LendRecordPage = LendRecordMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return Result.success(LendRecordPage);
    }

    @PutMapping("/{isbn}")
    public Result<?> update(@PathVariable String isbn, @RequestBody LendRecord lendRecord) {
        UpdateWrapper<LendRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("isbn", isbn);
        LendRecord lendrecord = new LendRecord();
        lendrecord.setLendTime(lendRecord.getLendTime());
        lendrecord.setReturnTime(lendRecord.getReturnTime());
        lendrecord.setStatus(lendRecord.getStatus());
        LendRecordMapper.update(lendrecord, updateWrapper);
        return Result.success();
    }

    @PutMapping("/{lendTime}")
    public Result<?> update2(@PathVariable Date lendTime, @RequestBody LendRecord lendRecord) {
        UpdateWrapper<LendRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("lendTime", lendTime);
        LendRecord lendrecord = new LendRecord();
        lendrecord.setReturnTime(lendRecord.getReturnTime());
        lendrecord.setStatus(lendRecord.getStatus());
        LendRecordMapper.update(lendrecord, updateWrapper);
        return Result.success();
    }

    @PutMapping()
    public  Result<?> update3( @RequestBody LendRecord lendRecord){
        UpdateWrapper<LendRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("isbn",lendRecord.getIsbn()).eq("reader_id",lendRecord.getReaderId()).eq("borrownum",lendRecord.getBorrownum());
        LendRecord lendrecord = new LendRecord();
        lendrecord.setReturnTime(lendRecord.getReturnTime());
        lendrecord.setStatus(lendRecord.getStatus());
        LendRecordMapper.update(lendrecord, updateWrapper);
        return Result.success();
    }
}
