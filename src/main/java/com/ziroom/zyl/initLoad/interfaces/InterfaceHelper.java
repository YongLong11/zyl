package com.ziroom.zyl.initLoad.interfaces;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public class InterfaceHelper {

    private static final HashMap<String, BaseInterfaces> map = Maps.newHashMap();
    public static void register(BaseInterfaces baseInterfaces){
        String s = baseInterfaces.registerSelf();
        map.put(s, baseInterfaces);
    }

    public static Map<String, BaseInterfaces> get(){
        return map;
    }
}
