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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Getter
@Setter
@TableName(value ="sys_dept")

public class Dept implements Serializable {

//    @TableField("dept_id")
    @TableId("dept_id")
    private Long id;

    private String name;
    /**
     * 上级部门
     */
    @TableField(value = "pid")
    private Long pid;

    /**
     * 子部门数目
     */
    @TableField(value = "sub_count")
    private Integer subCount;
    /**
     * 状态
     */
    @TableField(value = "enabled")
    private Boolean enabled;
}
