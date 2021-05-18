package pub.hejun.cloud.starter.redis.misc.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import pub.hejun.cloud.starter.redis.misc.Lock;

import java.util.concurrent.TimeUnit;

/**
 * RedLock
 *
 * @author HeJun
 */
@Slf4j
@RequiredArgsConstructor
public class RedLock implements Lock {

    private final RedisOperations redisOperations;

    @Override
    public Integer tryLock(String key, String value, long expire, TimeUnit timeUnit) {
        return 0;
    }

    @Override
    public Integer unLock(String key, String value) {
        return 0;
    }
}
