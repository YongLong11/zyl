package com.ziroom.zyl.mybatisPlus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private String userName;

    private String age;
}
