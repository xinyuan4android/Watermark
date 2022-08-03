package com.pcitc.watermark;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public class WaterMarkHelper {
    private static Application application;

    public static void init(Application application) {
        WaterMarkHelper.application = application;
        WaterMarkUtils.init(application);
    }

    public static WaterMarkDrawable addWaterMark(@NonNull View view) {
        String waterMarkString = "xinyuan.huang\n黄新元\n183111121222";
        WaterMarkDrawable background = new WaterMarkDrawable(waterMarkString);
        view.setBackground(background);
        return background;
    }

    public static WaterMarkDrawable addWaterMark(Activity activity) {
        FrameLayout rootLayout = activity.findViewById(android.R.id.content);
//        rootLayout.setBackground(new WaterMarkDrawable("xinyuan.huang"));

        RelativeLayout rlWaterMark = rootLayout.findViewWithTag("rlWaterMark");
        if (rlWaterMark == null) {
            rlWaterMark = new RelativeLayout(activity);
        }
//        rlWaterMark.setId(R.id.rlWaterMark);
        String waterMarkString = "xinyuan.huang\n黄新元\n183111121222";
        WaterMarkDrawable background = new WaterMarkDrawable(waterMarkString);
        rlWaterMark.setBackground(background);
        rlWaterMark.setTag("rlWaterMark");
        if (rlWaterMark.getParent() != null) {
            ((ViewGroup) rlWaterMark.getParent()).removeView(rlWaterMark);
        }
        rootLayout.addView(rlWaterMark);
        return background;
    }
}
