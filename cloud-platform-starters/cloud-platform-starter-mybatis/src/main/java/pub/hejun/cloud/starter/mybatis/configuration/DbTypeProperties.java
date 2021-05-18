package pub.hejun.cloud.starter.mybatis.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库类型配置
 *
 * @author HeJun
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DbTypeProperties {

    /**
     * 数据库类型
     */
    private DbType dbType;
}
