package com.example.bookstoretest.service.impl;

import com.example.bookstoretest.entity.User;
import com.example.bookstoretest.mapper.UserMapper;
import com.example.bookstoretest.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
