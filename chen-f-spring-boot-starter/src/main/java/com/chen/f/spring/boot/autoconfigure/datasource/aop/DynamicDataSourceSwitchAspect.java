package com.chen.f.spring.boot.autoconfigure.datasource.aop;

import com.chen.f.spring.boot.autoconfigure.datasource.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import  com.chen.f.spring.boot.autoconfigure.datasource.annotation.DataSource;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

/**
 * aop实现动态切换数据源
 *
 * @author chen
 * @since 2017/12/6 19:41.
 */
@Aspect
public class DynamicDataSourceSwitchAspect implements Ordered {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceSwitchAspect.class);

    private int order = Ordered.HIGHEST_PRECEDENCE;

    @Around(value = "@within(com.chen.f.spring.boot.autoconfigure.datasource.annotation.DataSource) || @annotation(com.chen.f.spring.boot.autoconfigure.datasource.annotation.DataSource)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        DataSource classDataSource = proceedingJoinPoint.getTarget().getClass().getAnnotation(DataSource.class);
        DataSource methodDataSource = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(DataSource.class);
        String value = methodDataSource != null ? methodDataSource.value() : classDataSource.value();
        logger.debug("切换数据源:[{}]", value);
        DynamicDataSourceHolder.setDataSourceKey(value);
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            logger.debug("数据源:[{}]使用结束,删除数据源", value);
            DynamicDataSourceHolder.removeDataSourceKey();
        }
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}
