package zirui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zirui.blog.dao.mapper.CommentMapper;
import zirui.blog.dao.pojo.Comment;
import zirui.blog.dao.pojo.Sys_user;
import zirui.blog.service.CommentsService;
import zirui.blog.service.ISys_userService;
import zirui.blog.util.CopyUtil;
import zirui.blog.util.UserThreadLocal;
import zirui.blog.vo.CommentVo;
import zirui.blog.vo.LoginUserVo;
import zirui.blog.vo.Result;
import zirui.blog.vo.params.CommentParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CommentsServiceImpl
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/26 13:28
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ISys_userService sysUserService;
    @Override
    public Result commentsByArticleId(String id) {
        /**
         * 1. 查询评论列表
         * 2. 根据id查询作者信息
         * 3. 判断 如果level=1 查询有没有子评论
         *  3.1 有：查询评论
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id)
                        .eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    public CommentVo copy(Comment comment){
        CommentVo commentVo = CopyUtil.copyBean(comment,CommentVo.class);
        //时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        String authorId = comment.getAuthorId();
        LoginUserVo userVo = sysUserService.findLoginUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //评论的评论
        //if (comment.getLevel() == 1) {
            List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
            commentVo.setChildren(commentVoList);
        //}
        if (comment.getLevel() > 1) {
            String toUid = comment.getToUid();
            LoginUserVo toUserVo = sysUserService.findLoginUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(String id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }

    public List<CommentVo> copyList(List<Comment> commentList){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    @Override
    public Result comment(CommentParam commentParam) {
        Sys_user sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        String parent = commentParam.getParent();
        if (parent == null || parent == "0") {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? "0" : parent);
        String toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? "0" : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }
}
