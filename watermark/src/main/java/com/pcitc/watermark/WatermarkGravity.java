package com.pcitc.watermark;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 水印的位置
 * 参考{@link android.view.Gravity}
 */
public class WatermarkGravity {
    public static final int AXIS_SPECIFIED = 0x0001;   //二进制 0001

    public static final int AXIS_PULL_BEFORE = 0x0002; //二进制 0010
    public static final int AXIS_PULL_AFTER = 0x0004;  //二进制 0100

    public static final int AXIS_X_SHIFT = 0;
    public static final int AXIS_Y_SHIFT = 4;

    /*--顶部 二进制表示 0011 0000--*/
    public static final int TOP = (AXIS_PULL_BEFORE | AXIS_SPECIFIED) << AXIS_Y_SHIFT;
    /*--左边 二进制表示 0000 0011--*/
    public static final int LEFT = (AXIS_PULL_BEFORE | AXIS_SPECIFIED) << AXIS_X_SHIFT;
    /*--右边 二进制表示 0000 0101--*/
    public static final int RIGHT = (AXIS_PULL_AFTER | AXIS_SPECIFIED) << AXIS_X_SHIFT;
    /*--底部 二进制表示 0101 0000--*/
    public static final int BOTTOM = (AXIS_PULL_AFTER | AXIS_SPECIFIED) << AXIS_Y_SHIFT;

    /*--垂直居中 二进制表示 0001 0000--*/
    public static final int CENTER_VERTICAL = AXIS_SPECIFIED << AXIS_Y_SHIFT;
    /*--水平居中 二进制表示 0000 0001--*/
    public static final int CENTER_HORIZONTAL = AXIS_SPECIFIED << AXIS_X_SHIFT;
    /*--中心居中 二进制表示 0001 0001--*/
    public static final int CENTER = CENTER_VERTICAL | CENTER_HORIZONTAL;

    @IntDef({TOP, LEFT, RIGHT, BOTTOM, CENTER, CENTER_VERTICAL, CENTER_HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WatermarkGravities {
    }

}
