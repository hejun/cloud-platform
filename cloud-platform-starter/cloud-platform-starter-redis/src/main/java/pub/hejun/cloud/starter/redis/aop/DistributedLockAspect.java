package pub.hejun.cloud.starter.redis.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;
import pub.hejun.cloud.starter.redis.annotations.DistributedLock;
import pub.hejun.cloud.starter.redis.misc.Lock;
import pub.hejun.cloud.starter.redis.misc.LockFactory;

/**
 * 分布式锁 实现
 *
 * @author HeJun
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class DistributedLockAspect {

    private final ExpressionParser parser = new SpelExpressionParser();
    private final RedisOperations redisOperations;

    /**
     * AOP切点
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(pub.hejun.cloud.starter.redis.annotations.DistributedLock)")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        DistributedLock annotation = AnnotationUtils.getAnnotation(methodSignature.getMethod(), DistributedLock.class);

        String[] parameters = methodSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();
        String keyPrefix = methodSignature.getDeclaringType().getSimpleName() + "_" + methodSignature.getName();

        String key = this.parseSpEL(keyPrefix, annotation.key(), parameters, args);
        String value = String.valueOf(Thread.currentThread().getId());

        boolean hasLocked = false;
        Object proceed = null;
        Lock lock = LockFactory.getLock(annotation.strategy(), redisOperations);
        try {
            if (lock.tryLock(key, value, annotation.expire(), annotation.timeUnit()) > 0) {
                log.info("[分布式锁] 线程[{}]加锁成功! 锁key: {}", value, key);
                hasLocked = true;
                proceed = proceedingJoinPoint.proceed();
            }
        } finally {
            if (hasLocked) {
                lock.unLock(key, value);
                log.info("[分布式锁] 线程[{}]已解锁!", value);
            }
        }
        return proceed;
    }

    /**
     * 解析 SpEL
     *
     * @param keyPrefix
     * @param spEL
     * @param parameters
     * @param args
     * @return
     */
    private String parseSpEL(String keyPrefix, String spEL, String[] parameters, Object[] args) {
        if (StringUtils.hasText(spEL)) {
            EvaluationContext context = new StandardEvaluationContext();
            for (int i = 0; i < parameters.length; i++) {
                context.setVariable(parameters[i], args[i]);
            }
            Expression expression = parser.parseExpression(spEL);
            return keyPrefix + "_" + expression.getValue(context);
        }
        return keyPrefix;
    }
}
