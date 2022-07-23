package zirui.blog.dao.service.impl;

import zirui.blog.dao.pojo.Tag;
import zirui.blog.dao.mapper.TagMapper;
import zirui.blog.dao.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
