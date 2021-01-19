package pub.hejun.cloud.auth.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import pub.hejun.colud.common.constants.ResultCode;
import pub.hejun.colud.common.vo.Result;

/**
 * 自定义认证异常
 *
 * @author HeJun
 */
@Aspect
@Configuration
public class ExceptionTranslatorConfiguration {

    /**
     * 使用AOP包装成功时返回结果,统一返回风格
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public ResponseEntity tokenEnhancer(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        if (proceed != null) {
            ResponseEntity<OAuth2AccessToken> entity = (ResponseEntity<OAuth2AccessToken>) proceed;
            OAuth2AccessToken accessToken = entity.getBody();
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Result.SUCCESS(accessToken));
        }
        return null;
    }

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return e -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Result.ERROR(ResultCode.INTERNAL_SERVER_ERROR, "登录失败", e.getMessage()));
    }
}
