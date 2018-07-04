package com.txp.fs.common.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
	
import com.google.common.collect.Lists;
import com.google.common.base.Function;

public class CollectionUtil {

    public static <T> Iterable<T> safe(Iterable<T> iterable) {
        return iterable == null ? Collections.<T> emptyList() : iterable;
    }
	
    public static <T> List<T> safe(List<T> list) {
        return list == null ? Collections.<T> emptyList() : list;
    }

    public static <T> Set<T> safe(Set<T> set) {
        return set == null ? Collections.<T> emptySet() : set;
    }

    public static <K, V> Map<K, V> safe(Map<K, V> map) {
        return map == null ? Collections.<K, V> emptyMap() : map;
    }
    
    public static <T> List<T> transform(List<T> list) {
        return safe(list);
    }
	
    public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
        return Lists.newArrayList(Lists.transform(safe(fromList), function));
    }
    
    public static <T> boolean isEmtpy(List<T> list) {
        return safe(list).isEmpty();
    }

    public static <T> boolean isNotEmtpy(List<T> list) {
        return !isEmtpy(list);
    }

}
