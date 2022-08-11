package com.pcitc.watermark;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class WatermarkMode {
    /*--单次--*/
    public static final int SINGLE = 1;
    /*--重复--*/
    public static final int REPEAT = 2;

    @IntDef({SINGLE, REPEAT})
    @Retention(RetentionPolicy.CLASS)
    public @interface WatermarkModes {
    }
}
