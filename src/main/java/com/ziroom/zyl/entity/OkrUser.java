package com.ziroom.zyl.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName：OkrUser
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 20:08
 **/
@Data
@Entity
@Table(name = "user")
public class OkrUser {



    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "email")
    private String email;

}

