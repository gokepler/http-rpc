package io.http.rpc.server.auto;

import io.http.rpc.core.annotation.ServiceNamespace;
import io.http.rpc.server.ServiceExporter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * Created by manbu on 7/6/16.
 */
public class ServiceScannerConfigurer implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware{

    private String basePackage;

    private Class<? extends Annotation> annotationClass = ServiceNamespace.class;

    private ApplicationContext applicationContext;

    private BeanNameGenerator nameGenerator;

    private ServiceExporter serviceExporter;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        ClassPathServiceScanner scanner = new ClassPathServiceScanner(registry);

        scanner.setResourceLoader(applicationContext);
        scanner.setBeanNameGenerator(nameGenerator);
        scanner.setAnnotationClass(annotationClass);
        scanner.setApplicationContext(applicationContext);
        scanner.setServiceRegistrationCenter(serviceExporter.getRegistrationCenter());

        scanner.registerFilters();

        scanner.scan(StringUtils.tokenizeToStringArray(basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // left intentionally blank
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setServiceExporter(ServiceExporter serviceExporter) {
        this.serviceExporter = serviceExporter;
    }

}
