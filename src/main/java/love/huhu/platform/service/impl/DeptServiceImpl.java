package love.huhu.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import love.huhu.platform.domain.Dept;
import love.huhu.platform.service.DeptService;
import love.huhu.platform.mapper.DeptMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_dept(部门)】的数据库操作Service实现
* @createDate 2023-03-03 15:27:20
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
    implements DeptService{

}




