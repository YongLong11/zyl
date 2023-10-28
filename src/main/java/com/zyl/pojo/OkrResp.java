package com.zyl.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName：OkrResp
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/2 12:57
 **/
@Data(staticConstructor = "build")
@Accessors(chain = true)
public class OkrResp {
    private Long id;

    private String name;

    private Integer weight;

    private Integer isDesensitization;

    private Integer achievingRate;

    private Long parentObjectiveId;

    private String parentObjectName;

    private String parentCreateId;

    private String parentCreateName;

    private Integer confidence;

    private Long objectiveTypeId;

    private String objectiveTypeName;

    private String explanation;

    private List<KeyInstructionResp> kiRespList;

    private List<KeyResultResp> krRespList;

    private Integer sort;

    private List<AlignInfoVo> alignInfoVos;
}
