### Watermark
* * *
方便为android开发者提供给view添加背景或者前景水印的库。
通过自定义`Drawable`的方式实现自定义水印，在不入侵代码的情况下，最大限度方便大家设置。
![image](https://raw.githubusercontent.com/xinyuan4android/Watermark/master/screenshot/add2Activity.gif)
![image](https://raw.githubusercontent.com/xinyuan4android/Watermark/master/screenshot/add2View.gif)
### how to add
* * *
#### Gradle Groovy DSL gradle.org
```
implementation 'io.github.xinyuan4android:watermark:latest.release'
```
#### maven.apache.org
```
<dependency>
  <groupId>io.github.xinyuan4android</groupId>
  <artifactId>watermark</artifactId>
  <version>latest.release</version>
  <type>aar</type>
</dependency>
```
### androdx and support
support版本的release版本号，就是在版本号前加一个`S.`。比如：
```
//androidx版本
implementation 'io.github.xinyuan4android:watermark:0.0.2-beta'
//support版本
implementation 'io.github.xinyuan4android:watermark:S.0.0.2-beta'
```

### how to use
#### 1.初始化Watermark
在自定义application类的`onCreate()`方法里添加如下初始化方法。
```
 WaterMarkHelper.init(application);
```
#### 2.实例化Watermark
目前提供三个构造方法
```
//无参
WaterMarkDrawable waterMarkDrawable = new WaterMarkDrawable();
//一个参数。传入String，表示要展示的水印文字
WaterMarkDrawable waterMarkDrawable = new WaterMarkDrawable("水印文字");
//三个参数，分别代表水印文字，水印的背景色，水印的文字颜色
WaterMarkDrawable waterMarkDrawable = new WaterMarkDrawable("水印文字",int bgColor,int textColor);

```
#### 3.设置属性值
目前提供了一些自认为常用的属性值，还可以继续完善。
```
// 设置水印的模式。目前分两种： 
// 1.WatermarkMode.REPEAT 重复模式
// 2.WatermarkMode.SINGLE 单一模式
waterMarkDrawable.setWatermarkMode();

// 两种模式下的公共方法
// 设置水印的文字。如果想换行可以用\n分割。
waterMarkDrawable.setWaterMarkText("水印文字\n第二行水印文字");

// 设置水印的旋转角度。正数是顺时针旋转，负数是逆时针旋转。
waterMarkDrawable.setDegrees(-30.0F);

// 设置水印的文字大小。单位sp
waterMarkDrawable.setTextSize(15);
// 或者自定义单位。示例单位为px像素
waterMarkDrawable.setTextSize(15, TypedValue.COMPLEX_UNIT_PX);

// 设置水印文字颜色。参数为@ColorInt int
waterMarkDrawable.setTextColor(WaterMarkUtils.getResources().getColor(R.color.colorAccent));
// 或者颜色字符串
waterMarkDrawable.setTextColor("#FF000000");

// 设置水印背景色，方法类似文字颜色
waterMarkDrawable.setBgColor(int);
waterMarkDrawable.setBgColor(string);

```
公共参数介绍完毕，接下里是两中模式各自特有的方法。特有方法是指只有设置了对应的模式后，才会生效的方法。
目前`WatermarkMode.SINGLE` 单一模式只有一个特殊方法，设置水印的位置。
```
// WatermarkMode.SINGLE 单一模式
// 设置水印的位置。
// 目前参数提供了七种常用的位置。TOP, LEFT, RIGHT, BOTTOM, CENTER, CENTER_VERTICAL, CENTER_HORIZONTAL
// 七种可以自由组合使用。比如TOP|RIGHT，CENTER_VERTICAL|LEFT等。
waterMarkDrawable.setWatermarkGravity(WatermarkGravity.CENTER);

```

`WatermarkMode.REPEAT 重复模式`的特殊方法如下。
```
// WatermarkMode.REPEAT 重复模式
// 设置水印和水印之间的水平间距。单位px像素。
 waterMarkDrawable.setWaterMarkHorizontalSpacing(100.0F);
 // 或者自定义单位。示例设置的单位为dp。
waterMarkDrawable.setWaterMarkHorizontalSpacing(100.0F,TypedValue.COMPLEX_UNIT_DIP);

// 设置水印和水印之间的垂直间距。方法和水平类似。
waterMarkDrawable.setWaterMarkVerticalSpacing(float);
waterMarkDrawable.setWaterMarkVerticalSpacing(float,int);

// 设置水印每行之间的偏移距离。单位px像素。
/*
 * 偏移距离指的是，每行水印，行与行之间的偏移距离。
 * 比如下面的例子，---表示偏离距离
 * 1111111
 * ---1111111
 * 1111111
 * ---1111111
 */
waterMarkDrawable.setOffSpace(100.0F);
// 当然也可以自定义单位。示例单位是dp。
waterMarkDrawable.setOffSpace(100.0F,TypedValue.COMPLEX_UNIT_DIP);

```
#### 4.给view设置水印
```
//给view设置背景
view.setBackground(waterMarkDrawable);
//给view设置前景
view.setForeground(waterMarkDrawable);
```

### 其他方法
目前库里提供了两种方法
#### 1.给指定view添加水印
这个方法主要是方便演示功能，具体情况开发者可以自行发挥。
```
// 给指定view添加水印，返回值为水印对象。
// 拿到水印对象后，可以自定义设置水印属性。
WaterMarkDrawable  waterMarkDrawable = WaterMarkHelper.addWaterMark(view);

// 删除指定view的水印
// 如果drawable instance WatermarkDrawable才会删除
WaterMarkHelper.removeWaterMark(view);
```
#### 2.给activity添加水印
这个方法，主要是方便给整个页面添加前景水印，也是目前我的项目中的需求。
原理是在`DecorView`(FrameLayout)中，添加一个`RelativeLayout`，然后给以这个RelativeLayout为水印view，给他设置`setBackground()`水印，就相当于在前景覆盖了一层水印。
```
// 给指定activity添加’前景水印’
WaterMarkDrawable waterMarkDrawable = WaterMarkHelper.addWaterMark(activity);

// 删除指定activity的’前景水印‘
WaterMarkDrawable waterMarkDrawable = WaterMarkHelper.removeWaterMark(activity);

```

只要在activity的基类中的`onCreate()`方法中，调用`addWaterMark()`方法，就可以给所有的页面前景水印。

