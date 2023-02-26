package love.huhu.platform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description
 * @Author nwl
 * @Create 2023-02-25 上午8:52
 */
@TableName(value ="sys_user")
@Data
public class User implements Serializable {
    /**
     * ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long Id;

    @TableField (exist = false)
    @ApiModelProperty(value = "用户角色")
    private Set<Role> roles;

    @TableField (exist = false)
    @ApiModelProperty(value = "用户岗位")
    private Set<Job> jobs;

    @TableField (exist = false)
    @ApiModelProperty(value = "用户部门")
    private Dept dept;

    @TableField(value = "username")
    @NotBlank
    @ApiModelProperty(value = "用户名称")
    private String username;

    @TableField(value = "nick_name")
    @NotBlank
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @TableField(value = "email")
    @Email
    @NotBlank
    @ApiModelProperty(value = "邮箱")
    private String email;

    @TableField(value = "phone")
    @NotBlank
    @ApiModelProperty(value = "电话号码")
    private String phone;

    @TableField(value = "gender")
    @ApiModelProperty(value = "用户性别")
    private String gender;

    @TableField(value = "avatar_name")
    @ApiModelProperty(value = "头像真实名称",hidden = true)
    private String avatarName;

    @TableField(value = "avatar_path")
    @ApiModelProperty(value = "头像存储的路径", hidden = true)
    private String avatarPath;

    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @TableField(value = "enabled")
    @NotNull
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @TableField(value = "is_admin")
    @ApiModelProperty(value = "是否为admin账号", hidden = true)
    private Boolean isAdmin = false;

    @TableField(value = "pwd_reset_time")
    @ApiModelProperty(value = "最后修改密码的时间", hidden = true)
    private Date pwdResetTime;

}
