package pub.hejun.cloud.starter.redis.misc;

import java.util.concurrent.TimeUnit;

/**
 * 锁接口
 *
 * @author HeJun
 */
public interface Lock {

    /**
     * 加锁
     *
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     * @return
     */
    Integer tryLock(String key, String value, long expire, TimeUnit timeUnit);

    /**
     * 解锁
     *
     * @param key
     * @param value
     * @return
     */
    Integer unLock(String key, String value);
}
