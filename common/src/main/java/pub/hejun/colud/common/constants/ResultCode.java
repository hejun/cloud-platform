package pub.hejun.colud.common.constants;

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
    ;
    private Integer code;
    private String desc;
}
