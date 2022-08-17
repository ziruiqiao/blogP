package zirui.blog.vo.params;

import lombok.Data;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: PageParams
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/23 17:12
 */
@Data
public class PageParams {

    public static int DEFAULT_PAGE = 1;
    public static int DEFAULT_SIZE = 10;

    private int page = DEFAULT_PAGE;
    private int pageSize = DEFAULT_SIZE;
    //@JsonSerialize(using = ToStringSerializer.class)
    private String categoryId;
    //@JsonSerialize(using = ToStringSerializer.class)
    private String tagId;
    private String year;
    private String month;
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
