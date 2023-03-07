package love.huhu.platform.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description
 * @Author nwl
 * @Create 2023-03-05 下午8:54
 */
@Getter
@Setter
public class ProblemKnowledgeDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    private Long problemId;

    private KnowledgeDto knowledge;

    private Integer weight;
}
