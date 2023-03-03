package love.huhu.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import love.huhu.platform.authorization.UserHolder;
import love.huhu.platform.domain.Dept;
import love.huhu.platform.service.DeptService;
import love.huhu.platform.service.TestService;
import love.huhu.platform.mapper.TestMapper;
import love.huhu.platform.service.dto.TestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import love.huhu.platform.domain.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【oj_test(测验)】的数据库操作Service实现
* @createDate 2023-03-03 13:58:07
*/
@Service
@RequiredArgsConstructor
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

    private final DeptService deptService;
    @Override
    public int saveTest(TestDto test) {
        test.setUserId(UserHolder.getUserId());
        Test toInsert = new Test();
        BeanUtils.copyProperties(test,toInsert);
        toInsert.setExaminationPaperId(test.getExaminationPaper().getId());
        baseMapper.insert(toInsert);
        return baseMapper.saveTestDept(toInsert.getId(), test.getDepts());
    }

    @Override
    public List<Long> getMyTestIds(Long selfDeptId) {
        Set<Long> superiorDeptIds = getSuperiorDept(selfDeptId);
        //查询匹配的id
        List<Long> tests = baseMapper.getTestByDeptIds(superiorDeptIds);
        return tests;
    }
    private Set<Long> getSuperiorDept(Long deptId) {
        Set<Long> deptIds = new HashSet<>();
        //递归拿到上级部门id
        Dept self = deptService.getById(deptId);
        if (self.getPid() != null) {
            deptIds.addAll(getSuperiorDept(self.getPid()));
        }
        deptIds.add(self.getId());
        return deptIds;
    }
}




