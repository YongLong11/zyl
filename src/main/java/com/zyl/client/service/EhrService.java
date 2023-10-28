package com.zyl.client.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zyl.client.EhrApi;
import com.zyl.client.pojo.EhrUserDetailResp;
import com.zyl.client.pojo.EhrUserResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName：EhrService
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 16:33
 **/
@Service
@Slf4j
public class EhrService {

    /**
     * ehr响应中的错误信息属性名
     */
    private final static String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    /**
     * ehr响应中的错误码属性名
     */
    private final static String ERROR_CODE_ATTRIBUTE = "errorCode";
    private final static String SUCCESS = "success";
    /**
     * ehr响应中的数据属性名
     */
    private final static String DATA_ATTRIBUTE = "data";
    /**
     * ehr响应中的状态属性名
     */
    private final static String STATUS_ATTRIBUTE = "status";
    private final static String DEFAULT_SETID = "101";

    @Resource
    private EhrApi ehrApi;

    /**
     * 通过部门编码获取部门下的人员列表
     * 如果部门主管在该部门下不是主职的话，不会返回
     *
     * @param deptId 部门编码
     * @return Set<EhrUserResp>
     */
    public Set<EhrUserResp> getUsers(String deptId, Integer setId) {
        log.info("EhrService.getUsers params:{}", deptId);
        JSONObject response = ehrApi.getUsers(deptId, null);
        if (response.getInteger(ERROR_CODE_ATTRIBUTE) != 0) {
            String errorMessage = response.getString(ERROR_MESSAGE_ATTRIBUTE);
            log.error("EhrService.getUsers has occurred error message: {}", errorMessage);
            return Sets.newHashSet();
        }
        JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
        Set<EhrUserResp> userSet = new HashSet<>();
        for (int i = 0, length = data.size(); i < length; i++) {
            JSONObject ehrUser = data.getJSONObject(i);
            EhrUserResp ehrUserResp = new EhrUserResp();
            ehrUserResp.setName(ehrUser.getString("name"));
            ehrUserResp.setUserCode(ehrUser.getString("username"));
            userSet.add(ehrUserResp);
        }
        log.info("EhrService.getUsers request success result:{}", userSet);
        return userSet;
    }


    public List<EhrUserDetailResp> getUserDetail(Set<String> userCodes, boolean isOnlyPJob) {
        log.info("EhrService.getUserDetail params:{}", userCodes);
        return Lists.partition(new ArrayList<>(userCodes), 10).parallelStream().map(list -> {
            JSONObject response = ehrApi.getUserDetail(list.toString());

            if (Objects.equals(response.getString(STATUS_ATTRIBUTE), SUCCESS)) {
                JSONArray data = response.getJSONArray(DATA_ATTRIBUTE);
                List<EhrUserDetailResp> userList = Lists.newArrayList();
                for (int i = 0, length = data.size(); i < length; i++) {
                    JSONObject ehrUser = data.getJSONObject(i);
                    EhrUserDetailResp ehrUserDetailResp = new EhrUserDetailResp();
                    ehrUserDetailResp.setJobIndicator(ehrUser.getString("jobIndicator"));
                    ehrUserDetailResp.setEmail(ehrUser.getString("email"));
                    ehrUserDetailResp.setAvatar(ehrUser.getString("photo"));
                    ehrUserDetailResp.setCode(ehrUser.getString("emplid"));
                    ehrUserDetailResp.setName(ehrUser.getString("name"));
                    ehrUserDetailResp.setGroupCode(ehrUser.getString("groupCodeNew"));
                    ehrUserDetailResp.setGroupName(ehrUser.getString("group"));
                    ehrUserDetailResp.setTreePath(ehrUser.getString("treePath"));
                    ehrUserDetailResp.setLevelName(ehrUser.getString("levelName"));
                    ehrUserDetailResp.setCenter(ehrUser.getString("center"));
                    ehrUserDetailResp.setCenterId(ehrUser.getString("centerCode"));
                    ehrUserDetailResp.setDesc(ehrUser.getString("descr"));
                    ehrUserDetailResp.setCityCodeNew(ehrUser.getString("cityCodeNew"));
                    ehrUserDetailResp.setUserType(ehrUser.getString("userType"));
                    ehrUserDetailResp.setEmpType(ehrUser.getString("empType"));
                    ehrUserDetailResp.setSuperEmpCode(ehrUser.getString("superEmpCode"));
                    ehrUserDetailResp.setCenterManagerEmpId(ehrUser.getString("centerManagerEmpId"));
                    if (ehrUser.getString("adCode").equals("xionglin")) {
                        ehrUserDetailResp.setDeptCode("100010");
                        ehrUserDetailResp.setDeptName("自如总部");
                    } else {
                        ehrUserDetailResp.setDeptCode(Optional.ofNullable(ehrUser.getString("treePath"))
                                .map(x -> x.toString().split(",").length <= 2 ?
                                        x.toString().split(",")[1] : x.toString().split(",")[2]).orElse(StringUtils.EMPTY));
                        ehrUserDetailResp.setDeptName(Optional.ofNullable(ehrUser.getString("orgDesc"))
                                .map(x -> x.toString().split("\\.")[0]).orElse(StringUtils.EMPTY));
                    }
                    if (isOnlyPJob) {
                        if (Objects.equals(ehrUserDetailResp.getJobIndicator(), "P")) {
                            userList.add(ehrUserDetailResp);
                        }
                    } else {
                        userList.add(ehrUserDetailResp);
                    }
                }
                return userList;
            }
            return null;
        }).filter(CollectionUtils::isNotEmpty).reduce((list1, list2) -> {
            list1.addAll(list2);
            return list1;
        }).orElseGet(Lists::newArrayList);
    }
}
