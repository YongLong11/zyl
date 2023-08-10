package com.ziroom.zyl.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ziroom.zyl.client.apiAdaptor.enums.UrlConfig;
import com.ziroom.zyl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public class ResponseParser {

    public static <T> T parse(String response, Class<T> clazz, UrlConfig urlConfig) {
        if (StringUtils.isBlank(response)) {
            throw new BusinessException("");
        }

        JSONObject resultData = JSONObject.parseObject(response);
        log.info(String.format("接口%s返回结果：%s", urlConfig.getUrl(), JSON.toJSONString(resultData)));

        String successFiled = urlConfig.getSuccessField();
        Object successValue = urlConfig.getSuccessCode();
        Object filedData = resultData.get(successFiled);
        if (filedData == null || !StringUtils.equals(
                String.valueOf(filedData), String.valueOf(successValue))) {
            throw new BusinessException("接口请求报错");
        }

        String data = resultData.getString("data");
        return JSONObject.parseObject(data, clazz);
    }

    public static <T> List<T> parseList(String response, Class<T> clazz, UrlConfig urlConfig) {
        String arrayResult = parse(response, String.class, urlConfig);
        return JSON.parseArray(arrayResult, clazz);
    }

}
