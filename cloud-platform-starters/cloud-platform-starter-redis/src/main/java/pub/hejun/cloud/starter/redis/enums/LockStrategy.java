package pub.hejun.cloud.starter.redis.enums;

/**
 * 锁策略
 *
 * @author HeJun
 */
public enum LockStrategy {

    /**
     * 一般锁策略
     */
    SIMPLE,
    /**
     * RedLock策略
     */
    RED_LOCK
}
