package love.huhu.platform.mapper;

import love.huhu.platform.domain.AnswerRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【oj_answer_record(做题记录)】的数据库操作Mapper
* @createDate 2023-03-02 14:11:39
* @Entity love.huhu.platform.domain.AnswerRecord
*/
public interface AnswerRecordMapper extends BaseMapper<AnswerRecord> {

    Integer getUserOfferUseLanguage(Long userId);
}




