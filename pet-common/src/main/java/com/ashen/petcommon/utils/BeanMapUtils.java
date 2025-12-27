package com.ashen.petcommon.utils;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanMapUtils {
    private BeanMapUtils(){
    }


    /**
     * 集合转 Map，value 为元素本身；键冲突时保留后者。
     */
    public static <K, V> Map<K, V> listToMap(Collection<V> source,
                                             Function<? super V, ? extends K> keyMapper) {
        if (source == null || keyMapper == null) {
            return Collections.emptyMap();
        }
        return source.stream()
                .filter(Objects::nonNull)
                .filter(v -> keyMapper.apply(v) != null)
                .collect(Collectors.toMap(
                        keyMapper,
                        Function.identity(),
                        (oldVal, newVal) -> newVal,
                        LinkedHashMap::new
                ));
    }

    /**
     * 集合转 Map，value 为同键的元素列表。
     */
    public static <K, V> Map<K, List<V>> listToMapList(Collection<V> source,
                                                       Function<? super V, ? extends K> keyMapper) {
        if (source == null || keyMapper == null) {
            return Collections.emptyMap();
        }
        return source.stream()
                .filter(Objects::nonNull)
                .filter(v -> keyMapper.apply(v) != null)
                .collect(Collectors.groupingBy(
                        keyMapper,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    /**
     * 集合转 Map，value 为同键的“字段”列表（通过 valueMapper 指定）。
     */
    public static <K, V, T> Map<K, List<T>> listToMapValueList(Collection<V> source,
                                                               Function<? super V, ? extends K> keyMapper,
                                                               Function<? super V, ? extends T> valueMapper) {
        if (source == null || keyMapper == null || valueMapper == null) {
            return Collections.emptyMap();
        }
        return source.stream()
                .filter(Objects::nonNull)
                .filter(v -> keyMapper.apply(v) != null)
                .collect(Collectors.groupingBy(
                        keyMapper,
                        LinkedHashMap::new,
                        Collectors.mapping(valueMapper, Collectors.toList())
                ));
    }

}
