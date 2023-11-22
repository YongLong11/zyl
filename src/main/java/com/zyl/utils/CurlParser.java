package com.zyl.utils;

import com.alibaba.fastjson.JSONObject;
import com.zyl.pojo.CurlParams;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CurlParser {
    static String PARAM = "--location";
    static String DATA_RAW = "--data-raw";
    static String HEADER = "--header";
    static String DATA_BINARY = "--data-binary";
    static String DATA_URLENCODE = "--data-urlencode";

    public CurlParser() {
    }

    /**
     * 解析curl
     *
     * @param curl
     * @return
     */
    public static CurlParams getParams(String curl) {
        // 通过 charles 抓包
        CurlParams cp = new CurlParams();
        if (curl.contains(PARAM)) {
            cp = getCurlParams(curl, cp);
            return cp;
        } else {
            String[] a = curl.split("\\n");
            cp.setRequestType("GET");
            HashMap<String, String> h = new HashMap<>();
            for (int j = 0; j < a.length; j++) {
                String key = a[j];
                if (key.contains("curl")) {
                    String url = key.replace("curl", " ").replace('\'', ' ').replace('\\', ' ').trim();
                    cp.setUrl(url);
                } else if (key.toUpperCase(Locale.ROOT).contains("-H")) {
                    String s = a[j].replace("-H", " ").replace('\'', ' ').replace('\\', ' ').trim();
                    h.put(s.split(": ")[0].trim(), s.split(": ")[1].trim());
                } else if (key.contains(DATA_RAW)) {
                    String data = key.replace(DATA_RAW, " ").replace('\'', ' ').replace('\\', ' ').trim();
                    cp.setData(data);
                } else if (key.contains(DATA_BINARY)) {
                    String data = key.replace(DATA_BINARY, " ").replace('\'', ' ').replace('\\', ' ').trim();
                    cp.setData(data);
                } else if (key.toUpperCase(Locale.ROOT).contains("-X")) {
                    String data = key.replace("-X", " ").replace('\'', ' ').replace('\\', ' ').trim();
                    cp.setRequestType(data);
                }
            }
            cp.setHeader(h);
            if (cp.getUrl().contains("?")) {
                int i = cp.getUrl().indexOf("?");
                String param = cp.getUrl().substring(i + 1);
                cp.setParam(param);
            }
            if (Objects.nonNull(cp.getData())) {
                cp.setRequestType("POST");
            }
            return cp;
        }
    }

    /**
     * 移动端 charles 抓包兼容
     *
     * @param curl
     * @param cp
     * @return
     */
    private static CurlParams getCurlParams(String curl, CurlParams cp) {
        Map<String, String> headerMap = new HashMap<>();
        // urlencode 数据
        JSONObject urlencodeJson = new JSONObject();
        String[] split = curl.replace("\'", " ").split("\n");
        for (String str : split) {
            if (str.contains("curl")) {
                // 处理curl
                String[] cuiLIst = str.split("\\s");
                for (String curStr : cuiLIst) {
                    if (curStr.contains("POST") || curStr.contains("GET") || curStr.contains("PUT") || curStr.contains("DELETE")) {
                        cp.setRequestType(curStr);
                    } else if (curStr.contains("http")) {
                        cp.setUrl(curStr);
                    }
                }
            } else if (str.contains(HEADER)) {
                //  处理头信息
                String[] header = str.split(HEADER);
                String headerKeyValue = header[1].replace(" \\", "").trim();
                String[] keyValue = headerKeyValue.split(":");
                headerMap.put(keyValue[0].trim(), keyValue[1].trim());
            } else if (str.contains(DATA_RAW)) {
                // 处理 body 信息
                String[] content = str.split(DATA_RAW);
                String strJSon = content[1].trim();
                if (strJSon.startsWith("{")) {
                    cp.setData(strJSon);
                }
            } else if (str.contains(DATA_URLENCODE)) {
                String[] content = str.split(DATA_URLENCODE);
                String paramKeyValue = content[1].replace(" \\", "").trim();
                String[] contentKey = paramKeyValue.split("=");
                urlencodeJson.put(contentKey[0], contentKey[1]);
            }
        }
        if (!urlencodeJson.isEmpty()) {
            cp.setData(JSONObject.toJSONString(urlencodeJson));
            cp.setRequestType("POST");
        }
        if (cp.getUrl().contains("null")) {
            int i = cp.getUrl().indexOf("?");
            String substring = cp.getUrl().substring(0, i);
            cp.setUrl(substring);
        }
        cp.setHeader(headerMap);
        return cp;
    }


    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static String getCurl(String path) {
        String content = "";
        StringBuilder builder = new StringBuilder();

        File file = new File(path);
        try {
            InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            while ((content = bufferedReader.readLine()) != null) {
                builder.append(content + "\n");
            }
        } catch (IOException e) {
           log.error("文件解析失败");
        }
        return builder.toString();
    }
}

