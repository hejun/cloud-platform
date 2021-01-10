package pub.hejun.cloud.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * 初始化基本路由,待转为数据库配置后删除
 *
 * @author HeJun
 */
@Component
@RequiredArgsConstructor
public class InitializeRouteConfiguration {

    private final RouteDefinitionRepository routeDefinitionRepository;

    @PostConstruct
    public void init() {
        FilterDefinition filter = new FilterDefinition("StripPrefix=1");
        // Auth
        RouteDefinition authDefinition = new RouteDefinition("authId=lb://auth,Path=/auth/**");
        authDefinition.getFilters().add(filter);
        routeDefinitionRepository.save(Mono.just(authDefinition)).subscribe();
        // Uac
        RouteDefinition uacDefinition = new RouteDefinition("uacId=lb://uac,Path=/uac/**");
        uacDefinition.getFilters().add(filter);
        routeDefinitionRepository.save(Mono.just(uacDefinition)).subscribe();
    }
}
