package com.goman.oops;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("key event", event.toString());
        return super.dispatchKeyEvent(event);
    }
}
