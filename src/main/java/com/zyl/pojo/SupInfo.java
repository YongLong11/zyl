package com.zyl.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：SupInfo
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 19:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupInfo {
    String email;
    String code;
    String name;
    String supCode;
    String supName;
    String groupCode;
    String groupName;
    String empType;
}
