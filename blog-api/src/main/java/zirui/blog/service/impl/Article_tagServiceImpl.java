package zirui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import zirui.blog.dao.pojo.Article_tag;
import zirui.blog.dao.mapper.Article_tagMapper;
import zirui.blog.service.IArticle_tagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zirui.blog.vo.TagVo;
import zirui.blog.vo.params.ArticleParam;
import zirui.blog.vo.params.PageParams;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@Service
public class Article_tagServiceImpl extends ServiceImpl<Article_tagMapper, Article_tag> implements IArticle_tagService {

    @Autowired
    private Article_tagMapper articleTagMapper;

    @Override
    public List<String> listArticleByTagId(PageParams pageParams) {

        List<String> articleIdList = new ArrayList<>();
        if(pageParams.getTagId() != null) {
            // article_tag article_id 1:n tag_id
            LambdaQueryWrapper<Article_tag> atWrapper = new LambdaQueryWrapper<>();
            atWrapper.eq(Article_tag::getTagId, pageParams.getTagId());
            List<Article_tag> articleTags = articleTagMapper.selectList(atWrapper);
            for (Article_tag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if(articleIdList.size() > 0) {
                return articleIdList;
            }
        }
        return null;
    }

    @Override
    public void loadArticleTag(ArticleParam articleParam, String articleId) {
        List<TagVo> tags = articleParam.getTags(); // 获取文章标签

        if(tags != null) {
            for (TagVo tag : tags) {
                Article_tag articleTag = new Article_tag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
    }
}
