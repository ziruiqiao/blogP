package zirui.blog.dao.mapper;

import org.springframework.stereotype.Repository;
import zirui.blog.dao.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * @Description: 根据文章id查询标签列表
     * @param articleId id
     * @return: java.util.List<zirui.blog.dao.pojo.Tag>
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 12:40
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * @Description: 查询最热标签前 limit 条
     * @param limit limit
     * @return: java.util.List<java.lang.Long>
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 13:57
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * @param tagIds tagId list
     * @Description: 根据一堆tagId 查询tags
     * @return: java.util.List<zirui.blog.dao.pojo.Tag>
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 14:14
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
