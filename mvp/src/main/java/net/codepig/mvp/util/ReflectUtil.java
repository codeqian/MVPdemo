package net.codepig.mvp.util;

import java.lang.reflect.ParameterizedType;

/**
 * 反射获取指定泛型
 */
public class ReflectUtil {
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType)
                    (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
