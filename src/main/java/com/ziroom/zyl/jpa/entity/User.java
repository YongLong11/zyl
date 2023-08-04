package com.ziroom.zyl.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName：User
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/1 14:57
 **/
@Table(name = "user")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "age")
    private String age;

}
