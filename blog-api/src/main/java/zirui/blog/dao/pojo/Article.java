package zirui.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("db_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论数量
     */
    private Integer comment_counts;

    /**
     * 创建时间
     */
    private Long create_date;

    /**
     * 简介
     */
    private String summary;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览数量
     */
    private Integer view_counts;

    /**
     * 是否置顶
     */
    private Integer weight;

    /**
     * 作者id
     */
    private Long author_id;

    /**
     * 内容id
     */
    private Long body_id;

    /**
     * 类别id
     */
    private Integer category_id;


}
