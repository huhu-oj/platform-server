package love.huhu.platform.authorization;

import love.huhu.platform.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author nwl20
 * @create 2022/9/29 15:51
 */
public class UserHolder {
    private static final ThreadLocal<Map<String, Object>> USER_HOLDER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 初始化用户threadLocal
     *
     */
    public static void setUser(User user,String token) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("user", user);
        map.put("token",token);
        USER_HOLDER_THREAD_LOCAL.set(map);
    }
    public static void  setUser(User user) {
        setUser(user,null);
    }


    /**
     * 销毁用户threadLocal
     */
    public static void remove() {
        USER_HOLDER_THREAD_LOCAL.remove();
    }

    /**
     * 从threadLocal中获取用户Id
     */
    public static Long getUserId() {
        Map<String, Object> map = USER_HOLDER_THREAD_LOCAL.get();
        return ((User) map.get("user")).getId();
    }

    public static User getUser() {
        Map<String, Object> map = USER_HOLDER_THREAD_LOCAL.get();
        return ((User) map.get("user"));
    }
    public static String getToken() {

        Map<String, Object> map = USER_HOLDER_THREAD_LOCAL.get();
        return (String) map.get("token");
    }
}
