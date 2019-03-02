package com.example.msi.week3demo.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * <p>文件描述：<p>
 * <p>作者：${Ln}<p>
 * <p>创建时间：2019/3/2  8:50<p>
 * <p>更改时间：2019/3/2  8:50<p>
 * <p>版本号：1<p>
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
