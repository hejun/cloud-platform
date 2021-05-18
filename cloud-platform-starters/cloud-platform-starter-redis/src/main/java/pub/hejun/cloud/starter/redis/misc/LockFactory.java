package pub.hejun.cloud.starter.redis.misc;

import org.springframework.data.redis.core.RedisOperations;
import pub.hejun.cloud.starter.redis.enums.LockStrategy;
import pub.hejun.cloud.starter.redis.misc.impl.RedLock;
import pub.hejun.cloud.starter.redis.misc.impl.SimpleLock;

/**
 * 锁实现工场
 *
 * @author HeJun
 */
public class LockFactory {
    public static Lock getLock(LockStrategy strategy, RedisOperations redisOperations) {
        switch (strategy) {
            case RED_LOCK: {
                return new RedLock(redisOperations);
            }
            default: {
                return new SimpleLock(redisOperations);
            }
        }
    }
}
