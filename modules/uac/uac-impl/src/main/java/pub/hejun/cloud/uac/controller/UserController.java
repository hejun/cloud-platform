package pub.hejun.cloud.uac.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pub.hejun.cloud.uac.entity.User;
import pub.hejun.cloud.uac.service.UserService;
import pub.hejun.colud.common.vo.Result;

/**
 * 用户 Controller
 *
 * @author HeJun
 */
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Result<User> selectByUsername(@RequestParam(defaultValue = "1") Long current,
                                         @RequestParam(defaultValue = "15") Long size) {
        return Result.SUCCESS(userService.selectPage(current, size));
    }

    @GetMapping("/{username}")
    public Result<User> selectByUsername(@PathVariable String username) {
        return Result.SUCCESS(userService.selectByUserName(username));
    }
}
