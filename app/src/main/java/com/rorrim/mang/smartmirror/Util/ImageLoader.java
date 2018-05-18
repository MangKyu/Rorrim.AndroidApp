package com.rorrim.mang.smartmirror.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.request.RequestOptions;

import static android.content.Context.ACTIVITY_SERVICE;


public class ImageLoader {
    private static ImageLoader instance;

    public ImageLoader(Context context){
        buildGlide(context);
    }

    public static ImageLoader getInstance(Context context){
        if(instance == null){
            instance = new ImageLoader(context);
        }
        return instance;
    }

    private void buildGlide(Context context) {
        GlideBuilder gb = new GlideBuilder();

        //set mem cache size to 8% of available memory
        LruResourceCache lruMemCache = new LruResourceCache(getMemCacheSize(context, 8));
        gb.setMemoryCache(lruMemCache);

        //set disk cache 300 mb
        InternalCacheDiskCacheFactory diskCacheFactory =
                new InternalCacheDiskCacheFactory(context, 300);
        gb.setDiskCache(diskCacheFactory);
        //set BitmapPool with 1/10th of memory cache's size
        LruBitmapPool bitmapPool = new LruBitmapPool(getMemCacheSize(context, 8)/10);
        gb.setBitmapPool(bitmapPool);

        //set custom Glide as global singleton
        Glide.init(context, gb);
    }

    private int getMemCacheSize(Context context, int percent){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();

        //((ActivityManager)context.getSystemService(ACTIVITY_SERVICE)).getMemoryInfo(mi);
        ((ActivityManager)context.getSystemService(ACTIVITY_SERVICE)).getMemoryInfo(mi);

        double availableMemory= mi.availMem;
        return (int)(percent*availableMemory/100);
    }

    public void setGlideImg(Activity activity, String url, ImageView imageView){
        Glide.with(activity)
                .load(url)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false)
                )
                .into((ImageView) imageView);
    }
}

