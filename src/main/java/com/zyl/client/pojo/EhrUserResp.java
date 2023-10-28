package com.zyl.client.pojo;

/**
 * @ClassName：EhrUserResp
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 16:35
 **/

import lombok.Data;

/**
 * EHR用户信息
 *
 * @author chenx34
 * @date 2020/4/23 14:47
 */
@Data
public class EhrUserResp {

    private String userCode;

    private String name;

    private String email;

    private String adCode;

    private String seriesCode;

    private String jobName;
}
