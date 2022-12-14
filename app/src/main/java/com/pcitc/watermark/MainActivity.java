package com.pcitc.watermark;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1.为什么选择自定义drawable而不是自定义view（分析drawable，view，bitmap区别）
 * 2.自定义drawable 实现的三个方法分析
 * 3.画text的时候，要注意baseline。分析Paint.FontMetricsInt
 * 4.drawable调用invalidateSelf()会重新绘制，调用自己的onDraw()
 * 5.android的@interface 注解，代替枚举。
 */
public class MainActivity extends AppCompatActivity {
    private LinearLayout llBg;
    private SeekBar seekBarDegrees;
    private SeekBar seekBarTextSize;
    private SeekBar seekBarHorizontalSpace;
    private SeekBar seekBarVerticalSpace;
    private SeekBar seekBarOffSpace;
    private SeekBar seekBarColorA;
    private SeekBar seekBarColorR;
    private SeekBar seekBarColorG;
    private SeekBar seekBarColorB;
    private WaterMarkDrawable waterMarkDrawable;

    private RadioButton radioButtonBgColor;
    private RadioButton radioButtonTextColor;
    private TextView tvColorSample;
    private Spinner spinnerMode;
    private Spinner spinnerType;
    private Spinner spinnerGravity;
    private Spinner spinnerGravity2;

    private int spinnerSelectGravity;
    private int spinnerSelectGravity2;
    private EditText etWatermarkText;
    private Button btnApplyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llBg = findViewById(R.id.llBg);
        seekBarDegrees = findViewById(R.id.seekBarDegrees);
        seekBarTextSize = findViewById(R.id.seekBarTextSize);
        seekBarHorizontalSpace = findViewById(R.id.seekBarHorizontalSpace);
        seekBarVerticalSpace = findViewById(R.id.seekBarVerticalSpace);
        seekBarOffSpace = findViewById(R.id.seekBarOffSpace);
        seekBarColorA = findViewById(R.id.seekBarColorA);
        seekBarColorR = findViewById(R.id.seekBarColorR);
        seekBarColorG = findViewById(R.id.seekBarColorG);
        seekBarColorB = findViewById(R.id.seekBarColorB);
        radioButtonBgColor = findViewById(R.id.radioButtonBgColor);
        radioButtonTextColor = findViewById(R.id.radioButtonTextColor);
        tvColorSample = findViewById(R.id.tvColorSample);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerMode = findViewById(R.id.spinnerMode);
        spinnerGravity = findViewById(R.id.spinnerGravity);
        spinnerGravity2 = findViewById(R.id.spinnerGravity2);
        etWatermarkText = findViewById(R.id.etWatermarkText);
        btnApplyText = findViewById(R.id.btnApplyText);
        btnApplyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etWatermarkText.getText().toString();
            }
        });
        etWatermarkText.addTextChangedListener(new TextWatcher() {
            //This method is called to notify you that, within s,
            // the count characters beginning at start are about to be replaced by new text with length after.

            //s，是改变之前的字符。
            //从start位置开始，将会有count个字符【被】之后的after个字符替换（将会有after个字符替换之前的count个字符）
            // 不推荐在这个回调方法中修改EditText的内容
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("MainActivity", "beforeTextChanged: charSequence=" + s + ", start=" + start + ", count=" + count + ", after=" + after);
            }

            //This method is called to notify you that, within s,
            // the count characters beginning at start have just replaced old text that had length before.

            //s，是改变后的字符
            // 从start位置开始，将会有count个字符替换之前的before个字符
            // 不推荐在这个回调方法中修改EditText的内容
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("MainActivity", "onTextChanged: charSequence=" + s + ", start=" + start + ", before=" + before + ", count=" + count);
            }

            //This method is called to notify you that, somewhere within s, the text has been changed.
            //每当editText输入框里的字符变化的时候，都会回调该方法。在方法里如果要修改EditText的内容时，
            //小心不要陷入死循环。
            @Override
            public void afterTextChanged(Editable s) {
                Log.i("MainActivity", "afterTextChanged: charSequence=" + s);
                waterMarkDrawable.setWaterMarkText(s.toString());

            }
        });
        waterMarkDrawable = WaterMarkHelper.addWaterMark(llBg);
        spinnerType.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Arrays.asList("给指定view添加", "给activity添加")));
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    String s = tv.getText().toString();
                    switch (s) {
                        case "给指定view添加":
                            WaterMarkHelper.removeWaterMark(MainActivity.this);
                            WaterMarkHelper.addWaterMark(llBg, waterMarkDrawable);
                            break;
                        case "给activity添加":
                            //下面两个代码的顺序很重要。。。
                            //view在设置background之前，
                            // 会mBackground.setCallback(null);把view之前设置的background的回调设置为null。
                            // 导致接下来对drawable的任何属性设置都不会刷新页面
                            WaterMarkHelper.removeWaterMark(llBg);
                            WaterMarkHelper.addWaterMark(MainActivity.this, waterMarkDrawable);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGravity.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Arrays.asList("left", "top", "right",
                "bottom", "center", "horizontalCenter", "verticalCenter")));
        spinnerGravity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerGravitySelect(view, spinnerGravity);
                Log.d("spinnerGravity", "position" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGravity2.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Arrays.asList("left", "top", "right",
                "bottom", "center", "horizontalCenter", "verticalCenter")));
        spinnerGravity2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerGravitySelect(view, spinnerGravity2);
                Log.d("spinnerGravity2", "position" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerMode.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Arrays.asList("repeat", "single")));
        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    String s = tv.getText().toString();
                    switch (s) {
                        case "single":
                            waterMarkDrawable.setWatermarkMode(WatermarkMode.SINGLE);
                            break;
                        case "repeat":
                            waterMarkDrawable.setWatermarkMode(WatermarkMode.REPEAT);
                            break;
                    }
                }
                Log.d("spinnerMode", "position" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seekBarDegrees.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float degrees = progress / 100.0F * 360;
                waterMarkDrawable.setDegrees(degrees);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterMarkDrawable.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarVerticalSpace.setMax(1000);
        seekBarVerticalSpace.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterMarkDrawable.setWaterMarkVerticalSpacing(progress, TypedValue.COMPLEX_UNIT_DIP);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarHorizontalSpace.setMax(1000);
        seekBarHorizontalSpace.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterMarkDrawable.setWaterMarkHorizontalSpacing(progress, TypedValue.COMPLEX_UNIT_DIP);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarOffSpace.setMax(1000);
        seekBarOffSpace.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterMarkDrawable.setOffSpace(progress, TypedValue.COMPLEX_UNIT_DIP);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        radioButtonBgColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonTextColor.setChecked(false);
                }
            }
        });
        radioButtonTextColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonBgColor.setChecked(false);
                }
            }
        });
        MyColorSeekBarChangeListener listener = new MyColorSeekBarChangeListener();
        seekBarColorA.setOnSeekBarChangeListener(listener);
        seekBarColorR.setOnSeekBarChangeListener(listener);
        seekBarColorG.setOnSeekBarChangeListener(listener);
        seekBarColorB.setOnSeekBarChangeListener(listener);
        List<TextView> textViewList = new ArrayList<>();
        TextView textView = new TextView(this);
        TextView textView2 = new Button(this);
        textViewList.add(textView);
        textViewList.add(textView2);
        textView = null;
        Log.e("view", "textView" + textView);
        Log.e("view", "textView2" + textView2);
        for (TextView view : textViewList) {
            Log.e("View", view.toString());
        }
    }

    @SuppressLint("WrongConstant")
    private void onSpinnerGravitySelect(View view, Spinner spinnerGravity) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            String s = tv.getText().toString();
            int gravity = 0;
            switch (s) {
                case "left":
                    gravity = WatermarkGravity.LEFT;
                    break;
                case "top":
                    gravity = WatermarkGravity.TOP;
                    break;
                case "right":
                    gravity = WatermarkGravity.RIGHT;
                    break;
                case "bottom":
                    gravity = WatermarkGravity.BOTTOM;
                    break;
                case "center":
                    gravity = WatermarkGravity.CENTER;
                    break;
                case "horizontalCenter":
                    gravity = WatermarkGravity.CENTER_HORIZONTAL;
                    break;
                case "verticalCenter":
                    gravity = WatermarkGravity.CENTER_VERTICAL;
                    break;
            }
            if (spinnerGravity == this.spinnerGravity) {
                spinnerSelectGravity = gravity;
            } else {
                spinnerSelectGravity2 = gravity;
            }
            waterMarkDrawable.setWatermarkGravity(spinnerSelectGravity | spinnerSelectGravity2);
        }
    }

    private class MyColorSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            if (seekBar == seekBarColorA) {
//
//            } else if (seekBar == seekBarColorR) {
//
//            } else if (seekBar == seekBarColorG) {
//
//            } else if (seekBar == seekBarColorB) {
//
//            }
            int argb = Color.argb(seekBarColorA.getProgress(), seekBarColorR.getProgress(), seekBarColorG.getProgress(), seekBarColorB.getProgress());
            tvColorSample.setBackgroundColor(argb);
            if (radioButtonBgColor.isChecked()) {
                waterMarkDrawable.setBgColor(argb);
            } else if (radioButtonTextColor.isChecked()) {
                waterMarkDrawable.setTextColor(argb);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}