package pub.hejun.cloud.uac.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 安全配置
 *
 * @author HeJun
 */
@Configuration
public class SecurityConfiguration {

    @Bean
    public ReactiveOpaqueTokenIntrospector reactiveOpaqueTokenIntrospector(LoadBalancerClient loadBalancerClient,
                                                                           OAuth2ResourceServerProperties properties) {
        OAuth2ResourceServerProperties.Opaquetoken opaqueToken = properties.getOpaquetoken();
        WebClient webClient = WebClient.builder()
                .filter(new LoadBalancerExchangeFilterFunction(loadBalancerClient))
                .defaultHeaders(h -> h.setBasicAuth(opaqueToken.getClientId(), opaqueToken.getClientSecret()))
                .build();
        return new NimbusReactiveOpaqueTokenIntrospector(opaqueToken.getIntrospectionUri(), webClient);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveOpaqueTokenIntrospector reactiveOpaqueTokenIntrospector) {
        http
                .authorizeExchange().anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .opaqueToken()
                .introspector(reactiveOpaqueTokenIntrospector);
        return http.build();
    }
}
