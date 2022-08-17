package zirui.blog.service;

import zirui.blog.dao.pojo.Article_tag;
import com.baomidou.mybatisplus.extension.service.IService;
import zirui.blog.vo.params.ArticleParam;
import zirui.blog.vo.params.PageParams;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
public interface IArticle_tagService extends IService<Article_tag> {
    List<String> listArticleByTagId(PageParams pageParams);

    void loadArticleTag(ArticleParam articleParam, String articleId);
}
