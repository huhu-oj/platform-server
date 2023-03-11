package love.huhu.platform.authorization;

/**
 * @Author nwl20
 * @create 2022/9/29 11:05
 */
public enum PermissionEnum {
    ADMIN("管理员"),

    ANY("any"),
    TEACHER("老师"),
    STUDENT("学生");


    PermissionEnum(String roleName) {
        this.roleName = roleName;
    }
    private String roleName;

    public String getRoleName() {
        return roleName;
    }
}
