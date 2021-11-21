package com.backinfile.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示此字段在使用时会被自动设置
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
public @interface AutoSet {
}
