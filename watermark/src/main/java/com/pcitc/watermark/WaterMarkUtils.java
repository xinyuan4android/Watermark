package com.pcitc.watermark;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;


public class WaterMarkUtils {
    private static Application application;

    public static void init(Application application) {
        WaterMarkUtils.application = application;
    }

    /**
     * 从资源文件获取bitmap
     *
     * @param drawableId 资源文件id
     * @return bitmap
     */
    public static Bitmap bitmapDecodeResource(@DrawableRes int drawableId) {
        return BitmapFactory.decodeResource(application.getResources(), drawableId);
    }

    public static Resources getResources() {
        if (application == null) {
            throw new RuntimeException("application 字段为空，是不是没有调用init(Application) 初始化方法");
        }
        return application.getResources();
    }

    /**
     * 根据单位值 转换为像素值
     *
     * @param value 单位值
     * @param unit  单位 {@link TypedValue#COMPLEX_UNIT_SP}等
     * @return 像素值
     */
    public static float convertPxByUnitValue(float value, int unit) {
        return TypedValue.applyDimension(unit, value, WaterMarkUtils.getResources().getDisplayMetrics());
    }

}
