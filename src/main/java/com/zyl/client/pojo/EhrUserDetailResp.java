package com.zyl.client.pojo;

import lombok.Data;

/**
 * @ClassName：EhrUserDetailResp
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 19:00
 **/
@Data
public class EhrUserDetailResp {

//    @ApiModelProperty("员工姓名")
    private String name;

//    @ApiModelProperty("系统号")
    private String code;

//    @ApiModelProperty("邮箱地址")
    private String email;

//    @ApiModelProperty("头像地址")
    private String avatar;

//    @ApiModelProperty("组织架构代码")
    private String groupCode;

//    @ApiModelProperty("组织机构名称")
    private String groupName;

//    @ApiModelProperty("部门树的树路径")
    private String treePath;

//    @ApiModelProperty("主职标识")
    private String jobIndicator;

//    @ApiModelProperty("职级名称")
    private String levelName;

//    @ApiModelProperty("部门名称")
    private String deptName;

//    @ApiModelProperty("部门code")
    private String deptCode;

//    @ApiModelProperty("中心名称")
    private String center;

//    @ApiModelProperty("中心id")
    private String centerId;

//    @ApiModelProperty("职务描述")
    private String desc;

//    @ApiModelProperty("城市")
    private String cityCodeNew;

    private String userType;

    private String empType;

//    @ApiModelProperty("职务序列")
    private String jobSeries;

//    @ApiModelProperty("职等编码")
    private String gradeCode;

    private String superEmpCode;

    private String centerManagerEmpId;
}
