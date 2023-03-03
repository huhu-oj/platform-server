package love.huhu.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import love.huhu.platform.domain.Test;
import love.huhu.platform.service.dto.TestDto;

import java.util.List;

/**
* @author Administrator
* @description 针对表【oj_test(测验)】的数据库操作Service
* @createDate 2023-03-03 13:58:07
*/
public interface TestService extends IService<Test> {

    int saveTest(TestDto test);

    List<Long> getMyTestIds(Long selfDeptId);
}
