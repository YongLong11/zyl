package com.zyl.service;

import com.zyl.something.http.HttpUtil;
import com.zyl.something.http.LogHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
@SpringBootTest
public class CurlTest {

    @Resource
    LogHttpClient logHttpClient;

    // 空格 是 +
    // \ 是 %5C
    // 换行是 %0A
    // : 是  %3A
    // / 是 %2F

//    curl 'https://tool.lu/curl/ajax.html' \
//            -H 'authority: tool.lu' \
//            -H 'accept: application/json, text/javascript, */*; q=0.01' \
//            -H 'accept-language: zh-CN,zh;q=0.9' \
//            -H 'content-type: application/x-www-form-urlencoded; charset=UTF-8' \
//            -H 'cookie: uuid=e3076d97-bd98-455d-b1c4-e6e669d71672; _session=%7B%22slim.flash%22%3A%5B%5D%7D; Hm_lvt_0fba23df1ee7ec49af558fb29456f532=1690335717,1690869029,1691555907,1692329310; Hm_lpvt_0fba23df1ee7ec49af558fb29456f532=1692329319; _access=3be3a8fa9d42815a85c83d9f40fefd36c5c771f368cd2b0a50c3a5ccd6484f3c' \
//            -H 'origin: https://tool.lu' \
//            -H 'referer: https://tool.lu/curl/' \
//            -H 'sec-ch-ua: "Not/A)Brand";v="99", "Google Chrome";v="115", "Chromium";v="115"' \
//            -H 'sec-ch-ua-mobile: ?0' \
//            -H 'sec-ch-ua-platform: "macOS"' \
//            -H 'sec-fetch-dest: empty' \
//            -H 'sec-fetch-mode: cors' \
//            -H 'sec-fetch-site: same-origin' \
//            -H 'user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36' \
//            -H 'x-requested-with: XMLHttpRequest' \
//            --data-raw 'command=curl+https%3A%2F%2Fhref.lu%2F&target=json' \
//            --compressed
    @Test
    public void test(){
        Map<String, Object> param = new HashMap<>();
        param.put("command", "curl+https%3A%2F%2Fhref.lu%2F");
        param.put("target", "json");
        Map<String, String> header = new HashMap<>();

        header.put("authority", "tool.lu");
        header.put("accept", "application/json, text/javascript, */*; q=0.01");
        header.put("accept-language", "zh-CN,zh;q=0.9");
        header.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        header.put("cookie", "uuid=e3076d97-bd98-455d-b1c4-e6e669d71672; _session=%7B%22slim.flash%22%3A%5B%5D%7D; Hm_lvt_0fba23df1ee7ec49af558fb29456f532=1690335717,1690869029,1691555907,1692329310; Hm_lpvt_0fba23df1ee7ec49af558fb29456f532=1692329319; _access=3be3a8fa9d42815a85c83d9f40fefd36c5c771f368cd2b0a50c3a5ccd6484f3c");
        header.put("origin", "https://tool.lu");
        header.put("referer", "https://tool.lu/curl/");
        header.put("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
        header.put("sec-ch-ua-mobile", "?0");
        header.put("sec-ch-ua-platform", "\"macOS\"");
        header.put("sec-fetch-dest", "empty");
        header.put("sec-fetch-mode", "cors");
        header.put("sec-fetch-site", "same-origin");
        header.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        header.put("x-requested-with", "XMLHttpRequest");

        String post = HttpUtil.post("https://tool.lu/curl/ajax.html", param, header);
        System.out.println(post);
    }


    public static void main(String[] args) throws IOException {
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
        writer.write("command=curl+'https%3A%2F%2Ftool.lu%2Fcurl%2Fajax.html'+%5C%0A++-H+'authority%3A+tool.lu'+%5C%0A++-H+'accept%3A+application%2Fjson%2C+text%2Fjavascript%2C+*%2F*%3B+q%3D0.01'+%5C%0A++-H+'accept-language%3A+zh-CN%2Czh%3Bq%3D0.9'+%5C%0A++-H+'content-type%3A+application%2Fx-www-form-urlencoded%3B+charset%3DUTF-8'+%5C%0A++-H+'cookie%3A+uuid%3De3076d97-bd98-455d-b1c4-e6e669d71672%3B+_session%3D%257B%2522slim.flash%2522%253A%255B%255D%257D%3B+Hm_lvt_0fba23df1ee7ec49af558fb29456f532%3D1690335717%2C1690869029%2C1691555907%2C1692329310%3B+Hm_lpvt_0fba23df1ee7ec49af558fb29456f532%3D1692329319%3B+_access%3D3be3a8fa9d42815a85c83d9f40fefd36c5c771f368cd2b0a50c3a5ccd6484f3c'+%5C%0A++-H+'origin%3A+https%3A%2F%2Ftool.lu'+%5C%0A++-H+'referer%3A+https%3A%2F%2Ftool.lu%2Fcurl%2F'+%5C%0A++-H+'sec-ch-ua%3A+%22Not%2FA)Brand%22%3Bv%3D%2299%22%2C+%22Google+Chrome%22%3Bv%3D%22115%22%2C+%22Chromium%22%3Bv%3D%22115%22'+%5C%0A++-H+'sec-ch-ua-mobile%3A+%3F0'+%5C%0A++-H+'sec-ch-ua-platform%3A+%22macOS%22'+%5C%0A++-H+'sec-fetch-dest%3A+empty'+%5C%0A++-H+'sec-fetch-mode%3A+cors'+%5C%0A++-H+'sec-fetch-site%3A+same-origin'+%5C%0A++-H+'user-agent%3A+Mozilla%2F5.0+(Macintosh%3B+Intel+Mac+OS+X+10_15_7)+AppleWebKit%2F537.36+(KHTML%2C+like+Gecko)+Chrome%2F115.0.0.0+Safari%2F537.36'+%5C%0A++-H+'x-requested-with%3A+XMLHttpRequest'+%5C%0A++--data-raw+'command%3Dcurl%2Bhttps%253A%252F%252Fhref.lu%252F%26target%3Djson'+%5C%0A++--compressed&target=json");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);
    }
}
