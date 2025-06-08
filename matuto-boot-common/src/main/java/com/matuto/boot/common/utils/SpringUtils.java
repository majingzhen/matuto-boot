package com.matuto.boot.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类
 *
 * @author Matuto
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name Bean名称
     * @return Object Bean实例
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz 类型
     * @return Bean实例
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return beanFactory.getBean(clz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name Bean名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name Bean名称
     * @return boolean
     * @throws BeansException
     */
    public static boolean isSingleton(String name) throws BeansException {
        return beanFactory.isSingleton(name);
    }

    /**
     * 获取注册对象的类型
     *
     * @param name Bean名称
     * @return Class 注册对象的类型
     * @throws BeansException
     */
    public static Class<?> getType(String name) throws BeansException {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name Bean名称
     * @return String[]
     * @throws BeansException
     */
    public static String[] getAliases(String name) throws BeansException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取当前环境
     *
     * @return 当前环境
     */
    public static String getActiveProfile() {
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        return activeProfiles.length > 0 ? activeProfiles[0] : null;
    }
} 