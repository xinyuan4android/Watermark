package com.pcitc.watermark;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class WaterMarkDrawable extends Drawable {
    /**
     * 文字的paint
     */
    private Paint mTextPaint;
    /**
     * 整个水印的背景色
     */
    private @ColorInt
    int bgColor;
    /**
     * 水印文字的颜色
     */
    private @ColorInt
    int textColor;
    /**
     * 水印的文字
     */
    private String waterMarkText;
    /**
     * 整个水印的默认背景色
     */
    private static final @ColorInt
    int bgDefaultColor = Color.parseColor("#00F5F5F5");
    /**
     * 水印文字的默认颜色
     */
    private static final @ColorInt
    int textDefaultColor = Color.parseColor("#80D7D7D7");


    public WaterMarkDrawable() {
        this("", bgDefaultColor, textDefaultColor);
    }

    public WaterMarkDrawable(String waterMarkText) {
        this(waterMarkText, bgDefaultColor, textDefaultColor);
    }

    public WaterMarkDrawable(String waterMarkText, int bgColor, int textColor) {
        this.waterMarkText = waterMarkText;
        this.bgColor = bgColor;
        this.textColor = textColor;
        init();
    }


    public void setTextColor(String textColorString) {
        setTextColor(Color.parseColor(textColorString));
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;

        mTextPaint.setColor(textColor);
        invalidateSelf();
    }

    public void setBgColor(String bgColorString) {
        setBgColor(Color.parseColor(bgColorString));
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidateSelf();
    }

    public void setWaterMarkText(String waterMarkText) {
        this.waterMarkText = waterMarkText;
        //View的setBackground()方法中，给backgroundDrawable设置了 setCallback
        //所以invalidateSelf()方法会通知View去刷新重绘
        invalidateSelf();
    }

    /**
     * 水印文字大小 默认15，单位是sp
     */
    private static final float TEXT_DEFAULT_SIZE = 15.0F;

    /**
     * 设置水印文字大小
     *
     * @param textSize 单位sp
     */
    public void setTextSize(float textSize) {
        setTextSize(textSize, TypedValue.COMPLEX_UNIT_SP);
    }

    /**
     * 设置水印文字大小，可以自定义单位
     *
     * @param textSize 大小值
     * @param unit     单位
     */
    public void setTextSize(float textSize, int unit) {
        float sizePx = WaterMarkUtils.convertPxByUnitValue(textSize, unit);
        if (sizePx != mTextPaint.getTextSize()) {
            mTextPaint.setTextSize(sizePx);
            invalidateSelf();
        }
    }

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        //抗锯齿
        mTextPaint.setAntiAlias(true);
//        mPaint.setTextSize(UIUtils.getDimens(R.dimen.text_size_15sp));
        //设置文字大小
        setTextSize(TEXT_DEFAULT_SIZE);
        //这里如果不设置alpha，那么alpha就会自己获取mPaint.setColor()方法中的透明度。
        //但是如果在setColor()方法之后，又设置了alpha，那么setColor()方法设置的color的透明度，也会因此而改变。
//        mTextPaint.setAlpha(255);
        degrees = DEFAULT_DEGREES;
        waterMarkHorizontalSpacing = WATER_MARK_HORIZONTAL_DEFAULT_SPACING;
        waterMarkVerticalSpacing = WATER_MARK_VERTICAL_DEFAULT_SPACING;
        offSpace = OFF_DEFAULT_SPACE;
    }


    /**
     * 水印旋转角度
     */
    private float degrees = 0;
    /**
     * 默认旋转角度
     */
    private static final float DEFAULT_DEGREES = -50.0F;

    public void setDegrees(float degrees) {
//        if (degrees == 0.0F) {
//            return;
//        }
        this.degrees = degrees;
        invalidateSelf();
    }

    /**
     * 水印的水平间距。单位px
     */
    private float waterMarkHorizontalSpacing = 0.0F;
    private static final float WATER_MARK_HORIZONTAL_DEFAULT_SPACING = 100.0F;

    public void setWaterMarkVerticalSpacing(float waterMarkVerticalSpacing) {
        setWaterMarkVerticalSpacing(waterMarkVerticalSpacing, TypedValue.COMPLEX_UNIT_PX);
    }

    public void setWaterMarkVerticalSpacing(float waterMarkVerticalSpacing, int unit) {
        waterMarkVerticalSpacing = WaterMarkUtils.convertPxByUnitValue(waterMarkVerticalSpacing, unit);
        if (this.waterMarkVerticalSpacing == waterMarkVerticalSpacing) {
            return;
        }
        this.waterMarkVerticalSpacing = waterMarkVerticalSpacing;
        invalidateSelf();
    }

    /**
     * 水印的垂直间距。单位px
     */
    private float waterMarkVerticalSpacing = 0.0F;
    private static final float WATER_MARK_VERTICAL_DEFAULT_SPACING = 100.0F;

    public void setWaterMarkHorizontalSpacing(float waterMarkHorizontalSpacing) {
        setWaterMarkHorizontalSpacing(waterMarkHorizontalSpacing, TypedValue.COMPLEX_UNIT_PX);
    }

    public void setWaterMarkHorizontalSpacing(float waterMarkHorizontalSpacing, int unit) {
        waterMarkHorizontalSpacing = WaterMarkUtils.convertPxByUnitValue(waterMarkHorizontalSpacing, unit);
        if (this.waterMarkHorizontalSpacing == waterMarkHorizontalSpacing) {
            return;
        }
        this.waterMarkHorizontalSpacing = waterMarkHorizontalSpacing;
        invalidateSelf();
    }

    /**
     * 奇数行和偶数行之间的偏离间距，单位px。
     * 比如下面的例子，---表示偏离距离
     * 1111111
     * ---1111111
     * 1111111
     * ---1111111
     */
    private float offSpace;
    private static final float OFF_DEFAULT_SPACE = 50.0F;

    public void setOffSpace(float offSpace) {
        setOffSpace(offSpace, TypedValue.COMPLEX_UNIT_PX);
    }

    public void setOffSpace(float offSpace, int unit) {
        offSpace = WaterMarkUtils.convertPxByUnitValue(offSpace, unit);
        if (this.offSpace == offSpace) {
            return;
        }
        this.offSpace = offSpace;
        invalidateSelf();
    }

    private
    @WatermarkGravity.WatermarkGravities
    int mWatermarkGravity = WatermarkGravity.CENTER;
    private
    @WatermarkMode.WatermarkModes
    int mWatermarkMode = WatermarkMode.REPEAT;

    public void setWatermarkGravity(@WatermarkGravity.WatermarkGravities int watermarkGravity) {
        this.mWatermarkGravity = watermarkGravity;
        invalidateSelf();
    }

    public void setWatermarkMode(@WatermarkMode.WatermarkModes int watermarkMode) {
        if (mWatermarkMode == watermarkMode) {
            return;
        }
        mWatermarkMode = watermarkMode;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawColor(bgColor);
        //保存画布当前状态
        canvas.save();
        //旋转画布
        canvas.rotate(degrees);
        switch (mWatermarkMode) {
            case WatermarkMode.REPEAT:
                drawRepeat(canvas);
                break;
            case WatermarkMode.SINGLE:
                drawSingle(canvas);
                break;
        }
    }

    private void drawSingle(Canvas canvas) {
        int width = getBounds().right;
        int height = getBounds().bottom;
        String[] strings = waterMarkText.split("\n");
        //每一行文字的最大宽度
        float textWidthMax = 0.0F;
        for (String s : strings) {
            float textWidth = mTextPaint.measureText(s);
            textWidthMax = Math.max(textWidthMax, textWidth);
        }
        Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
        float textHeightPerLine = fm.bottom - fm.top;

        // 开始绘画的X轴坐标
        float positionX = 0.0F;
        // 开始会话的Y轴坐标
        float positionY = 0.0F;
        // 处理Y轴坐标
        if ((mWatermarkGravity & WatermarkGravity.TOP) == WatermarkGravity.TOP) {
            // top
            // Y轴方向从单行文字的高度开始往下画，保证第一行文字不会绘制到屏幕外
            positionY = textHeightPerLine;
        }
        if ((mWatermarkGravity & WatermarkGravity.BOTTOM) == WatermarkGravity.BOTTOM) {
            // bottom
            // Y轴方向从高度的最底部减去单行文字的高度乘以行数减一开始往下画，保证最后一行文字不会绘制到屏幕外
            positionY = height - textHeightPerLine * (strings.length - 1);
        }
        if ((mWatermarkGravity & WatermarkGravity.CENTER_VERTICAL) == WatermarkGravity.CENTER_VERTICAL) {
            // 垂直居中
            // Y轴方向从高度的中间位置 加上单行文字的一半
            positionY = height / 2.0F + fm.bottom;
        }
        // 处理X轴坐标
        if ((mWatermarkGravity & WatermarkGravity.LEFT) == WatermarkGravity.LEFT) {
            // left
            // X轴方向从0的位置画
            positionY = 0;
        }
        if ((mWatermarkGravity & WatermarkGravity.RIGHT) == WatermarkGravity.RIGHT) {
            // right
            // X轴方向从宽度的最大值减去单行文字宽度的最大值
            positionX = width - textWidthMax;
        }
        if ((mWatermarkGravity & WatermarkGravity.CENTER_HORIZONTAL) == WatermarkGravity.CENTER_HORIZONTAL) {
            // 水平居中
            // X轴方向从宽度的一半 减去 单行宽度的最大值的一半
            positionX = width / 2.0F - textWidthMax / 2;
        }

        //开始画文字，考虑到换行的情况。
        //每一次换行时，垂直方向的高度都要加一次 每一行文字的高度。
        for (int j = 0; j < strings.length; j++) {
            String s = strings[j];
            //改变水平坐标X，就可以改变多行水印的对齐方式。待完成
            canvas.drawText(s, positionX, positionY + textHeightPerLine * j, mTextPaint);
        }
    }

    private void drawRepeat(Canvas canvas) {
        int width = getBounds().right;
        int height = getBounds().bottom;
        String[] strings = waterMarkText.split("\n");
        //每一行文字的最大宽度
        float textWidthMax = 0.0F;
        for (String s : strings) {
            float textWidth = mTextPaint.measureText(s);
            textWidthMax = Math.max(textWidthMax, textWidth);
        }
        Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
        int index = 0;
//        Bitmap bitmap = WaterMarkUtils.bitmapDecodeResource(R.drawable.ic_launcher);
//        int waterMarkHeightLeading = height / 10;
        //从垂直方向遍历。从屏幕的顶部到底部。从waterMarkHeightLeading 到屏幕的高度。
        for (float positionY = -height; positionY <= height * 2; positionY += waterMarkVerticalSpacing) {
            //水平方向遍历。从屏幕左边到右边。从-width开始画，就是从屏幕左边的外边开始画。
            //之所以从屏幕外开始画，因为，我们一般都是左低右高便宜角，多画一些会保证屏幕左下角不会出现空白的情况。
            float fromX = -width + (index++ % 2) * offSpace;
            //每一行文字的高度
            float textHeightPerLine = fm.bottom - fm.top;
            for (float positionX = fromX; positionX < width * 2; positionX += (textWidthMax + waterMarkHorizontalSpacing)) {
                //开始画文字，考虑到换行的情况。
                //每一次换行时，垂直方向的高度都要加一次 每一行文字的高度。
                for (int j = 0; j < strings.length; j++) {
                    String s = strings[j];
                    //改变水平坐标X，就可以改变多行水印的对齐方式。待完成
                    canvas.drawText(s, positionX, positionY + textHeightPerLine * j, mTextPaint);

                }
            }
            positionY += textHeightPerLine * strings.length;
        }
    }

//                    RectF rectF = new RectF(positionX, positionY + textHeightPerLine * j,
//                            positionX + textHeightPerLine, positionY + textHeightPerLine * (j + 1));
////                    canvas.drawRect(rectF, mPaint);
//                    canvas.drawBitmap(bitmap, null, rectF, mTextPaint);

//    //保存画布当前状态
//        canvas.save();
//    //旋转画布
//        canvas.rotate(-degrees);
//        canvas.drawBitmap(bitmap, null, new RectF(0, 0, 100, 100), mTextPaint);

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
