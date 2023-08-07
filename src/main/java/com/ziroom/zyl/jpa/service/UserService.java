package com.ziroom.zyl.jpa.service;

import com.ziroom.zyl.jpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserService {
    Page<User> getUserByPage(User user, Integer pageNum, Integer pageSize );

    List<User> getUserAll(User user);

}
