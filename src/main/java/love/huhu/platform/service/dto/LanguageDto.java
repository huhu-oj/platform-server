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
* @date 2023-02-07
**/
@Getter
@Setter
public class LanguageDto implements Serializable {


    @ApiModelProperty(value = "id")
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "语言名称")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "编译语句")
    private String compileStatement;

    @ApiModelProperty(value = "更新时间")
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    private DateTime createTime;

    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    private DateTime updateTime;
    private List<AnswerRecordDto> answerRecords;

    private List<JudgeMachineDto> judgeMachines;
    public void copy(LanguageDto source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
