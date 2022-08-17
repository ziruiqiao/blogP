package zirui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zirui.blog.dao.dos.Archives;
import zirui.blog.dao.mapper.ArticleBodyMapper;
import zirui.blog.dao.mapper.ArticleMapper;
import zirui.blog.dao.pojo.Article;
import zirui.blog.dao.pojo.ArticleBody;
import zirui.blog.dao.pojo.Sys_user;
import zirui.blog.service.*;
import zirui.blog.util.CopyUtil;
import zirui.blog.util.UserThreadLocal;
import zirui.blog.vo.*;
import zirui.blog.vo.params.ArticleParam;
import zirui.blog.vo.params.PageParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ITagService tagService;
    @Autowired
    private ISys_userService sysUserService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private  IArticle_tagService articleTagService;

    @Override
    public Result listArticle(PageParams pageParams) {

        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                                page,
                StringUtils.isBlank(pageParams.getCategoryId()) ? null : Long.parseLong(pageParams.getCategoryId()),
                StringUtils.isBlank(pageParams.getTagId()) ? null : Long.parseLong(pageParams.getTagId()),
                                pageParams.getYear(),
                                pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true, false, true));
    }

    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderByDesc(Article::getCreateDate)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        // select id, title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false, false, false));
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderByDesc(Article::getViewCounts)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        // select id, title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        return Result.success(copyList(articles, false, false, false, false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(String id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        // 查看完文章，新增阅读数
        // 查看完文章之后，本应该直接返回数据了，这个时候做了一个更新操作，更新时加写锁，阻塞其他读操作，性能较低
        // 更新增加了接口的耗时 一旦更新出问题，不能影响查看文章的操作
        // 通过线程池优化
        threadService.updateArticleViewCount(articleMapper, article);
        System.out.println("\n\n articleVo = " + articleVo + "\n\n");
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        // 此接口要加入到登录拦截当中
        Sys_user sysUser = UserThreadLocal.get();
        /**
         * 1. 发布文章 构建article对象
         * 1.1 作者id，当前登录用户
         * 1.2 标签 将标签加入到关联列表当中
         * 1.3 body 内容存储
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId()); // 对应作者id
        article.setWeight(Article.Article_Common); // 权重
        article.setTitle(articleParam.getTitle()); // title
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(articleParam.getCategory().getId());
        articleMapper.insert(article);
        String articleId = article.getId();

        articleTagService.loadArticleTag(articleParam, articleId);
        // body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    @Override
    public Result search(String search) {
        Page<Article> page = new Page<>(PageParams.DEFAULT_PAGE, PageParams.DEFAULT_SIZE);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getTitle)
                .like(Article::getTitle, search)
                .or()
                .like(Article::getSummary, search);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();

        List<ArticleVo> articleVoList = copyList(records, false, false, false, false);

        return Result.success(articleVoList);
    }

    @Override
    public Result delArticleById(String id) {
        int id1 = articleMapper.deleteById(Long.parseLong(id));
        if(id1 > 0) {
            return Result.success(null);
        } else {
            return Result.fail(ErrorCode.ARTICLE_UNEXIST);
        }
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,
                                     boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        records.forEach(r -> {
            articleVoList.add(copy(r, isTag, isAuthor, isBody, isCategory));
        });
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor,
                           boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo, CopyUtil.getNullPropertyNames(article));

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // 并不是所有的接口都需要标签，作者信息
        if(isTag) {
            List<TagVo> tagsByArticleId = tagService.findTagsByArticleId(article.getId());
            articleVo.setTags(tagsByArticleId);
        }
        if(isAuthor) {
            String author_id = article.getAuthorId();
            Sys_user userById = sysUserService.findUserById(author_id);
            Author author = new Author();
            author.setAvatar(userById.getAvatar());
            author.setNickname(userById.getNickname());
            author.setId(userById.getId());
            articleVo.setAuthor(author);
        }
        if(isBody) {
            String bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if(isCategory) {
            String categoryId = String.valueOf(article.getCategoryId());
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(String bodyId) {
        ArticleBody body = articleBodyMapper.selectById(bodyId);
        return CopyUtil.copyBean(body, ArticleBodyVo.class);
    }
}
