package com.zyl.service;

import com.alibaba.excel.EasyExcel;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zyl.client.pojo.EhrUserDetailResp;
import com.zyl.client.pojo.EhrUserResp;
import com.zyl.client.service.EhrService;
import com.zyl.pojo.SupInfo;
import com.zyl.utils.DateUtils;
import com.zyl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @ClassName：GeneralService
 * @Description： 方便些，不写impl
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 19:31
 **/
@Service
@Slf4j
public class GeneralService {

    @Resource
    private EhrService ehrService;

    @Resource
    private RedisUtils redisUtils;


    /*
     * @description: 获取全部用户信息或者指定用户信息，可以接系统内存的缓存或者redis
     * @author zhangyl31@ziroom.com
     * @date: 2023/5/4 21:02
     * @param: isAll
     * @param: userCodes
     * @return: boolean
     **/
    public void exportUsersSupInfo(HttpServletResponse response, List<String> userCodes, String deptCode){
        Preconditions.checkArgument(StringUtils.isNotEmpty(deptCode) ||  CollectionUtils.isNotEmpty(userCodes), "请指定部门或者输入指定用户");
        List<EhrUserDetailResp> userDetail = new ArrayList<>();
        try {
            userDetail = CompletableFuture.supplyAsync(() -> {
                if (StringUtils.isNotEmpty(deptCode)) {
                    Set<String> set = ehrService.getUsers(deptCode, null).stream().parallel().map(EhrUserResp::getUserCode).collect(Collectors.toSet());
                    return ehrService.getUserDetail(set, true);
                } else {
                    return ehrService.getUserDetail(new HashSet<>(userCodes), true);
                }
            }).get(2 * 60, TimeUnit.SECONDS);
        }catch (InterruptedException | TimeoutException | ExecutionException e){
            log.error("请求ehr失败", e);
        }

        if(CollectionUtils.isEmpty(userDetail)){
            log.error("查询用户列表为空");
            return ;
        }
//        final List<SupInfo> exportList = Lists.partition(userDetail, 20).parallelStream().map(userLists -> {
//            List<SupInfo> supInfoList = new ArrayList<>();
//            userLists.forEach(user -> {
//                final SupInfo supInfoBuilder = SupInfo.builder().code(user.getCode()).name(user.getName())
//                        .supName(user.getSuperEmpCode())
//                        .supCode(Optional.ofNullable(user.getSuperEmpCode()).orElse(""))
//                        .groupCode(Optional.ofNullable(user.getGroupCode()).orElse(""))
//                        .groupName(Optional.ofNullable(user.getGroupCode()).orElse(""))
//                        .empType(user.getEmpType()).email(user.getEmail()).build();
//                supInfoList.add(supInfoBuilder);
//            });
//            return supInfoList;
//        }).filter(CollectionUtils::isNotEmpty).reduce((list1, list2) -> {
//            list1.addAll(list2);
//            return list1;
//        }).orElseGet(Lists::newArrayList);

        final List<SupInfo> exportList = Lists.partition(userDetail, 20).parallelStream().map(userLists ->
                userLists.stream().map(user -> SupInfo.builder().code(user.getCode()).name(user.getName())
                .supName(user.getSuperEmpCode())
                .supCode(Optional.ofNullable(user.getSuperEmpCode()).orElse(""))
                .groupCode(Optional.ofNullable(user.getGroupCode()).orElse(""))
                .groupName(Optional.ofNullable(user.getGroupCode()).orElse(""))
                .empType(user.getEmpType()).email(user.getEmail()).build())
                        .collect(Collectors.toList())).filter(CollectionUtils::isNotEmpty).reduce((list1, list2) -> {
            list1.addAll(list2);
            return list1;
        }).orElseGet(Lists::newArrayList);

        String fileName =generalFileName("");
        try {
            if(CollectionUtils.isNotEmpty(exportList)){
//                response.setContentType("application/vnd.openxmlformats-officedocument.speadsheetml.sheet");
//                response.setCharacterEncoding("UTF-8");
//                // 这里URLEncoder.encode可以防止中文乱码, 与EasyExcel无关
////                String fileName = URLEncoder.encode("这里是Excel文件名", "UTF-8").replaceAll("\\+", "%20");
//                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
//                EasyExcel.write(response.getOutputStream(), SupInfo.class).sheet("上级").doWrite(exportList);
                EasyExcel.write(fileName, SupInfo.class).sheet("上级").doWrite(exportList);
                log.info("文件导出成功");
            }else {
                log.error("人员列表为空");
            }
        }catch (Exception e){
            log.error("导出失败");
        }

    }

    private String generalFileName(String file){
        String exportTime = DateUtils.dateToString(new Date(), "yyyyMM");
        return String.format("ehr_sup_info_{%s}_{%s}", exportTime, file);

    }

//    private List<EhrUserDetailResp> getAllUserInfo(){
//        String userInfos = JSONObject.toJSONString(redisUtils.get(RedisKeyConstants.ALL_USER_INFO_KEY));
//        AtomicReference<List<EhrUserDetailResp>> userDetail = null;
//        if(StringUtils.isEmpty(userInfos)){
//            log.error("getAllUserInfo from redis is null");
//            threadConfig.threadPoolExecutor().execute( () -> {
//                log.info("开始更新 getAllUserInfo 缓存");
//                Set<String> set = ehrService.getUsers("100001", null).parallelStream().map(EhrUserResp::getUserCode).collect(Collectors.toSet());
//                userDetail.set(ehrService.getUserDetail(set, false));
//                redisUtils.setFewHour(RedisKeyConstants.ALL_USER_INFO_KEY, JSONObject.toJSONString(userDetail), 3);
//                log.info("更新 getAllUserInfo 缓存结束");
//
//            });
//            return userDetail.get();
//        }else {
//            return JSONObject.parseArray(userInfos, EhrUserDetailResp.class);
//        }
//    }
}
