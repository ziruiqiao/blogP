package zirui.blog.dao.mapper;

import org.springframework.stereotype.Repository;
import zirui.blog.dao.dos.Archives;
import zirui.blog.dao.pojo.Article;
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
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * @Description: 获取文章归档
     * @return: java.util.List<zirui.blog.dao.dos.Archives>
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 15:26
     */
    List<Archives> listArchives();
}
