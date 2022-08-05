package com.pcitc.watermark;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 水印的位置
 */
public class WatermarkGravity {
    /*--顶部--*/
    public static final int TOP = 1;
    /*--左边--*/
    public static final int LEFT = 2;
    /*--右边--*/
    public static final int RIGHT = 3;
    /*--底部--*/
    public static final int BOTTOM = 4;
    /*--中心--*/
    public static final int CENTER = 5;

    @IntDef({TOP, LEFT, RIGHT, BOTTOM, CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WatermarkGravities {
    }

}
