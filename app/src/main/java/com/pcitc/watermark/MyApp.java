package com.pcitc.watermark;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WaterMarkHelper.init(this);
    }
}
