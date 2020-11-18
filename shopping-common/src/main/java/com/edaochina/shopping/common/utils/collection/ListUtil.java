package com.edaochina.shopping.common.utils.collection;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class ListUtil {

    /**
     * List 去重
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(j).equals(list.get(i))) {
                        list.remove(j);
                    }
                }
            }
        }
        return list;
    }

}
