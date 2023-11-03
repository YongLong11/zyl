//package com.zyl.utils.httpAop;
//
//import com.zyl.utils.ApplicationContextUtils;
//import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
//import org.springframework.beans.factory.config.BeanDefinitionHolder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
//import org.springframework.core.type.classreading.MetadataReader;
//
//import java.io.IOException;
//import java.util.Set;
//
//public class HttpClientClassScanner extends ClassPathBeanDefinitionScanner {
//
//    public HttpClientClassScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
//        super(beanDefinitionRegistry);
//    }
//
//    @Override
//    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
//        return metadataReader.getClassMetadata().isInterface();
//    }
//
//    @Override
//    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//        return beanDefinition.getMetadata().hasAnnotation("com.zyl.utils.httpAop.annotation.HttpClient");
//    }
//
//    /**
//     * 1.获取指定包路径下的beanDefinition
//     * 2.解析metadata,获取对应的 ClassMetadata AnnotationMetadata
//     * 3.过滤器过滤, AnnotationMetadata 校验过滤
//     * 4.注册bean
//     * @param basePackages
//     * @return
//     */
//
//    @Override
//    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
//        // 扫描 包下含有 HttpClient注解 的接口  并注册bean
//        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
//        for (BeanDefinitionHolder holder : beanDefinitions) {
//            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
//            definition.getConstructorArgumentValues().addIndexedArgumentValue(0, definition.getBeanClassName());
//            definition.setBeanClass(ApplicationContextUtils.class);
//        }
//        return beanDefinitions;
//    }
//}
