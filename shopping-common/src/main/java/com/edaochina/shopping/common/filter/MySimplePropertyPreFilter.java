package com.edaochina.shopping.common.filter;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 对fastjson-1.1.33中默认的SimplePropertyPreFilter进行改造，增加了excludes字段的设置方法.
 *
 * @author Calm
 * @created 2016年12月28日 上午9:42:02
 * @since v1.0
 */
public class MySimplePropertyPreFilter implements PropertyPreFilter {

    private final Class<?> clazz;
    private Set<String> includes = new HashSet<>();
    private Set<String> excludes = new HashSet<>();

    public MySimplePropertyPreFilter(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    public MySimplePropertyPreFilter(String... properties) {
        this(null, properties);
    }

    public MySimplePropertyPreFilter(Class<?> clazz, String... properties) {
        super();
        this.clazz = clazz;
        for (String item : properties) {
            if (item != null) {
                this.includes.add(item);
            }
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Set<String> getIncludes() {
        return includes;
    }

    public void setIncludes(String[] properties) {
        if (ArrayUtils.isEmpty(properties)) {
            return;
        }
        for (String item : properties) {
            if (StringUtils.isNotBlank(item)) {
                this.includes.add(item);
            }
        }
    }

    public Set<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] properties) {
        if (ArrayUtils.isEmpty(properties)) {
            return;
        }
        for (String item : properties) {
            if (StringUtils.isNotBlank(item)) {
                this.excludes.add(item);
            }
        }
    }

    @Override
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (source == null) {
            return true;
        }

        if (clazz != null && !clazz.isInstance(source)) {
            return true;
        }

        if (this.excludes.contains(name)) {
            return false;
        }

        return includes.isEmpty() || includes.contains(name);
    }
}
