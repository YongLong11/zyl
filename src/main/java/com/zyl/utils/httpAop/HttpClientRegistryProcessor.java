package com.zyl.utils.httpAop;

import com.zyl.utils.httpAop.annotation.HttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.function.Supplier;

@Component
public class HttpClientRegistryProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private Environment environment;

//    private final RestTemplate restTemplate;
//
//    public HttpClientRegistryProcessor() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setReadTimeout(5000);//ms
//        factory.setConnectTimeout(15000);//ms
//        restTemplate = new RestTemplate(factory);
//    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpClient.class));
        Set<BeanDefinition> beanDefinitionHolders = scanner.findCandidateComponents("com.zyl");
        for (BeanDefinition holder : beanDefinitionHolders) {
            registerHttpClientBean(holder, registry);
        }
    }

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
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Do nothing
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