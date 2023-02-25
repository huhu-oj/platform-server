package love.huhu.platform.authorization;

/**
 * @Author nwl20
 * @create 2022/9/29 11:05
 */
public enum PermissionEnum {
    ADMIN(1),

    PROBLEM_MANAGER(2),

    ANY(3);
    PermissionEnum(Integer id) {
        this.id = id;
    }
    private Integer id;

    public Integer getId() {
        return id;
    }
}
