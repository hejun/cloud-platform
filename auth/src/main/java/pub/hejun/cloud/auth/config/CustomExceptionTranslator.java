package pub.hejun.cloud.auth.config;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import pub.hejun.colud.common.constants.ResultCode;
import pub.hejun.colud.common.vo.Result;

/**
 * 自定义认证异常
 *
 * @author HeJun
 */
@Component
public class CustomExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Result.ERROR(ResultCode.INTERNAL_SERVER_ERROR, "登录失败", e.getMessage()));
    }
}
