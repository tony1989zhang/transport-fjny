package com.fjny.myapplication.factory;

import android.content.Context;
import android.widget.Toast;

/*
* 自定义消息框工程类
*
* */
public class ToastFactory {
    //重载 短消息
    public  static void show(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    //重载 长消息
    public static void show(Context context,String text,boolean isLong){
        int length = isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(context,text,length).show();
    }
}
