package com.zyl.utils.dubbo;


import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ApiDocsDubboGenericUtil {

    /**
     * Registry information cache.
     */
    private static Map<String, RegistryConfig> registryConfigCache;

    /**
     * Dubbo service interface proxy cache.
     */
    private static Map<String, ReferenceConfig<GenericService>> referenceCache;

    private static final ScheduledExecutorService EXECUTOR;

    /**
     * Default retries.
     */
    private static int retries = 2;

    /**
     * Default timeout.
     */
    private static int timeout = 1500;

    static {
        EXECUTOR = new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 40 * (1 + 5 / 2),
                new BasicThreadFactory.Builder().namingPattern("dubbo-async-executor-pool-%d").daemon(true).build());

        registryConfigCache = new ConcurrentHashMap<>();
        referenceCache = new ConcurrentHashMap<>();
    }


    /**
     * 获取注册中心配置
     *
     * @param address 注册中心地址
     */
    private static RegistryConfig getRegistryConfig(String address) {
        RegistryConfig registryConfig = registryConfigCache.get(address);
        if (null == registryConfig) {
            registryConfig = new RegistryConfig();
            registryConfig.setAddress(address);
            registryConfig.setRegister(false);
            registryConfigCache.put(address, registryConfig);
        }
        return registryConfig;
    }

    /**
     * 获得泛化服务
     *
     * @param interfaceName 接口名称
     * @return 泛化服务
     */
    private static ReferenceConfig<GenericService> getReferenceConfig(String interfaceName, String version, String group) {
        //接入服务配置的，之前是查数据库来的
        String address = "";
        String app = "";
        final String key = buildCacheKey(address, app, interfaceName, version, group);
        ReferenceConfig<GenericService> referenceConfig = referenceCache.get(key);
        if (null == referenceConfig) {
            referenceConfig = new ReferenceConfig<>();
            referenceConfig.setRetries(retries);
            referenceConfig.setTimeout(timeout);

            referenceConfig.setVersion(version);
            referenceConfig.setGroup(group);

            ApplicationConfig application = new ApplicationConfig();
            application.setName(app);

            referenceConfig.setApplication(application);
            if (address.startsWith("dubbo")) {
                referenceConfig.setUrl(address);
            } else {
                referenceConfig.setRegistry(getRegistryConfig(address));
            }
            referenceConfig.setInterface(interfaceName);

            referenceConfig.setGeneric(true);
            referenceCache.put(key, referenceConfig);
        }
        return referenceConfig;
    }


    /**
     * 调用Dubbo服务
     *
     * @param interfaceName 接口名称
     * @param method        dubbo方法
     * @param async         异步调用
     * @param version       版本
     * @param paramTypes    参数类型
     * @param paramValues   参数内容
     * @param group         分组
     * @param attachments   隐式参数
     * @return 返回结果
     */
    public static CompletableFuture<Object> invoke(String interfaceName, String method,
                                                   String version, boolean async, String[] paramTypes,
                                                   Object[] paramValues, String group, Map<String, String> attachments) {
        CompletableFuture future = null;
        ReferenceConfig<GenericService> reference = getReferenceConfig(interfaceName, version, group);
        if (null != reference) {
            GenericService genericService = reference.get();
            if (null != genericService) {
                //dubbo上下文
                attachments = Objects.nonNull(attachments) ? attachments : new HashMap<>();
                RpcContext.getContext().setAttachments(attachments);
                if (async) {
                    future = genericService.$invokeAsync(method, paramTypes, paramValues);
                } else {
                    future = CompletableFuture.supplyAsync(() -> genericService.$invoke(method, paramTypes, paramValues), EXECUTOR);
                }
            }
        }

        return future;
    }


    /**
     * 调用Dubbo服务
     *
     * @param interfaceName 接口名称
     * @param method        dubbo方法
     * @param version       版本
     * @param paramTypes    参数类型
     * @param paramValues   参数内容
     * @param group         分组
     * @param attachments   隐式参数
     * @return 返回结果
     */
    public static Object invokeSync(String interfaceName, String method, String version,
                                    String[] paramTypes,
                                    Object[] paramValues, String group, Map<String, String> attachments) {

        ReferenceConfig<GenericService> reference = getReferenceConfig(interfaceName, version, group);
        if (null != reference) {
            GenericService genericService = reference.get();
            if (null != genericService) {
                return genericService.$invoke(method, paramTypes, paramValues);
            }
        }

        return null;
    }


    /**
     * @param address       地址
     * @param interfaceName 接口名称
     * @param version       版本
     * @param group         分组
     * @return 返回结果
     */
    private static String buildCacheKey(String address, String app, String interfaceName, String version, String group) {
        return address + "/" + app + "/" + interfaceName + "/" + version + "/" + group;
    }
}
