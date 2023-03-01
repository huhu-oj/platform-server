package love.huhu.platform.config.porperties;

import lombok.Data;

/**
 * @Author nwl20
 * @create 2022/9/29 15:22
 */
@Data
public class AuthorizationProperties {
    //  header: Authorization
    private String header;
    //  # 令牌前缀
    //  token-start-with: Bearer
    private String tokenStartWith;
    //  # 必须使用最少88位的Base64对该令牌进行编码
    //  base64-secret: ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI=
    private String base64Secret;
    //  # 令牌过期时间 此处单位/毫秒 ，默认4小时，可在此网站生成 https://www.convertworld.com/zh-hans/time/milliseconds.html
    //  token-validity-in-seconds: 14400000
    private Long tokenValidityInSeconds;
    //  # 在线用户key
    //  online-key: online-token-
    private String onlineKey;
    //  # 验证码
    //  code-key: code-key-
    private String codeKey;
    //  # token 续期检查时间范围（默认30分钟，单位毫秒），在token即将过期的一段时间内用户操作了，则给用户的token续期
    //  detect: 1800000
    private Long detect;
    //  # 续期时间范围，默认1小时，单位毫秒
    //  renew: 3600000
    private Long renew;

    private Boolean skipToken;
    public String getTokenStartWith() {
        return this.tokenStartWith + " ";
    }
}
