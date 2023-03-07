package love.huhu.platform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

/**
 * 测验用户关联
 * @TableName oj_test_user
 */
@TableName(value ="oj_test_user")
@Data
public class TestUser implements Serializable {
    /**
     * 测验id
     */
    @MppMultiId
    private Long testId;

    /**
     * 用户id
     */
    @MppMultiId
    private Long userId;

    /**
     * 测验分数
     */
    @TableField(value = "score")
    private Double score;

    /**
     * 测验数据序列化字符串
     */
    @TableField(value = "test_json_str")
    private String testJsonStr;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}