package com.zyl.client;

/**
 * @ClassName：ehrApi
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 16:29
 **/

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "127.0.0.1", name = "ehr")
public interface EhrApi {

    /**
     * 根据部门查询部门下所有人员
     *
     * @param deptId 部门code
     * @return 结果
     */
    @GetMapping("/api/ehr/getUsers.action?oper=1")
    JSONObject getUsers(@RequestParam("deptId") String deptId, @RequestParam("setId") Integer setId);

    /**
     * 根据用户编号查询详情
     *
     * @param userCode 用户编号
     * @return 结果
     */
    @GetMapping("/api/ehr/getUserDetail.action")
    JSONObject getUserDetail(@RequestParam("userCode") String userCode);

}

