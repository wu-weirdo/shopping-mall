package com.edaochina.shopping.common.utils;

import com.edaochina.shopping.common.exception.YidaoException;
import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * AssertUtils
 *
 * @author wangpenglei
 * @since 2019/11/7 10:28
 */
@UtilityClass
public class AssertUtils {

    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        try {
            Preconditions.checkArgument(expression, errorMessage);
        } catch (IllegalArgumentException e) {
            throw new YidaoException(e.getMessage());
        }
    }

    public static <T> void checkNotNull(T reference, @Nullable Object errorMessage) {
        try {
            Preconditions.checkNotNull(reference, errorMessage);
        } catch (NullPointerException e) {
            throw new YidaoException(e.getMessage());
        }
    }

}
