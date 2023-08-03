package com.ziroom.zyl.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName：Students
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/1 16:02
 **/
@Data
@Table(name = "students")
@Entity
public class Students {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

}
