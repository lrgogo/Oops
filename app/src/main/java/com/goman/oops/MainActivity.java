package com.goman.oops;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new AlertDialog.Builder(this)
                .setTitle("确定退出吗?")
                .show();

    }

    @OnClick(R.id.btn_open)
    public void open(){
        new AlertDialog.Builder(this)
                .setTitle("确定打开吗?")
                .show();
    }

    @OnClick(R.id.btn_follow)
    public void follow(){
        new AlertDialog.Builder(this)
                .setTitle("确定关注吗?")
                .show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("key event", event.toString());
        return super.dispatchKeyEvent(event);
    }
}
