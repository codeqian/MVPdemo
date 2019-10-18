package com.shouchuanghui.commonmodule.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.shouchuanghui.commonmodule.config.BaseConfig;

import java.io.File;

/**
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * get file uri
     *
     * @param picPath file path
     * @return Uri
     */
    public static Uri getFileUri(String picPath) {
        Uri uri = Uri.fromFile(new File(picPath));
        return uri;
    }

    public static void recursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] childFile = file.listFiles();

            if (childFile == null || childFile.length == 0) {
                file.delete();
            } else {
                for (File f : childFile) {
                    recursionDeleteFile(f);
                }
//                file.delete();
            }
        }
    }

    public static String getBaseImageDir() {
        String path = Environment.getExternalStorageDirectory() + File.separator + BaseConfig.APP_IMAGE_PATH + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    /**
     * 获取缓存文件地址
     * @return
     */
    public static String getTempDir() {
        String path = Environment.getExternalStorageDirectory() + File.separator + BaseConfig.APP_TEMP_PATH + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }
}
