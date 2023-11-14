package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户名是否被占用
        User user = userService.findByUserName(username);

        if (user == null) {//未被占用
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被使用");
        }
    }

    @PostMapping("/login")
    public Result login(String username, String password) {
        User user = userService.findByUserName(username);

        if (user == null) {
            return Result.error("用户名不存在");
        }

        if (user.getPassword().equals(Md5Util.getMD5String(password))) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtUtil.genToken(claims);
            //将token存入redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set("token",token,12, TimeUnit.HOURS);
            return Result.success(token);
        } else return Result.error("密码错误");
    }

    @PostMapping("/userInfo")
    public Result userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        if (userId.equals(user.getId())) {
            user.setUpdateTime(LocalDateTime.now());
            userService.update(user);
            return Result.success();
        } else {
            return Result.error("用户id错误");
        }
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        userService.updateAvatar(avatarUrl, userId);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> map) {
        //校验参数
        String oldPwd = map.get("old_pwd");
        String newPwd = map.get("new_pwd");
        String rePwd = map.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("密码不能为空");
        }
        //比较原密码
        Map<String,Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }
        //新密码不能与原密码一样
        if (oldPwd.equals(newPwd)){
            return Result.error("新密码不能与原密码一致");
        }
        //重复输入密码不一致
        if (!newPwd.equals(rePwd)){
            return Result.error("第二次输入密码与第一次不一致");
        }
        //修改密码
        userService.updatePassword(newPwd,username);
        //删除redis中的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete("token");
        return Result.success();
    }
}
