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
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author nwl
* @date 2023-02-13
**/
@Getter
@Setter
public class AnswerRecordDto implements Serializable {


    @ApiModelProperty(value = "id")
    private Long id;


    @NotNull
    @ApiModelProperty(value = "所属题目")
    private ProblemDto problem;
    @NotNull
    @ApiModelProperty(value = "所属用户")
    private Long userId;
    private TestDto test;

    @NotBlank
    @ApiModelProperty(value = "代码")
    private String code;

    @NotNull
    @ApiModelProperty(value = "执行时间")
    private Long executeTime;

    @NotNull
    @ApiModelProperty(value = "所属语言")
    private LanguageDto language;
    @NotBlank
    @ApiModelProperty(value = "日志")
    private String log;

    @NotBlank
    @ApiModelProperty(value = "错误日志")
    private String error;

    @NotNull
    @ApiModelProperty(value = "通过数")
    private Integer passNum;

    @NotNull
    @ApiModelProperty(value = "未通过数")
    private Integer notPassNum;


    @NotNull
    @ApiModelProperty(value = "执行结果")
    private ExecuteResultDto executeResult;

    @NotBlank
    @ApiModelProperty(value = "备注")
    private String note;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    private DateTime createTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-ddTHH:mm:ss")
    private DateTime updateTime;
    public void copy(AnswerRecordDto source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
