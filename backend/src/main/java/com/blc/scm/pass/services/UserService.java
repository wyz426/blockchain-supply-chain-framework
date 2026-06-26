package com.blc.scm.pass.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blc.scm.pass.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    Integer login(String email, String password);
//
    Integer register(String email, String password);

}
