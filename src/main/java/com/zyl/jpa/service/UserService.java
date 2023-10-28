package com.zyl.jpa.service;

import com.zyl.jpa.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<User> getUserByPage(User user, Integer pageNum, Integer pageSize );

    List<User> getUserAll(User user);

}
