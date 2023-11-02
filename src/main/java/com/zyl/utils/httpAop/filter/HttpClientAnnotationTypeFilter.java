package com.zyl.utils.httpAop.filter;

import com.zyl.utils.httpAop.annotation.HttpClient;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

@Component
public class HttpClientAnnotationTypeFilter implements TypeFilter {

    @Override
    public boolean match(org.springframework.core.type.classreading.MetadataReader metadataReader, org.springframework.core.type.classreading.MetadataReaderFactory metadataReaderFactory) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(HttpClient.class));
        return scanner.findCandidateComponents(metadataReader.getClassMetadata().getClassName()).size() > 0;
    }
}
