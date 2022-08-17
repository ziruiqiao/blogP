package zirui.blog.service;

import zirui.blog.dao.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import zirui.blog.vo.Result;
import zirui.blog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
public interface ITagService extends IService<Tag> {
    List<TagVo> findTagsByArticleId(String articleId);

    /**
     * @Description: 查询最热标签
     * @param limit 查询几个
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 13:48
     */
    Result hots(int limit);

    /**
     * @Description: 查询所有文章标签
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/27 10:15
     */
    Result findAll();

    /**
     * @Description: 查询所有文章标签detail
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/27 17:29
     */
    Result findAllDetail();

    /**
     * @Description: 根据id查找标签
     * @param id id
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/27 18:09
     */
    Result findDetailById(String id);
}
