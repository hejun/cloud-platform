package pub.hejun.cloud.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果状态码枚举
 *
 * @author HeJun
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    OK(200, "成功"),
    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    INTERNAL_SERVER_ERROR(500, "服务器异常"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    ;
    private Integer code;
    private String desc;
}