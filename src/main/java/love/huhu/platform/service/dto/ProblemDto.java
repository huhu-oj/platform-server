/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package love.huhu.platform.service.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
* @website https://eladmin.vip
* @description /
* @author nwl
* @date 2023-02-14
**/
@Getter
@Setter
public class ProblemDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "标题")
    private String title;

    @NotBlank
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    private DateTime createTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    private DateTime updateTime;

    @ApiModelProperty(value = "渲染文本")
    private String descriptionHtml;

    private List<HintDto> hints;
//    @JSONField(serialize = false)
    private List<SolutionDto> solutions;
    private List<StandardIoDto> standardIos;
    private List<AnswerRecordDto> answerRecords;

    private List<LabelDto> labels;
    private List<ProblemKnowledgeDto> problemKnowledges;
    private List<ExaminationPaperProblemDto> examinationPaperProblems;
    public void copy(ProblemDto source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
