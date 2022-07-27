package zirui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import zirui.blog.dao.pojo.Tag;
import zirui.blog.dao.mapper.TagMapper;
import zirui.blog.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zirui.blog.util.CopyUtil;
import zirui.blog.vo.Result;
import zirui.blog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zirui
 * @since 2022-07-23
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // mp 无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return CopyUtil.copyList(tags, TagVo.class);
    }

    @Override
    public Result hots(int limit) {
        // 1. 标签所拥有的文章数量最多就是最热标签
        // 2. 查询， 根据tag_id 分组 计数，从大到小 排列 取前 limit 个
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        // 还需要tag名称
        List<Tag> tags = tagMapper.findTagsByTagIds(tagIds);

        return Result.success(tags);
    }

    @Override
    public Result findAll() {
        List<Tag> tags = tagMapper.selectList(new QueryWrapper<>());

        return Result.success(CopyUtil.copyList(tags, TagVo.class));
    }
}
