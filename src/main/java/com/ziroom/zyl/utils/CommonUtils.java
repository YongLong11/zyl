package com.ziroom.zyl.utils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @ClassName：CommonUtils
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 18:54
 **/
public class CommonUtils {

    // stream中根据指定字段去重
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> {
            Object item = keyExtractor.apply(t);
            if (Objects.isNull(item)) {
                return false;
            }
            return seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
        };
    }
}
