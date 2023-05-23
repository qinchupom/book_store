package com.example.bookstoretest.mapper;

import com.example.bookstoretest.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zxy
 * @since 2023-02-24 12:19:39
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
