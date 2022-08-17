package zirui.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import zirui.blog.service.ITagService;
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
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private ITagService tagService;

    /**
     * @Description: 查询所有文章标签
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/27 10:14
     */
    @GetMapping
    public Result findAll() {
        return tagService.findAll();
    }

    /**
     * @Description: 查询所有文章标签detail
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/27 10:14
     */
    @GetMapping("detail")
    public Result findAllDetail() {
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable String id) {
        return tagService.findDetailById(id);
    }

    /**
     * @Description: 查询最热标签
     * @param
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/25 13:47
     */
    @GetMapping("hot")
    public Result hot() {
        int limit = 6;
        return tagService.hots(limit);
    }

}

