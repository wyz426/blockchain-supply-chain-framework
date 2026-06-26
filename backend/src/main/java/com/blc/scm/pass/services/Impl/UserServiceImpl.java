package com.blc.scm.pass.services.Impl;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blc.scm.pass.mapper.UserMapper;
import com.blc.scm.pass.models.User;
import com.blc.scm.pass.enums.Register;
import com.blc.scm.pass.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Integer login(String email, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);

        User user = userMapper.selectOne(queryWrapper);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            // user.setUpdated_time(new Date()); // 如果需要更新更新时间，取消注释此行
            userMapper.updateById(user);
            return user.getId();
        }
        return null;

    }

    @Override
    public Integer register(String email, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);

        User user = userMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(user)) {
            User new_user = new User();
            new_user.setEmail(email);
            new_user.setUser_uuid(String.valueOf(UUID.randomUUID()));//自动生成uid
            new_user.setPassword(BCrypt.hashpw(password));
            userMapper.insert(new_user);
            return Register.SUCCESS;
        } else {
            return Register.EXISTED;
        }

    }



}
