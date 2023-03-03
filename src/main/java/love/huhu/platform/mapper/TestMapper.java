package love.huhu.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import love.huhu.platform.domain.Dept;
import love.huhu.platform.domain.Test;
import love.huhu.platform.service.dto.TestDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【oj_test(测验)】的数据库操作Mapper
* @createDate 2023-03-03 13:58:07
* @Entity love.huhu.platform.domain.Test
*/
public interface TestMapper extends BaseMapper<Test> {


    int saveTestDept(@Param("id") Long id,@Param("depts") List<Dept> dept);

    List<Long> getTestByDeptIds(@Param("ids") Set<Long> superiorDeptIds);
}




