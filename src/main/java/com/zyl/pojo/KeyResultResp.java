package com.zyl.pojo;

import lombok.Data;

/**
 * @ClassName：KeyResultResp
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 12:57
 **/
@Data
public class KeyResultResp {

    private Long id;

    private String name;

    private Long relatedObjectiveId;

    private Integer sort;
}
