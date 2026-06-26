package com.blc.scm.pass.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blc.scm.pass.models.User;
import com.blc.scm.pass.services.Impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable("email") String email) {
        System.out.println("getUser");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);//拿account查询用户
        return userServiceImpl.getOne(queryWrapper, false);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        System.out.println(user);
        userServiceImpl.saveOrUpdate(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        user.setId(userId);
        userServiceImpl.saveOrUpdate(user);
        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userServiceImpl.removeById(userId);
    }

    /**
     * User login API.
     *
     * @param email the account of the user
     * @param password the password of the user
     * @return the ID of the logged-in user if successful, null otherwise
     */
    @PostMapping("/login")
    public Integer login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userServiceImpl.login(email, password);
    }

    /**
     * User registration API.
     *
     * @param email  the account of the user
     * @param password the password of the user
     * @return the registration status code: Register.SUCCESS if successful,
     *         Register.EXISTED if the user already exists
     */
    @PostMapping("/register")
    public Integer register(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userServiceImpl.register(email, password);
    }
}
