package pub.hejun.cloud.starter.redis.misc.impl;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import pub.hejun.cloud.starter.redis.misc.Lock;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 一般锁
 *
 * @author HeJun
 */
@Slf4j
@RequiredArgsConstructor
public class SimpleLock implements Lock {

    private final RedisOperations redisOperations;

    private Timer timer = new HashedWheelTimer();
    private final Map<String, Boolean> currentStatusMap = new ConcurrentHashMap<>(1);

    private final RedisScript<Integer> lockScript = new DefaultRedisScript<>(
            "if(redis.call('exists', KEYS[1]) == 0) then " +
                    "redis.call('SET', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2]);" +
                    "return '1';" +
                    "else " +
                    "return '0';" +
                    "end;", Integer.class);
    private final RedisScript<Integer> unlockScript = new DefaultRedisScript<>(
            "if(redis.call('get', KEYS[1]) == ARGV[1]) then " +
                    "redis.call('del', KEYS[1]);" +
                    "return '1';" +
                    "else " +
                    "return '0';" +
                    "end;", Integer.class);
    private final RedisScript<Integer> renewExpiration = new DefaultRedisScript<>(
            "if(redis.call('get', KEYS[1]) == ARGV[1]) then " +
                    "redis.call('pexpire', KEYS[1], ARGV[2]);" +
                    "return '1';" +
                    "else " +
                    "return '0';" +
                    "end;", Integer.class);

    @Override
    public Integer tryLock(String key, String value, long expire, TimeUnit timeUnit) {
        List<String> keys = Collections.singletonList(key);
        Object[] args = new Object[]{value, timeUnit.toMillis(expire)};
        currentStatusMap.put(key, true);
        Integer result = (Integer) redisOperations.execute(lockScript, keys, args);
        if (result > 0) {
            this.renewExpiration(key, value, expire, timeUnit);
        }
        return result;
    }

    @Override
    public Integer unLock(String key, String value) {
        List<String> keys = Collections.singletonList(key);
        Object[] args = new Object[]{value};
        currentStatusMap.clear();
        return (Integer) redisOperations.execute(unlockScript, keys, args);
    }

    private void renewExpiration(String key, String value, long expire, TimeUnit timeUnit) {
        long expireTime = timeUnit.toMillis(expire);
        TimerTask timerTask = timeout -> {
            if (currentStatusMap.getOrDefault(key, false)) {
                List<String> keys = Collections.singletonList(key);
                Object[] args = new Object[]{value, expireTime};
                Integer result = (Integer) redisOperations.execute(renewExpiration, keys, args);
                log.info("[分布式锁] 线程[{}]自动续期! 锁key: {}", value, key);
                if (result > 0) {
                    SimpleLock.this.renewExpiration(key, value, expire, timeUnit);
                }
            }
        };
        timer.newTimeout(timerTask, expireTime - 500, TimeUnit.MILLISECONDS);
    }
}
