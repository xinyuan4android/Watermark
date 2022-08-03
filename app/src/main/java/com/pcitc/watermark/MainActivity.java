package com.pcitc.watermark;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llBg;
    private SeekBar seekBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llBg = findViewById(R.id.llBg);
        seekBar = findViewById(R.id.seekBar);
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
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("watermark", "seekBar " + progress);
                waterMarkDrawable.setWaterMarkText("change" + progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarDegrees.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float degrees = -90 + progress / 100.0F * 180;
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
        waterMarkDrawable = WaterMarkHelper.addWaterMark(llBg);
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