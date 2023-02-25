package love.huhu.platform.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 上午9:41
 */
public class UserLoginDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
