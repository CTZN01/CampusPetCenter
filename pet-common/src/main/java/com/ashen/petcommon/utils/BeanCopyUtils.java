package com.ashen.petcommon.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    /**
     * 单对象拷贝 (返回新对象)
     *
     */
    public static <V> V copyProperties(Object source, Class<V> clazz) {

        if (source == null) {
            return null;
        }
        try {
            V result = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("BeanCopyUtils copyProperties error", e);
        }
    }

    /**
     * 单对象拷贝 (拷贝到已有目标对象)
     *
     */
    public static void copyProperties(Object dest, Object source) {
        if (dest == null) {
            throw new IllegalArgumentException("BeanCopyUtils copyProperties dest must not be null");
        }
        if (source == null) {
            return;
        }
        BeanUtils.copyProperties(source, dest);
    }

    /**
     * 列表拷贝
     */
    public static <O, V> List<V> copyListProperties(List<O> list, Class<V> clazz) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream()
                .map(o -> copyProperties(o, clazz))
                .collect(Collectors.toList());
    }
}

