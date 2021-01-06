package pub.hejun.cloud.uac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pub.hejun.cloud.uac.entity.User;

/**
 * User Mapper
 *
 * @author HeJun
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
