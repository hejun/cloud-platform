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
        // Test
        RouteDefinition testDefinition = new RouteDefinition("testId=https://www.baidu.com,Path=/test/**");
        testDefinition.getFilters().add(filter);
        routeDefinitionRepository.save(Mono.just(testDefinition)).subscribe();
    }
}
