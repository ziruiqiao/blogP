package zirui.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zirui.blog.common.app.LogAnnotation;
import zirui.blog.service.CommentsService;
import zirui.blog.vo.Result;
import zirui.blog.vo.params.CommentParam;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CommentsController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/26 13:21
 */
@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    @LogAnnotation(module="评论",operator="根据文章id获取所有评论")
    public Result comments(@PathVariable("id") String id) {
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    @LogAnnotation(module="评论",operator="添加评论")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
