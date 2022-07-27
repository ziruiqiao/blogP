package zirui.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import zirui.blog.common.app.LogAnnotation;
import zirui.blog.service.IArticleService;
import zirui.blog.vo.params.ArticleParam;
import zirui.blog.vo.params.PageParams;
import zirui.blog.vo.Result;

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
//    @Cache(expire = 5 * 60 * 1000,name = "listArticle")
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
    public Result listArchives() {
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id")Long id){

        return articleService.findArticleById(id);
    }

    @PostMapping("publish")
    public Result publishArticle(@RequestBody ArticleParam articleParam) {
        System.out.println("articleParam = " + articleParam);
        return articleService.publish(articleParam);
    }

}

