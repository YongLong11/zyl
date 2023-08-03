package com.ziroom.zyl.common.constants;

/**
 * @ClassName：RedisKeyConstants
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 20:40
 **/
public interface RedisConstants {

    String ALL_USER_INFO_KEY = "all_user_info_key";
    String ALL_USER_INFO_LOCK = "all_user_info_lock";

    String ZSET_ADD_LUA = "if redis.call('exists', KEYS[1]) == 1 then\n" +
            "\t\tredis.call('ZADD', KEYS[1], ARGV[1], ARGV[2])\n" +
            "\t\treturn 1\n" +
            "\telse\n" +
            "\t\tredis.call('ZADD', KEYS[1], ARGV[1], ARGV[2])\n" +
            "\t\tredis.call('expire', KEYS[1], ARGV[3])\n" +
            "\t\t return 0\n" +
            "end";

    String SET_ADD_LUA = "if redis.call('exists', KEYS[1]) == 1 then\n" +
            "\t\tredis.call('SADD', KEYS[1], ARGV[1])\n" +
            "\t\treturn 1\n" +
            "\telse\n" +
            "\t\tredis.call('SADD', KEYS[1], ARGV[1])\n" +
            "\t\tredis.call('expire', KEYS[1], ARGV[2])\n" +
            "\t\t return 0\n" +
            "end";

    String INCR_LUA = "if redis.call('exists', KEYS[1]) == 1 then\n" +
            "\t\tredis.call('INCR', KEYS[1])\n" +
            "\telse\n" +
            "\t\tredis.call('INCR', KEYS[1])\n" +
            "\t\tredis.call('expire', KEYS[1], ARGV[1])\n" +
            "end\n" +
            "return redis.call('GET', KEYS[1])";
}
