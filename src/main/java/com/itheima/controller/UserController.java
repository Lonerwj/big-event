package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //查询用户名是否被占用
        User user = userService.findByUserName(username);

        if (user == null){//未被占用
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.error("用户名已被使用");
        }
    }

    @PostMapping("/login")
    public Result login(String username,String password){
        User user = userService.findByUserName(username);

        if (user == null){
            return Result.error("用户名不存在");
        }

        if (user.getPassword().equals(Md5Util.getMD5String(password))){
            HashMap<String,Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        else return Result.error("密码错误");


    }
}
