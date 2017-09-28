package com.zhongdi.miluo.constants;

/**
 * Created by kenn on 2017/9/18.
 */

public class MiluoConfig {

    public static final String CACHE_SP_NAME = "sp_cache";//默认SharedPreferences缓存文件名
    public static final String CACHE_DISK_DIR = "disk_cache";//默认磁盘缓存目录
    public static final String CACHE_HTTP_DIR = "http_cache";//默认HTTP缓存目录
    public static final long CACHE_NEVER_EXPIRE = -1;//永久不过期

    public static final int MAX_AGE_ONLINE = 60;//默认最大在线缓存时间（秒）
    public static final int MAX_AGE_OFFLINE = 24 * 60 * 60;//默认最大离线缓存时间（秒）


    public static final String COOKIE_PREFS = "Cookies_Prefs";//默认Cookie缓存目录

    public static final int DEFAULT_TIMEOUT = 60;//默认超时时间（秒）
    public static final int DEFAULT_MAX_IDLE_CONNECTIONS = 5;//默认空闲连接数
    public static final long DEFAULT_KEEP_ALIVE_DURATION = 8;//默认心跳间隔时长（秒）
    public static final long CACHE_MAX_SIZE = 10 * 1024 * 1024;//默认最大缓存大小（字节）

    public static final int DEFAULT_RETRY_COUNT = 3;//默认重试次数
    public static final int DEFAULT_RETRY_DELAY_MILLIS = 3000;//默认重试间隔时间（毫秒）

    public static final String DEFAULT_DOWNLOAD_DIR = "download";//默认下载目录
    public static final String DEFAULT_DOWNLOAD_FILE_NAME = "download_file.tmp";//默认下载文件名称

    public static final int DEFAULT_PAGESIZE = 15;//默认分页大小
    public static final int UN_OPEN_ACCOUNT = 0;//未开户

    public static final int BAOSHOU = 1; //保守型
    public static final int WENJIAN = 2;//稳健型
    public static final int CHENGSHU = 3;//成熟型
    public static final int JINQU = 4;//进取型
    public static final int JIJIN = 5;//激进型

}
