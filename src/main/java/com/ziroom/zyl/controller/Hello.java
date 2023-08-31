package com.ziroom.zyl.controller;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.ziroom.zyl.aop.Retryable;
import com.ziroom.zyl.cache.cache.PrefixKeyCache;
import com.ziroom.zyl.common.Resp;
import com.ziroom.zyl.common.constants.RedisConstants;
import com.ziroom.zyl.common.enums.CacheEnum;
import com.ziroom.zyl.common.exception.BusinessException;
import com.ziroom.zyl.service.EasyExcelService;
import com.ziroom.zyl.utils.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName：Hello
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/9 15:09
 **/
@RequestMapping("/hello")
@RestController
public class Hello {

    @Resource
    private EasyExcelService easyExcelService;

    @Resource
    RedisUtils redisUtils;

    @GetMapping("/redis/set")
    public Resp redisSet(){
        redisUtils.set(RedisConstants.REDIS_TEST, 100);
        return  Resp.success();
    }

    @GetMapping("/redis/get")
    public Resp redisGet(){
        return  Resp.success(redisUtils.get(RedisConstants.REDIS_TEST));
    }

    @GetMapping("/redis/set/cache")
    public Resp redisSetFromCache(){
        Boolean aBoolean = redisUtils.setFromCacheManager(CacheEnum.REDIS_TEST, RedisConstants.REDIS_TEST_V, "hahhha");
        return  Resp.success(aBoolean);
    }

    @GetMapping("/redis/get/cache")
    public Resp redisGetFromCache(){
        Object redisCacheManager = redisUtils.getFromCacheManager(CacheEnum.REDIS_TEST, RedisConstants.REDIS_TEST_V);
        return  Resp.success(redisCacheManager);
    }
    @GetMapping("/redis/get/cache/class")
    public Resp redisGetFromCacheByClass(){
        String redisCacheManager = redisUtils.getFromCacheManager(CacheEnum.REDIS_TEST, RedisConstants.REDIS_TEST_V, String.class);
        return  Resp.success(redisCacheManager);
    }

    // 通过请求工具，拿到json结果。需求确认的是 cookie 是否有 有效期
    @PostMapping(value = "/hello-world", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @MethodLog()
//    @Limiter(time = 10, count = 2)
//    @Retryable
    public Resp<String> hello(@RequestParam String curl) throws Exception{
        // 空格 是 +
        // \ 是 %5C
        // 换行是 %0A
        // : 是  %3A
        // / 是 %2F
        for (int i = 0; i < 100; i++) {
            String replace = curl.replace(" ", "+").replace("\\", "%5C")
                    .replace("\\n", "%0A").replace(":", "%3A")
                    .replace("/", "%2F");
            URL url = new URL("https://tool.lu/curl/ajax.html");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("authority", "tool.lu");
            httpConn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            httpConn.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpConn.setRequestProperty("cookie", "uuid=e3076d97-bd98-455d-b1c4-e6e669d71672; _session=%7B%22slim.flash%22%3A%5B%5D%7D; Hm_lvt_0fba23df1ee7ec49af558fb29456f532=1690335717,1690869029,1691555907,1692329310; Hm_lpvt_0fba23df1ee7ec49af558fb29456f532=1692329319; _access=3be3a8fa9d42815a85c83d9f40fefd36c5c771f368cd2b0a50c3a5ccd6484f3c");
            httpConn.setRequestProperty("origin", "https://tool.lu");
            httpConn.setRequestProperty("referer", "https://tool.lu/curl/");
            httpConn.setRequestProperty("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
            httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
            httpConn.setRequestProperty("sec-ch-ua-platform", "\"macOS\"");
            httpConn.setRequestProperty("sec-fetch-dest", "empty");
            httpConn.setRequestProperty("sec-fetch-mode", "cors");
            httpConn.setRequestProperty("sec-fetch-site", "same-origin");
            httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
            httpConn.setRequestProperty("x-requested-with", "XMLHttpRequest");

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("command=" + replace + "&target=json");
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println("===============================================");
            System.out.println(response);
            Thread.sleep(1000);
        }

        return null;
    }

    public static void main(String[] args) {
        String a = "{\"status\":true,\"message\":null,\"code\":\"{\\n    \\\"url\\\": \\\"https:\\/\\/tool.lu\\/curl\\/ajax.html\\\",\\n    \\\"raw_url\\\": \\\"https:\\/\\/tool.lu\\/curl\\/ajax.html\\\",\\n    \\\"method\\\": \\\"post\\\",\\n    \\\"cookies\\\": {\\n        \\\"uuid\\\": \\\"e3076d97-bd98-455d-b1c4-e6e669d71672\\\",\\n        \\\"Hm_lvt_0fba23df1ee7ec49af558fb29456f532\\\": \\\"1690335717,1690869029,1691555907,1692329310\\\",\\n        \\\"Hm_lpvt_0fba23df1ee7ec49af558fb29456f532\\\": \\\"1692330451\\\",\\n        \\\"_session\\\": \\\"{\\\\\\\"slim.flash\\\\\\\":[]}\\\"\\n    },\\n    \\\"headers\\\": {\\n        \\\"authority\\\": \\\"tool.lu\\\",\\n        \\\"accept\\\": \\\"application\\/json, text\\/javascript, *\\/*; q=0.01\\\",\\n        \\\"accept-language\\\": \\\"zh-CN,zh;q=0.9\\\",\\n        \\\"content-type\\\": \\\"application\\/x-www-form-urlencoded; charset=UTF-8\\\",\\n        \\\"cookie\\\": \\\"uuid=e3076d97-bd98-455d-b1c4-e6e669d71672; Hm_lvt_0fba23df1ee7ec49af558fb29456f532=1690335717,1690869029,1691555907,1692329310; Hm_lpvt_0fba23df1ee7ec49af558fb29456f532=1692330451; _session={\\\\\\\"slim.flash\\\\\\\":[]}\\\",\\n        \\\"origin\\\": \\\"https:\\/\\/tool.lu\\\",\\n        \\\"referer\\\": \\\"https:\\/\\/tool.lu\\/curl\\/\\\",\\n        \\\"sec-ch-ua\\\": \\\"\\\\\\\"Not\\/A)Brand\\\\\\\";v=\\\\\\\"99\\\\\\\", \\\\\\\"Google Chrome\\\\\\\";v=\\\\\\\"115\\\\\\\", \\\\\\\"Chromium\\\\\\\";v=\\\\\\\"115\\\\\\\"\\\",\\n        \\\"sec-ch-ua-mobile\\\": \\\"?0\\\",\\n        \\\"sec-ch-ua-platform\\\": \\\"\\\\\\\"macOS\\\\\\\"\\\",\\n        \\\"sec-fetch-dest\\\": \\\"empty\\\",\\n        \\\"sec-fetch-mode\\\": \\\"cors\\\",\\n        \\\"sec-fetch-site\\\": \\\"same-origin\\\",\\n        \\\"user-agent\\\": \\\"Mozilla\\/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/115.0.0.0 Safari\\/537.36\\\",\\n        \\\"x-requested-with\\\": \\\"XMLHttpRequest\\\"\\n    },\\n    \\\"data\\\": {\\n        \\\"command\\\": \\\"curl 'https:\\/\\/tool.lu\\/curl\\/ajax.html'\\\\\\\\H'authority: tool.lu\\\\\\\\\\\"\\n    }\\n}\\n\"}";
        System.out.println(a.replace("\\n", "").replace("\\", ""));

    }

//    @GetMapping("/world")
//    @MethodLog
    @RequestMapping(value = "world", method = RequestMethod.GET)
//    @Limiter(time = 10, count = 2)
    public Resp<String> hello1(HttpServletRequest request){
        return Resp.success("hello");
    }

    @GetMapping("/export")
//    @MethodLog
    public void hello1(HttpServletResponse response){
        easyExcelService.xssDown(response);
    }

    @GetMapping("/down")
//    @MethodLog
    public void down(HttpServletResponse response){
        try {
            easyExcelService.writeExcel(response, "test", 0, initData());
        }catch (IOException e) {
            System.out.println("错误");
        }
    }


    private List<Student> initData() {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student data = new Student();
            data.setName("学号" + i);
            data.setBirthday(new Date());
            data.setGender("男");
            students.add(data);
        }
        return students;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Student {

        @ExcelIgnore
        private String id;

        @ExcelProperty("学生姓名")
        private String name;

        @ExcelProperty("学生性别")
        private String gender;

        @ExcelProperty("学生出生日期")
        private Date birthday;
    }

}

