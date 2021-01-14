package pub.hejun.cloud.uac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 安全配置
 *
 * @author HeJun
 */
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange().anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .opaqueToken()
                .introspectionClientCredentials("root", "1234")
                // TODO 用负载均衡实现 NimbusReactiveOpaqueTokenIntrospector
                .introspectionUri("http://localhost:9001/oauth/check_token");
        return http.build();
    }
}
