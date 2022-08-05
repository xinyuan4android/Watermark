package com.pcitc.watermark;

import android.app.Activity;
import android.app.Application;
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

    /**
     * 给单独的view设置水印背景。水印会被view的内容遮挡。
     *
     * @param view
     * @return
     */
    public static WaterMarkDrawable addWaterMark(@NonNull View view) {
        String waterMarkString = "xinyuan.huang\n黄新元\n183111121222";
        WaterMarkDrawable background = new WaterMarkDrawable(waterMarkString);
        view.setBackground(background);
        return background;
    }

    /**
     * 给activity覆盖一个View，给View设置水印背景，达到给整个页面添加水印的效果。
     * 这种方法水印会覆盖在页面的最顶层，水印会遮挡页面内容。
     * 入侵性比较小
     *
     * @param activity
     * @return
     */
    public static WaterMarkDrawable addWaterMark(Activity activity) {
        //找到activity的顶层布局DecorView里面的 R.id.content
        FrameLayout rootLayout = activity.findViewById(android.R.id.content);
        RelativeLayout rlWaterMark = rootLayout.findViewWithTag("rlWaterMark");
        if (rlWaterMark == null) {
            rlWaterMark = new RelativeLayout(activity);
        }
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
