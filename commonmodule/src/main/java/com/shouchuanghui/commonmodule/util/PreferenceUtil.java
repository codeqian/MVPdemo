package com.shouchuanghui.commonmodule.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.shouchuanghui.commonmodule.config.PreferenceConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * SharedPreferences工具类
 * 保存首选项信息
 */
public class PreferenceUtil {
    private static SharedPreferences preferences = null;


    private PreferenceUtil() {
        //
    }

    /**
     * @param context
     * @param isface  强制初始化
     */
    public static void initialize(Context context, boolean isface) {
        if (preferences == null || isface) {
            preferences = context.getSharedPreferences(PreferenceConfig.SHARED_INFO, Context.MODE_PRIVATE);
        }
    }

    public static void initialize(Context context) {
        initialize(context, false);
    }

    public static SharedPreferences getPreference() {
        return preferences;
    }

    /**
     * 保存Sring型数据
     *
     * @param key   键
     * @param value 数值
     */
    public static void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存int型数据
     *
     * @param key   键
     * @param value 数值
     */
    public static void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存int型数据
     *
     * @param key   键
     * @param value 数值
     */
    public static void setLongValue(String key, Long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 保存Boolean型数据
     *
     * @param key   键
     * @param value 数值
     */
    public static void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取String类型数据
     *
     * @param key          键
     * @param defaultValue 数值
     * @return String类型数据
     */
    public static String getString(String key, String defaultValue) {
        if (preferences == null) {
            return "";
        }
        return preferences.getString(key, defaultValue);
    }

    /**
     * 获取Int类型数据
     *
     * @param key          键
     * @param defaultValue
     * @return Int类型数据
     */
    public static int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public static long getLong(String key, Long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    /**
     * 获取Boolean类型数据
     *
     * @param key          键
     * @param defaultValue
     * @return Boolean类型数据
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * 除清首选项数据
     *
     * @param context 界面上下文
     */
    public static void clearData(Context context) {
        preferences.edit().clear().commit();
    }

    /**
     * 是否存在key键值
     *
     * @param key 键
     * @return 是否存在
     */
    public static boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * 从Preferences获取存储对象
     *
     * @param key 键值
     * @param <T>
     * @return
     */
    public static <T> T getSerializables(String key) {
        String temp = preferences.getString(key, "");
        if (StringUtil.isEmpty(temp)) {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        T t = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            t = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 删除数据
     * @param key
     */
    public static  void remove(final String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
