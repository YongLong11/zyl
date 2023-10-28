package com.zyl.pojo;

import lombok.Data;

/**
 * @ClassName：KeyInstructionResp
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 12:55
 **/
@Data
public class KeyInstructionResp{

    private Long id;

    private String name;

    private Long relatedObjectiveId;

    private Integer sort;

    private Long dictionaryId;
}
