package zirui.blog.util;

import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import zirui.blog.vo.params.LoginParams;

import java.util.*;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: CopyUtil
 * @Description: TODO
 * @version: v1.8.0
 * @author: ZIRUI QIAO
 * @date: 2022/7/25 12:21
 */
public class CopyUtil {

    public static <T, V> T copyBean(V obj1, Class<T> tClass) {
        T t = null;
        try {
            t = tClass.newInstance();
            BeanUtils.copyProperties(obj1, t, getNullPropertyNames(obj1));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("copyBean 工具方法失败： " + tClass);
        }
        return t;
    }

    public static <T, V> List<T> copyList(List<V> list1, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        for (V v : list1) {
            list.add(copyBean(v, tClass));
        }

        return list;
    }

    /**
     * Returns an array of null properties of an object
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }
}
