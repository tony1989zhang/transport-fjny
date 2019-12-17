package com.fjny.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.fjny.myapplication.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initParams();
    }

    //获取布局Id
    abstract int getLayoutId();

    //初始化视图
    abstract void initView();

    //初始化参数
    abstract void initParams();
}
