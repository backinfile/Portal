package com.backinfile.support;

import com.backinfile.support.func.Function1;
import com.backinfile.support.func.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StreamUtils {
    public static <T, R> List<R> map(Collection<T> collection, Function1<R, T> function) {
        List<R> result = new ArrayList<>(collection.size());
        for (T t : collection) {
            result.add(function.invoke(t));
        }
        return result;
    }

    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : collection) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T> boolean any(Collection<T> collection, Predicate<T> predicate) {
        for (T t : collection) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean all(Collection<T> collection, Predicate<T> predicate) {
        for (T t : collection) {
            if (!predicate.test(t)) {
                return false;
            }
        }
        return true;
    }

    public static <T> T get(Collection<T> collection, int index) {
        if (collection instanceof List) {
            return ((List<T>) collection).get(index);
        }
        Iterator<T> iterator = collection.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }
}
