package pub.hejun.cloud.starter.redis.annotations;

import pub.hejun.cloud.starter.redis.enums.LockStrategy;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁 注解
 *
 * @author HeJun
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {

    /**
     * 分布式锁唯一 key SpEL 获取表达式
     *
     * @return
     */
    String key();

    /**
     * 锁策略
     *
     * @return
     */
    LockStrategy strategy() default LockStrategy.SIMPLE;

    /**
     * 过期时间
     *
     * @return
     */
    long expire() default 30000L;

    /**
     * 过期时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
