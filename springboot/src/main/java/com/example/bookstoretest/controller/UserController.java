package com.example.bookstoretest.controller;

import cn.hutool.core.util.RandomUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.bookstoretest.commom.Result;
import com.example.bookstoretest.entity.User;
import com.example.bookstoretest.mapper.UserMapper;
import com.example.bookstoretest.utils.RegexUtils;
import com.example.bookstoretest.utils.TokenUtils;
import com.example.bookstoretest.utils.LoginUser;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@RestController
@RequestMapping("/bookstoretest/user")
// @Api(value = "BookController|书籍管理")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/getcode")
    // @ApiOperation(value = "添加图书", notes = "注入book实体类|图书编号互斥")
    // @ApiImplicitParams({
    //         @ApiImplicitParam(dataType = "string", name = "phone", value = "phone", required = true),
    //         @ApiImplicitParam(dataType = "HttpServletResponse", name = "response", value = "response", required = true),
    //         @ApiImplicitParam(dataType = "HttpServletResponse", name = "request", value = "request", required = true)
    // })
    public Result<?> getcode(@RequestParam String phone, HttpServletResponse response, HttpServletResponse request) {

        // 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.error("-1", "手机号错误");
        }
        String code = RandomUtil.randomNumbers(6); // 六位随机验证码
        System.out.println("六位随机验证码:" + code);

        // 保存验证码到session中
        HttpSession session = ((HttpServletRequest) request).getSession();
        session.setAttribute("six_code", code);
        return Result.success();
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (res != null) {
            return Result.error("-1", "用户名已重复");
        }
        user.setNickName(user.getUsername());
        userMapper.insert(user);
        return Result.success();
    }

    @CrossOrigin
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        // 使用request对象的getSession()获取session
        HttpSession session = request.getSession();
        // 将数据存储到session中
        session.setAttribute("six_code", null);
        String token = TokenUtils.genToken(res);
        res.setPassword("123456");//掩码
        res.setToken(token);
        LoginUser.addVisitCount();
        return Result.success(res);
    }

    @PostMapping
    public Result<?> save(@RequestBody User user) {
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<?> update(@RequestParam Integer id,
            @RequestParam String password2) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        User user = new User();
        user.setPassword(password2);
        userMapper.update(user, updateWrapper);
        return Result.success();
    }

    @PutMapping
    public Result<?> password(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {

        // 从session获取code（解决缓存问题）
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("six_code");

        if (code == null) {
            return Result.error("-1", "请先获取验证码");
        }

        if (user.getCode().equals(code)) {
            userMapper.updateById(user);
            // stringRedisTemplate.delete(user.getPhone());
            return Result.success();
        }
        return Result.error("-1", "验证码错误");
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody User user) {
        // 校验手机号
        if (RegexUtils.isPhoneInvalid(user.getPhone())) {
            return Result.error("-1", "手机号格式错误");
        }
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error("-1", "授权失败,用户信息错误");
        }
        if (user.getAlow() == null) {
            user.setAlow("1");
            userMapper.updateById(user);
            return Result.success();
        }
        if (user.getAlow().equals("1")) {
            return Result.error("-2", "该用户已有借阅权限");
        }
        return Result.error("-10", "服务器错误");
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrappers = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrappers.like(User::getNickName, search);

        }
        wrappers.ne(User::getRole, 0);
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return Result.success(userPage);
    }

    @GetMapping("/usersearch")
    public Result<?> findPage2(@RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search1,
            @RequestParam(defaultValue = "") String search2,
            @RequestParam(defaultValue = "") String search3,
            @RequestParam(defaultValue = "") String search4,
            @RequestParam(defaultValue = "") String role) {
        LambdaQueryWrapper<User> wrappers = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(search1)) {
            wrappers.like(User::getId, search1);
        }
        if (StringUtils.isNotBlank(search2)) {
            wrappers.like(User::getNickName, search2);
        }
        if (StringUtils.isNotBlank(search3)) {
            wrappers.like(User::getPhone, search3);
        }
        if (StringUtils.isNotBlank(search4)) {
            wrappers.like(User::getAddress, search4);
        }
        if (StringUtils.isNotBlank(role)) {
            wrappers.eq(User::getRole, role);
        }else{
            wrappers.ne(User::getRole, 0); //如果没有传递角色参数

        }
        wrappers.orderByAsc(User::getId); // 按编号排序
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return Result.success(userPage);
    }

    @GetMapping("/alow/{id}")
    public Result<?> alow(@PathVariable Long id) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getId, id).eq(User::getAlow, "1");
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return Result.error("-1", "您没有管理员授予的借阅权!");
        }
        return Result.success();
    }
}
