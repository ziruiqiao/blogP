package zirui.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 评论数量
     */
    private Integer commentCounts;

    /**
     * 创建时间
     */
    private Long createDate;

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
    private Integer viewCounts;

    /**
     * 是否置顶
     */
    private Integer weight;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 内容id
     */
    private String bodyId;

    /**
     * 类别id
     */
    private String categoryId;


}
