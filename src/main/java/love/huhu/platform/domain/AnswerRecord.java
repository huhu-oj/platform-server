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
package love.huhu.platform.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @website https://eladmin.vip
* @description /
* @author nwl
* @date 2023-02-13
**/
@Getter
@TableName(value ="oj_answer_record")
@Setter
public class AnswerRecord implements Serializable {

    @Id
    @ApiModelProperty(value = "id")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "所属题目")
    private Long problemId;
    @ApiModelProperty(value = "所属用户")
    private Long userId;

    @NotBlank
    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "执行时间")
    private Long executeTime;

    @NotNull
    @ApiModelProperty(value = "所属语言")
    private Long languageId;

    @ApiModelProperty(value = "日志")
    private String log;

    @ApiModelProperty(value = "错误日志")
    private String error;

    @ApiModelProperty(value = "通过数")
    private Integer passNum;

    @ApiModelProperty(value = "未通过数")
    private Integer notPassNum;

    @ApiModelProperty(value = "执行结果")
    private Long executeResultId;

    @ApiModelProperty(value = "所属测验")
    private Long testId;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @TableField(exist = false)
    private String input;
    public void copy(AnswerRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
