package com.example.frescotest;

import java.io.File;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.neteaseyx.common.util.FileUtil;

import android.content.Context;

/**
 * 图片Fresco配置
 * @author Byron(hzchenlk&corp.netease.com) on 15-7-8.
 */
public class FrescoConfig {
    /* 缓存相关配置 */
    private static String IMAGE_CACHE = "image";
    private static String SMALL_IAMGE_CACHE = "small";
    private static long MAX_CACHE_SIZE = 300 * 1024 * 1024;
    private static long MAX_CACHE_SIZE_LOW = 100 * 1024 * 1024;
    private static long MAX_CACHE_SIZE_VERY_LOW = 50 * 1024 * 1024;

    public static ImagePipelineConfig getConfig(Context context){
        DiskCacheConfig diskConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(getImageCachePath(context))
                .setBaseDirectoryName(IMAGE_CACHE)
                .setMaxCacheSize(MAX_CACHE_SIZE)
                .setMaxCacheSizeOnLowDiskSpace(MAX_CACHE_SIZE_LOW)
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_CACHE_SIZE_VERY_LOW)
                .build();

        DiskCacheConfig smallDiskConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(getImageCachePath(context))
                .setBaseDirectoryName(SMALL_IAMGE_CACHE)
                .setMaxCacheSize(MAX_CACHE_SIZE / 2)
                .setMaxCacheSizeOnLowDiskSpace(MAX_CACHE_SIZE_LOW / 2)
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_CACHE_SIZE_VERY_LOW / 2)
                .build();

        return ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(diskConfig)
                .setSmallImageDiskCacheConfig(smallDiskConfig)
                .setDownsampleEnabled(true)
                .build();
    }

    private static File getImageCachePath(Context context){
        return new File(FileUtil.getSysDiskCachePath(context));
    }

    public static void clearDisCache(Context context){
        File cacheFile = new File(FileUtil.getSysDiskCachePath(context) + File.separator + IMAGE_CACHE);
        deleteFile(cacheFile);
    }

    private static void deleteFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files == null || files.length == 0){
                file.delete();
            }
            else{
                for(int i = 0; i < files.length; i++){
                    deleteFile(files[i]);
                }
            }
        }
        else{
            file.delete();
        }
    }
}
