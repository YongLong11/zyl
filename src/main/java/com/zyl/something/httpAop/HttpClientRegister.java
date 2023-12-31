package com.zyl.something.httpAop;

import com.zyl.something.httpAop.annotation.HttpClient;
import com.zyl.something.httpAop.annotation.EnableHttpClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Component
@Slf4j
public class HttpClientRegister implements ImportBeanDefinitionRegistrar,
        EnvironmentAware, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private Environment environment;

    // 不需要此处配置扫包路径，使用 EnableHttpClient 注解，放在启动类上配置即可
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry){
        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableHttpClient.class.getName());
        if(CollectionUtils.isEmpty(attributes)){
            throw new RuntimeException("EnableHttpClient basePackage must not null");
        }
        String basePackage = (String) attributes.get("basePackage");
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
//        ClassPathBeanDefinitionScanner beanDefinitionScanner = new ClassPathBeanDefinitionScanner(registry, false);
//        scanner.setResourceLoader(this.resourceLoader);
//        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpClient.class));
//        Set<BeanDefinition> candidateComponents = beanDefinitionScanner.findCandidateComponents(basePackage);
        Set<BeanDefinition> beanDefinitionHolders = scanner.findCandidateComponents(basePackage);
        for (BeanDefinition holder : beanDefinitionHolders) {
            registerHttpClientBean(holder, registry);
        }
    }

//    @Override
//    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) {
//        ClassPathScanningCandidateComponentProvider scanner = getScanner();
//        scanner.setResourceLoader(this.resourceLoader);
//        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpClient.class));
//        Set<BeanDefinition> beanDefinitionHolders = scanner.findCandidateComponents(System.getProperty("httpClient.basePackage"));
//        for (BeanDefinition holder : beanDefinitionHolders) {
//            registerHttpClientBean(holder, registry);
//        }
//    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void registerHttpClientBean(BeanDefinition definition, BeanDefinitionRegistry registry) {
        AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition) definition;
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> targetClass = Class.forName(beanClassName);
            if (targetClass.isInterface()) {
                Object proxy = Proxy.newProxyInstance(
                        targetClass.getClassLoader(),
                        new Class[]{targetClass},
                        new HttpClientInvocationHandler(targetClass, environment));
                BeanDefinitionBuilder proxyBeanBuilder = BeanDefinitionBuilder.genericBeanDefinition(targetClass, (Supplier) () -> proxy);
                AbstractBeanDefinition proxyBeanDefinition = proxyBeanBuilder.getRawBeanDefinition();
                String beanName = BeanDefinitionReaderUtils.generateBeanName(definition, registry);
                registry.registerBeanDefinition(beanName, proxyBeanDefinition);
            }
        } catch (ClassNotFoundException e) {
            log.error("" + e);
        }
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(@NotNull AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(@NotNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}