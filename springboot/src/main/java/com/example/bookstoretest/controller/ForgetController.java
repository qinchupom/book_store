package com.example.bookstoretest.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.bookstoretest.commom.Result;
import com.example.bookstoretest.entity.User;
import com.example.bookstoretest.mapper.UserMapper;
import com.example.bookstoretest.utils.RegexUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bookstoretest/forget")
@Api(value = "BookController|书籍管理")
public class ForgetController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getcode")
    @ApiOperation(value = "添加图书", notes = "注入book实体类|图书编号互斥")
    @ApiImplicitParam(dataType = "string", name = "username", value = "username", required = true)
    public Result<?> getcode(@RequestParam String username) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return Result.error("-1", "用户名不存在");
        }
        String phone = user.getPhone();
        // 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.error("-1", "手机号错误或未绑定手机号");
        }

        String code = RandomUtil.randomNumbers(6); // 六位随机验证码

        System.out.println("六位随机验证码" + code);
        return Result.success();
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, user.getUsername());
        User user1 = userMapper.selectOne(wrapper);
        String phone = user1.getPhone();
        System.out.println(phone);
        // todo
        String code = "";

        // String code = stringRedisTemplate.opsForValue().get(phone); // 从redis中取出验证码
        // if (code == null) {
        //     return Result.error("-1", "请先获取验证码");
        // }

        if (user.getCode().equals(code)) {
            user.setId(user1.getId());
            userMapper.updateById(user);
            // stringRedisTemplate.delete(phone);
            return Result.success();
        }
        return Result.error("-1", "验证码错误");
    }

}
