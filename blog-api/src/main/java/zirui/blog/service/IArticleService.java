package zirui.blog.service;

import org.springframework.transaction.annotation.Transactional;
import zirui.blog.dao.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import zirui.blog.vo.params.ArticleParam;
import zirui.blog.vo.params.PageParams;
import zirui.blog.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@Transactional
public interface IArticleService extends IService<Article> {

    /**
     * @Description: 分页查询所有文章
     * @param pageParams 分页
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 12:39
     */
    Result listArticle(PageParams pageParams);

    /**
     * @Description: 最热文章
     * @param limit 几个
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 14:36
     */
    Result hotArticle(int limit);

    /**
     * @Description: 最新文章
     * @param limit 几个
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 15:02
     */
    Result newArticles(int limit);

    /**
     * @Description: 文章归档
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 15:10
     */
    Result listArchives();

    /**
     * @param id 文章id
     * @Description: 查询文章主体
     * @return: zirui.blog.vo.ArticleVo
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 23:14
     */
    Result findArticleById(Long id);

    /**
     * @Description: 发布文章
     * @param articleParam 需要的各种参数
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/27 10:34
     */
    Result publish(ArticleParam articleParam);
}
