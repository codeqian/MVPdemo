package com.shouchuanghui.commonmodule.config;

/**
 * 基础设置
 */
public class BaseConfig {
    public static final String ROOTURL = "http://nwapi.mobilekoudai.com";
    public static final String VERSION_NAME = "1.0.0";
    public static final boolean DEBUG = false;//false:不打印log，true：打印log
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwIuuONPkYanjmTB+Xb6HGjyg/\r" +
            "zxWujT+eqhwX1dboIJrj/A16NrdNuHSwiY2kfmMiKz7qFPL7dW9tAFGGa/LnMzVo\r" +
            "mAqE4qsBgtlpe9K/jZCF3T/3IjOrwHPaDNbvSRhZNk/Eho/T77APLNNqbDOqiXxg\r" +
            "6HK9KWJ+86/iCGcJoQIDAQAB";

    //微信相关，id暂时用别人的，后面改
    public static final String WXAPI_APPID = "wx5ed1b753097f9ac3";
    public static final String WXAPI_APPSECRET = "8c5e660f180a45a511edc1ef3ae86b7b";
    //微信获取access_token api地址
    public static final String WXACCESSTOKEN_API_URL = "https://api.weixin.qq.com";

    public static final int MAX_IMG = 10;
    public static final String APP_IMAGE_PATH = "trading_image";//缓存图片目录
    public static final String APP_TEMP_PATH = "trading_temp";//缓存文件目录
}
