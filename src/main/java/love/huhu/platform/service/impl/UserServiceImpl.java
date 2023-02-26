package love.huhu.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import love.huhu.platform.domain.User;
import love.huhu.platform.service.UserService;
import love.huhu.platform.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author longjiang
* @description 针对表【sys_user(系统用户)】的数据库操作Service实现
* @createDate 2023-02-26 11:25:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




