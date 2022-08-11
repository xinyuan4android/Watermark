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

    /**
     * 给单独的view设置水印背景。水印会被view的内容遮挡。
     *
     * @param view 要添加水印的view
     * @return 添加到view的水印背景drawable，方便后续对水印设置其他属性。
     */
    public static WaterMarkDrawable addWaterMark(@NonNull View view) {
        WaterMarkDrawable background = new WaterMarkDrawable();
        addWaterMark(view, background);
        return background;
    }

    public static void addWaterMark(@NonNull View view, Drawable background) {
        view.setBackground(background);
    }

    /**
     * 移除view的水印背景
     *
     * @param view 要移除水印的view
     */
    public static void removeWaterMark(@NonNull View view) {
        Drawable background = view.getBackground();
        if (background instanceof WaterMarkDrawable) {
            view.setBackground(null);
        }
    }

    /**
     * 给activity覆盖一个View，给View设置水印背景，达到给整个页面添加水印的效果。
     * 这种方法水印会覆盖在页面的最顶层，水印会遮挡页面内容。
     * 入侵性比较小
     *
     * @param activity 要添加水印的activity
     * @return 添加到activity的水印背景drawable，方便后续对水印设置其他属性。
     */
    public static WaterMarkDrawable addWaterMark(Activity activity) {
        WaterMarkDrawable background = new WaterMarkDrawable();
        addWaterMark(activity, background);
        return background;
    }

    public static void addWaterMark(Activity activity, Drawable background) {
        //找到activity的顶层布局DecorView里面的 R.id.content
        FrameLayout rootLayout = activity.findViewById(android.R.id.content);
        RelativeLayout rlWaterMark = rootLayout.findViewWithTag("rlWaterMark");
        if (rlWaterMark == null) {
            rlWaterMark = new RelativeLayout(activity);
        }
        rlWaterMark.setBackground(background);
        rlWaterMark.setTag("rlWaterMark");
        //先remove，再重新add，是为了重新绘制background。
        if (rlWaterMark.getParent() != null) {
            ((ViewGroup) rlWaterMark.getParent()).removeView(rlWaterMark);
        }
        rootLayout.addView(rlWaterMark);
    }

    /**
     * 移除activity上的水印
     *
     * @param activity 要移除水印的activity
     */
    public static void removeWaterMark(Activity activity) {
        if (activity == null) {
            return;
        }
        FrameLayout rootLayout = activity.findViewById(android.R.id.content);
        RelativeLayout rlWaterMark = rootLayout.findViewWithTag("rlWaterMark");
        if (rlWaterMark != null) {
            rlWaterMark.setBackground(null);
        }
    }
}
