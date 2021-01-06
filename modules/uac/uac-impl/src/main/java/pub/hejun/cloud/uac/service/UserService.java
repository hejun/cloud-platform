package pub.hejun.cloud.uac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pub.hejun.cloud.uac.entity.User;
import pub.hejun.cloud.uac.mapper.UserMapper;

/**
 * @author HeJun
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public IPage<User> selectPage(Long current, Long size) {
        Page<User> page = new Page<>(current, size);
        return userMapper.selectPage(page, Wrappers.emptyWrapper());
    }

    public User selectByUserName(String username) {
        return username == null ? null : userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username));
    }
}
