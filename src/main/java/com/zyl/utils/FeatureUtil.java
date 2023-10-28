package com.zyl.utils;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.*;

@UtilityClass
@Slf4j
public class FeatureUtil {

    public <T> T getFeatureData(Future<T> future, long time, T defaultResult) {
        if (Objects.isNull(future)) {
            return defaultResult;
        }
        T ret = defaultResult;
        try {
            ret = future.get(time, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("异步获取结果失败 {}", JSON.toJSONString(future));
        }
        return ret;
    }

    public <T> T getFeatureData1S(Future<T> future, T defaultResult) {
        return getFeatureData(future, 1000, defaultResult);
    }

    public <T> T getFeatureData1S(Future<T> future) {
        return getFeatureData(future, 1000, null);
    }

    public <T> T getFeatureData2S(Future<T> future, T defaultResult) {
        return getFeatureData(future, 2000, defaultResult);
    }

    public <T> T getFeatureData2S(Future<T> future) {
        return getFeatureData(future, 2000, null);
    }
}
