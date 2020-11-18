package com.edaochina.shopping.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.edaochina.shopping.common.filter.MySimplePropertyPreFilter;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JSON UTIL
 *
 * @author Calm
 * @created 2017年6月17日 上午11:45:06
 * @since v1.0
 */
@UtilityClass
public class JsonUtils {
    /**
     * <p>
     * 将Object转化为json字符串：默认仅使用值非null的字段，可设置序列化指定字段，映射新的字段名
     * </p>
     *
     * @param obj      要序列化的目标对象, Must no be null
     * @param nameMap  要替换的字段名映射Map, Key is old name of property,Value is the new,May
     *                 be null or empty
     * @param clazz    要过滤属性的类,May be null or empty
     * @param includes 要保留的属性,May be null or empty
     * @param excludes 要过滤掉的属性,May be null or empty
     * @return
     * @author Calm
     * @created 2013-8-13 上午10:37:08
     * @since v0.8.1
     */
    public String toJson(Object obj, final Map<String, String> nameMap, Class clazz, String[] includes,
                         String[] excludes) {
        if (obj == null) {
            throw new IllegalArgumentException("Input [obj] is null");
        }

        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);

        // Add nameFilter
        if (MapUtils.isNotEmpty(nameMap)) {

            NameFilter nameFilter = new NameFilter() {
                @Override
                public String process(Object source, String name, Object value) {
                    for (String key : nameMap.keySet()) {
                        if (name.equals(key)) {
                            return nameMap.get(key);
                        }
                    }
                    return name;
                }
            };
            serializer.getNameFilters().add(nameFilter);
        }

        // Add properties
        if (clazz != null) {
            MySimplePropertyPreFilter filter = new MySimplePropertyPreFilter(clazz);
            filter.setIncludes(includes);
            filter.setExcludes(excludes);
            serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
        }

        serializer.write(obj);
        return out.toString();
    }

    /**
     * <p>
     * 将Object转化为json字符串：默认仅使用值非null的字段，可设置序列化指定字段，映射新的字段名
     * </p>
     *
     * @param obj      要序列化的目标对象, Must no be null
     * @param nameMap  要替换的字段名映射Map, Key is old name of property,Value is the
     *                 new,Must no be empty
     * @param clazz    要过滤属性的类,Must not be null
     * @param includes 要保留的属性,Must no be empty
     * @return
     * @author Calm
     * @created 2013-8-14 上午10:42:16
     * @since v0.8.1
     */
    public String toJson(Object obj, final Map<String, String> nameMap, Class clazz, String... includes) {
        if (MapUtils.isEmpty(nameMap) || clazz == null || ArrayUtils.isEmpty(includes)) {
            throw new IllegalArgumentException("Input [nameMap,clazz,includes] is null or empty");
        }
        return toJson(obj, nameMap, clazz, includes, null);
    }

    /**
     * <p>
     * 将Object转化为json字符串：映射新的字段名<br>
     * 1、映射对全部对象都有效<br>
     * 2、适合Object为容器类时使用<br>
     * </p>
     *
     * @param obj     要序列化的目标对象, Must no be null
     * @param nameMap 要替换的字段名映射Map, Key is old name of property,Value is the
     *                new,Must no be empty
     * @return
     * @author Calm
     * @created 2013-8-13 下午4:16:27
     * @since v0.8.1
     */
    public String toJson(Object obj, final Map<String, String> nameMap) {
        if (MapUtils.isEmpty(nameMap)) {
            throw new IllegalArgumentException("Input [nameMap] is empty");
        }
        return toJson(obj, nameMap, null, null, null);
    }

    /**
     * <p>
     * 将Object转化为json字符串：映射新的字段名<br>
     * 1、指定字段仅对Object本身所属的class有效<br>
     * 2、适合Object为非容器类的简单对象时使用<br>
     * </p>
     *
     * @param obj      要序列化的目标对象, Must no be null
     * @param includes 要保留的属性,May be null or empty
     * @return
     * @author Calm
     * @created 2013-8-13 下午4:44:43
     * @since v0.8.1
     */
    public String toJson(Object obj, String... includes) {
        return toJson(obj, obj.getClass(), includes, null);
    }

    /**
     * <p>
     * 将Object转化为json字符串：默认仅使用值非null的字段，可设置仅序列化指定类的特定字段<br>
     * </p>
     *
     * @param obj      要序列化的目标对象, Must no be null
     * @param clazz    要过滤属性的类,May be null or empty
     * @param includes 要保留的属性,May be null or empty
     * @return
     * @author Calm
     * @created 2013-8-13 下午4:58:16
     * @since v0.8.1
     */
    public String toJson(Object obj, Class clazz, String... includes) {
        return toJson(obj, clazz, includes, null);
    }

    /**
     * <p>
     * 将Object转化为json字符串：默认仅使用值非null的字段，可设置仅序列化指定类的特定字段<br>
     * </p>
     *
     * @param obj      要序列化的目标对象, Must no be null
     * @param clazz    要过滤属性的类,May be null or empty
     * @param includes 要保留的属性,May be null or empty
     * @param excludes 要过滤掉的属性,May be null or empty
     * @return
     * @author Calm
     * @since v0.8.1
     */
    public String toJson(Object obj, Class clazz, String[] includes, String[] excludes) {
        if (obj == null) {
            throw new IllegalArgumentException("Input [obj] is null");
        }
        MySimplePropertyPreFilter filter = new MySimplePropertyPreFilter(clazz);
        filter.setIncludes(includes);
        filter.setExcludes(excludes);
        return JSON.toJSONString(obj, filter);
    }

    public String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

    public <T> T toBean(String json, Class<?> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        return (T) JSON.parseObject(json, clazz);
    }

    public <T> T toBean(String json, TypeReference type) {
        if (json == null || json.length() == 0) {
            return null;
        }
        return (T) JSON.parseObject(json, type, Feature.IgnoreNotMatch);
    }

    /**
     * json字符串转化为List.
     *
     * @param json
     * @param clazz
     * @return
     * @author Calm
     * @created 2013年12月25日 下午2:52:21
     * @since v0.8.9
     */
    public <T> List<T> toList(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return Collections.emptyList();
        }
        return JSONArray.parseArray(json, clazz);
    }


}
