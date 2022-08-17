package zirui.blog.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zirui.blog.util.ImgtuUtil;
import zirui.blog.vo.ErrorCode;
import zirui.blog.vo.Result;

import java.io.IOException;
import java.util.UUID;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: UploadController
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/27 14:55
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file) throws IOException {
        // 原始文件名称
        String name = file.getOriginalFilename();
        String suffix = StringUtils.substringAfterLast(name, ".");
        // 生成唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + suffix;
        // 上传文件到 位置？ Imgtu
        String s = ImgtuUtil.uploadPic(file.getBytes(), fileName);
        if(StringUtils.isNotBlank(s)) {
            return Result.success(s);
        }
        return Result.fail(ErrorCode.UPLOAD_ERROR);

    }
}
