package com.goman.oops;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by lingrui on 2017/12/12.
 */

public class FloatView {

    WindowManager mWindowManager;

    WindowManager.LayoutParams mWindowLayoutParams;

    EditText mEditText;

    public void init(Context context){
        //Activity中的方法,得到窗口管理器
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //设置悬浮窗布局属性
        mWindowLayoutParams = new WindowManager.LayoutParams();
        //设置类型,具体有哪些值可取在后面附上
        mWindowLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置行为选项,具体有哪些值可取在后面附上
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //设置悬浮窗的显示位置
        mWindowLayoutParams.gravity = Gravity.END | Gravity.TOP;
//        //设置悬浮窗的横竖屏,会影响屏幕方向,只要悬浮窗不消失,屏幕方向就会一直保持,可以强制屏幕横屏或竖屏
//        mWindowLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        //设置 x 轴的偏移量
        mWindowLayoutParams.x = 0;
        //设置 y 轴的偏移量
        mWindowLayoutParams.y = 0;
        //如果悬浮窗图片为透明图片,需要设置该参数为 PixelFormat.RGBA_8888
        mWindowLayoutParams.format = PixelFormat.RGBA_8888;
        //设置悬浮窗的宽度
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置悬浮窗的高度
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置悬浮窗的布局
        mEditText = (EditText) LayoutInflater.from(context).inflate(R.layout.layout_float, null);

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                    App.getContext().backPressResumeActivity();
                    //unbind();
                    //Process.killProcess(Process.myPid());
                    return true;
                }
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    String code = mEditText.getText().toString();
                    Log.e("floatview", code);
                    mEditText.setText("");
                    return true;
                }
                return false;
            }
        });

    }

    public void show(){
        //加载显示悬浮窗
        mWindowManager.addView(mEditText, mWindowLayoutParams);
    }

    public void cancel(){
        mEditText.setText("");
        mWindowManager.removeView(mEditText);
    }

}
