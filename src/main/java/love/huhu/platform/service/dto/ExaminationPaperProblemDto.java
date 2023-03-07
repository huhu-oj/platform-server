package love.huhu.platform.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author nwl
 * @Create 2023-03-06 下午6:37
 */
@Getter
@Setter
public class ExaminationPaperProblemDto {

    private Long id;

    private Long examinationPaperId;

    private ProblemDto problem;

    private Double score;
}
