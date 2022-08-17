package zirui.blog.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: FileType
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/27 17:40
 */
public class FileType{
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static ContentType checkType(byte[] b) {
        String photo = bytesToHexString(b);
        photo = photo.toUpperCase();
        ContentType ct = TypeDict.checkType(photo);
        return ct;
    }

    public static ContentType checkType(String name) {
        String suffix = StringUtils.substringAfterLast(name, ".");
        switch (suffix) {
            case "jpg":
            case "jpeg":
                return ContentType.IMAGE_JPEG;
            case "png":
                return ContentType.IMAGE_PNG;
            case "gif":
                return ContentType.IMAGE_GIF;
            default:
                return null;
        }
    }
}
