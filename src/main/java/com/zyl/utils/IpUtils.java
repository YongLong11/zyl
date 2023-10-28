package com.zyl.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

    private static final String UNKNOWN = "unknown";

    public static String findIP(HttpServletRequest request) {
        String IP = request.getHeader("x-forwarded-for");
        if (StringUtils.isNotEmpty(IP) && !UNKNOWN.equals(IP)) {
            IP = IP.split("m")[0];
        }
        if (StringUtils.isEmpty(IP) || UNKNOWN.equals(IP)) {
            IP = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(IP) || UNKNOWN.equals(IP)) {
            IP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(IP) || "unknown".equalsIgnoreCase(IP)) {
            IP = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(IP) || "unknown".equalsIgnoreCase(IP)) {
            IP = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(IP) || "unknown".equalsIgnoreCase(IP)) {
            IP = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isEmpty(IP) || "unknown".equalsIgnoreCase(IP)) {
            IP = request.getRemoteAddr();
        }

        return IP;
    }
}
