package com.zyl.controller;

import com.zyl.service.GeneralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName：EhrController
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 19:21
 **/
@RequestMapping("/ehr")
@RestController
@Slf4j
public class EhrController {

    @Resource
    private GeneralService generalService;

    @GetMapping("/export")
//    @MethodLog
    public void exportSupInfo(HttpServletResponse response,
            @RequestParam(value = "deptCode", required = false) String deptCode, @RequestParam(value = "userCodes", required = false) List<String> userCodes){
//        return Resp.success(generalService.exportUsersSupInfo(true,null, null));
        if(!StringUtils.hasText(deptCode)){
            deptCode = "100001";
        }
        generalService.exportUsersSupInfo(response, userCodes,deptCode);
    }


}
