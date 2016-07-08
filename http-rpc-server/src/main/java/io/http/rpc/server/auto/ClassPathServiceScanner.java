package io.http.rpc.server.auto;

import io.http.rpc.annotation.ServiceMapping;
import io.http.rpc.annotation.ServiceNamespace;
import io.http.rpc.server.ServiceRegistrationCenter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by manbu on 7/6/16.
 */
public class ClassPathServiceScanner extends ClassPathBeanDefinitionScanner {

    private Class<? extends Annotation> annotationClass;

    private ApplicationContext applicationContext;

    private ServiceRegistrationCenter serviceRegistrationCenter;

    public ClassPathServiceScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {

        if(annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(annotationClass));
        } else {
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {

        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {

            logger.warn("No http rpc service was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");

        } else {

            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

        try {

            for (BeanDefinitionHolder holder : beanDefinitions) {

                GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

                Class<?> clazz = definition.resolveBeanClass(applicationContext.getClassLoader());

                ServiceNamespace namespaceAnnotation = clazz.getAnnotation(ServiceNamespace.class);

                String namespace = namespaceAnnotation.value();

                if(StringUtils.isEmpty(namespace)) {
                    namespace = clazz.getName();
                }

                Method[] methods = clazz.getDeclaredMethods();

                for (Method method: methods) {

                    ServiceMapping mappingAnnotation = method.getAnnotation(ServiceMapping.class);

                    String name = "";

                    if(mappingAnnotation != null) {
                        name = mappingAnnotation.value();
                    }

                    if(StringUtils.isEmpty(name)) {
                        name = method.getName();
                    }

                    serviceRegistrationCenter.registerService(namespace, name, clazz, method);
                }
            }

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("scan class not found", ex);
        }
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) { this.annotationClass = annotationClass; }

    public void setServiceRegistrationCenter(ServiceRegistrationCenter serviceRegistrationCenter) {
        this.serviceRegistrationCenter = serviceRegistrationCenter;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
