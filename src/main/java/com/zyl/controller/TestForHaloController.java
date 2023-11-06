package com.zyl.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zyl.common.Resp;
import com.zyl.pojo.SendEmailDto;
import com.zyl.service.SendEmailService;
import com.zyl.something.http.HttpUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import org.xm.similarity.text.CosineSimilarity;
import org.xm.similarity.text.TextSimilarity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取 /open 接口，获取类型，获取参数，参数赋值，参数组合，header组合，发起请求
 */
@RequestMapping("/halo")
@RestController
public class TestForHaloController {

    @Resource
    SendEmailService sendEmailService;

    @GetMapping("/write-execl")
    public Resp writeExecl(){
        String url = "http://tech-halo.kt.ziroom.com/v2/api-docs";
        String body = HttpUtil.get(url, null, null);
        List<Api> allApis = getApis(body);
        if (CollectionUtils.isEmpty(allApis)) {
            return Resp.success();
        }
        Map<String, Object> paramsMap = generatorParamsValue();
        List<Api> openApis = allApis.stream()
                // 只保留 get 请求， post 参数不好构造
                .filter(api -> api.getUrl().startsWith("/open") && "get".equalsIgnoreCase(api.getRequestType()))
                .collect(Collectors.toList());
        Map<String, Pair<Api, Api>> newAndOldMap = getNewAndOldMap(allApis, openApis);
        List<CaseDto> caseDtoList = openApis.stream().flatMap(api -> {
            List<Params> mustParams = api.getMustParams();
            List<Params> noMustParams = api.getNoMustParams();
            List<List<Params>> lists = generateCombinations(noMustParams);
            List<Params> mustParamWithValue = mustParams.stream().map(par -> par.setValue(paramsMap.get(par.getName()))).collect(Collectors.toList());
            api.setMustParams(mustParamWithValue);
            return lists.stream().map(list -> {
                List<Params> noParamWithValue = list.stream().map(par -> par.setValue(paramsMap.get(par.getName()))).collect(Collectors.toList());
                api.setNoMustParams(noParamWithValue);
                String step = generatorStep(mustParamWithValue) + generatorStep(noParamWithValue);
                String caseName = api.getUrl() + generatorCaseName(mustParamWithValue) + generatorCaseName(noParamWithValue);
                Pair<Api, Api> apiApiPair = newAndOldMap.get(api.getUrl());
                String result = "数据结构同旧接口";
                if (Objects.nonNull(apiApiPair)) {
                    Api oldApi = apiApiPair.getRight();
                    result = result + "--" + oldApi.getUrl();
                }
                return CaseDto.of().setCaseName(caseName).setStep(step).setResult(result);
            });
        }).collect(Collectors.toList());
        String fileName = "/Users/zhangxiaolong/Downloads/halo-权限升级用例.xlsx";
        EasyExcel.write(fileName).sheet(0).doWrite(caseDtoList);
        return Resp.success();
    }

    private String generatorStep(List<Params> params){
        StringBuilder stringBuilder = new StringBuilder();
        for (Params param : params) {
            stringBuilder.append(param.getName()).append(param.getValue()).append(";");
        }
        return stringBuilder.toString();
    }

    private String generatorCaseName(List<Params> params){
        StringBuilder stringBuilder = new StringBuilder();
        for (Params param : params) {
            stringBuilder.append(param.getName()).append(";");
        }
        return stringBuilder.toString();
    }

    @GetMapping("/test/halo-api")
    public Resp testHalo() throws Exception{
        String url = "http://tech-halo.kt.ziroom.com/v2/api-docs";
        String body = HttpUtil.get(url, null, null);
        List<Api> allApis = getApis(body);
        if (CollectionUtils.isEmpty(allApis)) {
            return Resp.success();
        }
        List<SendMessageDto> msgs = new ArrayList<>();
        List<Api> openApis = allApis.stream()
                // 只保留 get 请求， post 参数不好构造
                .filter(api -> api.getUrl().startsWith("/open") && "get".equalsIgnoreCase(api.getRequestType()))
                .collect(Collectors.toList());
        Map<String, Pair<Api, Api>>  newAndOldMap = getNewAndOldMap(allApis, openApis);
        Map<String, Object> paramsMap = generatorParamsValue();

        for (Map.Entry<String, Pair<Api, Api>> entry : newAndOldMap.entrySet()) {
            Pair<Api, Api> value = entry.getValue();
            Api openApi = value.getLeft();
            Api oldApi = value.getRight();
            //
            List<Params> noMustParams = openApi.getNoMustParams().stream().map(par -> par.setValue(paramsMap.get(par.getName()))).collect(Collectors.toList());
            List<Params> mustParams = openApi.getMustParams().stream().map(par -> par.setValue(paramsMap.get(par.getName()))).collect(Collectors.toList());
            openApi.setMustParams(mustParams);
            oldApi.setMustParams(mustParams);
            List<List<Params>> lists = generateCombinations(noMustParams);
            for (List<Params> list : lists) {
                try {
                    List<Params> newParams = list.stream().map(par -> par.setValue(paramsMap.get(par.getName()))).collect(Collectors.toList());
                    openApi.setNoMustParams(newParams);
                    String openApiResult = runApi(openApi, true);
                    oldApi.setNoMustParams(newParams);
                    String oldApiResult = runApi(oldApi, false);
                    TextSimilarity cosSimilarity = new CosineSimilarity();
                    double similarity = cosSimilarity.getSimilarity(openApiResult, oldApiResult);
                    List<Params> allParams = new ArrayList<>();
                    allParams.addAll(newParams);
                    allParams.addAll(mustParams);
                    SendMessageDto sendMessageDto = SendMessageDto.of()
                            .setOpenUrl(openApi.getUrl())
                            .setOldUrl(oldApi.getUrl())
                            .setSimilarity(similarity)
                            .setPass(similarity >= 0.95)
                            .setParams(allParams);
                    msgs.add(sendMessageDto);
                }catch (Exception e){
                    SendMessageDto sendMessageDto = SendMessageDto.of()
                            .setOpenUrl(openApi.getUrl())
                            .setOldUrl("请求报错")
                            .setSimilarity(0)
                            .setPass(false)
                           ;
                    msgs.add(sendMessageDto);
                }

            }
        }
        SendEmailDto cells = SendEmailDto.of()
                .setFrom("zhangyl31@ziroom.com")
                .setTos(Lists.newArrayList("zhangyl31@ziroom.com"))
                .setTitle("halo接口测试")
                .setCells(getSendEmailData(msgs));
        sendEmailService.sendEmail(cells);
        return Resp.success();
    }
    private List<List<String>> getSendEmailData(List<SendMessageDto> sendMessages){
        return sendMessages.stream().map(msg -> {
            List<String> list = new ArrayList<>();
            list.add(msg.getOpenUrl());
            list.add(msg.getOldUrl());
            list.add(String.valueOf(msg.getSimilarity()));
            list.add(JSONObject.toJSONString(msg.getParams()));
            list.add(String.valueOf(msg.isPass()));
            return list;
        }).collect(Collectors.toList());
    }

    private List<String> getColumn() {
        return Lists.newArrayList("open url", "old url ", "相似度", "参数", "是否通过");
    }


    private static String runApi(Api api, boolean isOpenApi) {
        String baseUrl = "http://tech-halo.kt.ziroom.com";
        HashMap<String, String> headerMap = Maps.newHashMap();
        headerMap.put("HALO_AUTHORIZATION", "aGFsbzpabUl6T0RSak1tTmhOVEE0TkdFelkyRTRObUkyTVRVeE5HRmlaRGc0TURRd1RYaE1OV2s9");
        return isOpenApi ? HttpUtil.get(baseUrl + api.getUrl(), paramMap(api.getMustParams(), api.getNoMustParams()), headerMap) :
                HttpUtil.get(baseUrl + api.getUrl(), paramMap(api.getMustParams(), api.getNoMustParams()), null);
    }

    private static Map<String, Object> paramMap(List<Params> must, List<Params> noMust) {
        Map<String, Object> collect = must.stream().collect(Collectors.toMap(Params::getName, Params::getValue));
        Map<String, Object> collect1 = noMust.stream().collect(Collectors.toMap(Params::getName, Params::getValue));
        collect.putAll(collect1);
        return collect;
    }

    private static Map<String, Object> generatorParamsValue(){
        Map<String, Object> map = new HashMap<>();
        map.put("appCode", "halo");
        map.put("userCodes", Lists.newArrayList("60042077", "60038140"));
        map.put("deptId", "100921");
        map.put("jobCode", "101768");
        map.put("empCode", "60042077");
        map.put("userCode", "60042077");
        map.put("url", "/admin/PersonalAuthorization");
        map.put("parentId", "12134");
        map.put("expandAll", true);
        map.put("cityCode", "110000");
        map.put("isDept", true);
        map.put("isJob", true);
        map.put("isPost", true);
        // roleCode 的值可能有两种数据类型参照接口  /role/permission   /menus
        map.put("roleCode", "halo-user");
        map.put("orgs", "100921,100372");
        map.put("deptIds", "100921,100372");
        map.put("postCode", "10004195");
        return map;
    }

    /**
     * 递归实现非必填参数的所有组合
     *
     * @param inputList
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> generateCombinations(List<T> inputList) {
        List<List<T>> result = new ArrayList<>();
        generateCombinations(inputList, 0, new ArrayList<>(), result);
        return result;
    }

    private static <T> void generateCombinations(List<T> inputList, int start, List<T> currentCombination, List<List<T>> result) {
        for (int i = start; i < inputList.size(); i++) {
            currentCombination.add(inputList.get(i));
            result.add(new ArrayList<>(currentCombination));
            generateCombinations(inputList, i + 1, currentCombination, result);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    /**
     * 通过正则匹配，找到 /open 开头的接口对应的旧接口
     *
     * @param source 全量的接口
     * @param target /open 开头的接口对应的旧接口
     * @return
     */
    private static Map<String, Pair<Api, Api>> getNewAndOldMap(List<Api> source, List<Api> target) {
        Map<String, Pair<Api, Api>> ret = new HashMap<>();
        target.stream().forEach(openApi -> {
            String[] split = openApi.getUrl().split("/open/", 2);
            split[0] = "open";
            for (Api api : source) {
                String url = api.getUrl();
                if(url.contains("/open/")){
                    continue;
                }
                if(url.startsWith("/")){
                    url = url.substring(1);
                }
                String[] sourceSplit = url.split("/", 2);
                if(split.length == 2 && sourceSplit.length == 2){
                    if(Objects.equals(url, split[1])){
                        Api oldApi = Api.of();
                        BeanUtils.copyProperties(openApi, oldApi);
                        oldApi.setUrl(api.getUrl());
                        ret.put(openApi.getUrl(), Pair.of(openApi, oldApi));
                        continue;
                    }
                    if(Objects.equals(split[1], sourceSplit[1]) && !Objects.equals(split[0], sourceSplit[0])){
                        Api oldApi = Api.of();
                        BeanUtils.copyProperties(openApi, oldApi);
                        oldApi.setUrl(api.getUrl());
                        ;
                        ret.put(openApi.getUrl(), Pair.of(openApi, oldApi));
                        continue;
                    }
                }
            }

        });
        return ret;
    }

    /**
     * 根据halo swagger 的 v2/api-docs 解口，获取所有的API
     * open 开头的是有全量的信息，其他接口只有 URL
     *
     * @param body
     * @return
     */
    private static List<Api> getApis(String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        Map<String, String> paths = JSONObject.parseObject(jsonObject.getString("paths"), new TypeReference<Map<String, String>>() {
        });
        if (CollectionUtils.isEmpty(paths)) {
            return null;
        }
        List<Api> apis = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : paths.entrySet()) {
            String url = stringStringEntry.getKey();
            // 找出全量的 URL，不是 /open 开头的不需要参数，只是为了找出 /open 开头 与其对应的旧接口
            Api api = Api.of().setUrl(stringStringEntry.getKey());
            if (!url.startsWith("/open")) {
                apis.add(api);
                continue;
            }
            Map<String, JSONObject> requestTypeMap = JSONObject.parseObject(stringStringEntry.getValue(), new TypeReference<Map<String, JSONObject>>() {
            });
            if (CollectionUtils.isEmpty(paths)) {
                apis.add(api);
                continue;
            }
            String requestType = requestTypeMap.keySet().stream().findFirst().get();
            api.setRequestType(requestType);
            JSONObject jsonObj = requestTypeMap.get(requestType);
            if (Objects.isNull(jsonObj)) {
                apis.add(api);
                continue;
            }
            String s = jsonObj.getString("parameters");
            if (Objects.isNull(s)) {
                apis.add(api);
                continue;
            }
            List<Params> params = JSONObject.parseArray(s, Params.class);

            api.setMustParams(params.stream().filter(Params::getRequired).collect(Collectors.toList()));
            api.setNoMustParams(params.stream().filter(pm -> !pm.getRequired()).collect(Collectors.toList()));
            apis.add(api);
        }
        return apis;
    }

    @Data(staticConstructor = "of")
    @Accessors(chain = true)
    static class SendMessageDto {
        private String openUrl;
        private String oldUrl;
        private double similarity;
        private List<Params> params;
        private boolean pass;
    }

    @Data(staticConstructor = "of")
    @Accessors(chain = true)
    static class Params {
        String name;
        Boolean required;
        Object value;
    }

    @Data(staticConstructor = "of")
    @Accessors(chain = true)
    static class Api {
        String url;
        String requestType;
        List<Params> mustParams;
        List<Params> noMustParams;
    }

    @Data(staticConstructor = "of")
    @Accessors(chain = true)
    static class CaseDto {
        @ExcelProperty("用例名称")
        String caseName;
        @ExcelProperty("步骤")
        String step;
        @ExcelProperty("预期")
        String result;
    }

}
