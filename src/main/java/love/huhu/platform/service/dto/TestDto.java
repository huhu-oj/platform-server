package love.huhu.platform.service.dto;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import love.huhu.platform.domain.Dept;
import love.huhu.platform.domain.ExaminationPaper;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class TestDto {
    @Id
    @ApiModelProperty(value = "id")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "标题")
    private String title;

    @NotBlank
    @ApiModelProperty(value = "备注")
    private String description;

    @NotNull
    @ApiModelProperty(value = "试卷")
    private ExaminationPaper examinationPaper;

    @NotNull
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    private DateTime startTime;

    @NotNull
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private DateTime endTime;

//    @NotNull
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

//    @NotNull
    @ApiModelProperty(value = "所属用户")
    private Long userId;

    @NotNull
    private List<Dept> depts;
}
