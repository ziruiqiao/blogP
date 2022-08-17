package zirui.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zirui.blog.common.app.LogAnnotation;
import zirui.blog.common.cache.Cache;
import zirui.blog.service.IArticleService;
import zirui.blog.vo.Result;
import zirui.blog.vo.params.ArticleParam;
import zirui.blog.vo.params.PageParams;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private IArticleService articleService;
    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    //加上此注解 代表要对此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    @Cache("listArticle")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     * @Description: 首页最热文章
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 14:35
     */
    @PostMapping("hot")
    @LogAnnotation(module="文章",operator="获取最热文章")
    @Cache("hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * @Description: 首页最新文章
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 14:35
     */
    @PostMapping("new")
    @LogAnnotation(module="文章",operator="获取最新文章")
    @Cache("new_article")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * @Description: 首页最新文章
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 14:35
     */
    @PostMapping("listArchives")
    @LogAnnotation(module="文章",operator="获取文章归档")
    public Result listArchives() {
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    @LogAnnotation(module="文章",operator="根据id获取文章detail")
    public Result findArticleById(@PathVariable("id")String id){

        return articleService.findArticleById(id);
    }

    @PostMapping("publish")
    @LogAnnotation(module="文章",operator="发布文章")
    public Result publishArticle(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

    @PostMapping("search")
    @LogAnnotation(module="文章",operator="搜索文章")
    public Result searchArticles(@RequestBody Map<String, String> search) {
        return articleService.search(search.get("search"));
    }

    @PostMapping("del/{id}")
    @LogAnnotation(module="文章",operator="删除文章")
    public Result delArticle(@PathVariable("id")String id) {
        return articleService.delArticleById(id);
    }

}

