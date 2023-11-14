package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

    //更新
    void update(User user);

    void updateAvatar(String avatarUrl, Integer userId);

    void updatePassword(String newPwd, String username);
}
