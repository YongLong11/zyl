package com.ziroom.zyl.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CacheEnum {

    REDIS_TEST(60 * 60 *2);
    private int ttl;

}
