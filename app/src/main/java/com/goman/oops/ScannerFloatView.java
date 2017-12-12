package com.goman.oops;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by lingrui on 2017/12/12.
 */

public class ScannerFloatView {

    WindowManager mWindowManager;

    EditText floatWindowView;

    public void init(Context context){
        //Activity中的方法,得到窗口管理器
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        add1(context);

        //设置悬浮窗布局属性
        WindowManager.LayoutParams mWindowLayoutParams = new WindowManager.LayoutParams();
        //设置类型,具体有哪些值可取在后面附上
        mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置行为选项,具体有哪些值可取在后面附上
        //mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        //设置悬浮窗的显示位置
        mWindowLayoutParams.gravity = Gravity.END | Gravity.TOP;
//        //设置悬浮窗的横竖屏,会影响屏幕方向,只要悬浮窗不消失,屏幕方向就会一直保持,可以强制屏幕横屏或竖屏
//        mWindowLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        //设置 x 轴的偏移量
        mWindowLayoutParams.x = 0;
        //设置 y 轴的偏移量
        mWindowLayoutParams.y = 0;
        //如果悬浮窗图片为透明图片,需要设置该参数为 PixelFormat.RGBA_8888
        mWindowLayoutParams.format = PixelFormat.TRANSLUCENT;
        //设置悬浮窗的宽度
        mWindowLayoutParams.width = 500;
        //设置悬浮窗的高度
        mWindowLayoutParams.height = 100;
        //设置悬浮窗的布局
        floatWindowView = (EditText) LayoutInflater.from(context).inflate(R.layout.layout_scanner_float, null);
        floatWindowView.requestFocus();
        //加载显示悬浮窗
        mWindowManager.addView(floatWindowView, mWindowLayoutParams);
    }

    public void add1(Context context){
        //设置悬浮窗布局属性
        WindowManager.LayoutParams mWindowLayoutParams = new WindowManager.LayoutParams();
        //设置类型,具体有哪些值可取在后面附上
        mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置行为选项,具体有哪些值可取在后面附上
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置悬浮窗的显示位置
        mWindowLayoutParams.gravity = Gravity.END | Gravity.TOP;
//        //设置悬浮窗的横竖屏,会影响屏幕方向,只要悬浮窗不消失,屏幕方向就会一直保持,可以强制屏幕横屏或竖屏
//        mWindowLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        //设置 x 轴的偏移量
        mWindowLayoutParams.x = 0;
        //设置 y 轴的偏移量
        mWindowLayoutParams.y = 0;
        //如果悬浮窗图片为透明图片,需要设置该参数为 PixelFormat.RGBA_8888
        mWindowLayoutParams.format = PixelFormat.TRANSLUCENT;
        //设置悬浮窗的宽度
        mWindowLayoutParams.width = 10;
        //设置悬浮窗的高度
        mWindowLayoutParams.height = 10;
        //设置悬浮窗的布局
        View view = LayoutInflater.from(context).inflate(R.layout.layout_float, null);
        //加载显示悬浮窗
        mWindowManager.addView(view, mWindowLayoutParams);
    }

    public void unbind(){
        mWindowManager.removeView(floatWindowView);
    }

}
