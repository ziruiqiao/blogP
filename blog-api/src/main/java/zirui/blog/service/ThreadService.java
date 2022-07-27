package zirui.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import zirui.blog.dao.mapper.ArticleMapper;
import zirui.blog.dao.pojo.Article;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: ThreadService
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/26 11:54
 */
@Component
public class ThreadService {
    /**
     * @Description: 期望此操作在线程池中执行
     *               不影响主线程的操作
     * @param articleMapper
     * @param article
     * @return: void
     * @Author: Zirui Qiao
     * @Date: 2022/7/26 12:03
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        queryWrapper.eq(Article::getViewCounts,viewCounts);
        // update article set view_count=#{viewCounts + 1} where view_count=#{viewCounts} and id=#{id}
        articleMapper.update(articleUpdate,queryWrapper);
    }
}
