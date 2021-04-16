package pub.hejun.cloud.starter.mybatis.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * Mybatis-Plus配置类
 *
 * @author HeJun
 */
@Configuration
@ConditionalOnBean(DataSource.class)
public class MyBatisConfiguration {

    /**
     * 分页插件
     *
     * @param platform 数据库类型,默认为 MySql
     */
    @Bean
    @Order(20)
    @ConditionalOnMissingBean
    public PaginationInnerInterceptor paginationInnerInterceptor(@Value("${spring.datasource.platform:mysql}") String platform) {
        return new PaginationInnerInterceptor(DbType.getDbType(platform));

    }

    /**
     * 乐观锁
     */
    @Bean
    @Order(30)
    @ConditionalOnMissingBean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 注册插件
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Autowired(required = false) List<InnerInterceptor> interceptors) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (!CollectionUtils.isEmpty(interceptors)) {
            interceptors.forEach(interceptor::addInnerInterceptor);
        }
        return interceptor;
    }
}
