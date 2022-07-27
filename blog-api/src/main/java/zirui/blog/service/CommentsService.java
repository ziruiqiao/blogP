package zirui.blog.service;

import zirui.blog.vo.Result;
import zirui.blog.vo.params.CommentParam;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CommentsService
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/26 13:28
 */
public interface CommentsService {
    /**
     * @Description: 查询文章的评论
     * @param id 文章id
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/26 13:29
     */
    Result commentsByArticleId(Long id);

    /**
     * @Description: 获取评论
     * @param commentParam 评论参数
     * @return: zirui.blog.vo.Result
     * @Author: Zirui Qiao
     * @Date: 2022/7/26 16:15
     */
    Result comment(CommentParam commentParam);
}
