package io.http.rpc.client.auto;

import io.http.rpc.client.CoreServiceInvoker;
import io.http.rpc.core.serialize.SerializeScheme;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by manbu on 7/6/16.
 */
public class ClassPathServiceScanner extends ClassPathBeanDefinitionScanner {

    private Class<? extends Annotation> annotationClass;

    private CoreServiceInvoker serviceInvoker;

    private SerializeScheme serializeScheme;

    private String url;

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

        ServiceFactoryBean factoryBean = new ServiceFactoryBean();

        for (BeanDefinitionHolder holder : beanDefinitions) {

            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

            logger.info("Creating ServiceFactoryBean with name '" + holder.getBeanName()
                    + "' and '" + definition.getBeanClassName() + "' serviceInterface");

            // the service interface is the original class of the bean
            // but, the actual class of the bean is ServiceFactoryBean
            definition.getPropertyValues().add("serviceInterface", definition.getBeanClassName());
            definition.getPropertyValues().add("serviceInvoker", serviceInvoker);
            definition.getPropertyValues().add("serializeScheme", serializeScheme);
            definition.getPropertyValues().add("url", url);
            definition.setBeanClass(factoryBean.getClass());
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) { this.annotationClass = annotationClass; }

    public void setSerializeScheme(SerializeScheme serializeScheme) {
        this.serializeScheme = serializeScheme;
    }

    public void setServiceInvoker(CoreServiceInvoker serviceInvoker) {
        this.serviceInvoker = serviceInvoker;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
