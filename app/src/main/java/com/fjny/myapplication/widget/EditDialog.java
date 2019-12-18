package com.fjny.myapplication.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.factory.ToastFactory;
import com.fjny.myapplication.utils.Session;

//带编辑框的对话框 小部件
public class EditDialog {

    //定义 事件监听接口
    public interface OnListener{
        //接收编辑库输入字符串
        void onAfter(String input);
    }

    //定义 显示 静态方法
    public static void show(final Context context, String title, final EditDialog.OnListener onAfter){

        //初始化一个编辑库
        final EditText et = new EditText(context);
        //如已设置IP则显示出来
        if(!Session.ip.equals(AppConfig.IP_DEFAULT)){
            et.setText(Session.ip);
        }

        new AlertDialog.Builder(context).setTitle(title) //设置标题
                .setView(et)   //s给对话框添加一个编辑框
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if(input.equals("")){
                            ToastFactory.show(context,"内容不能为空");
                        }else{
                            //传入 编辑框输入字符串 给外部接口实现回调
                            onAfter.onAfter(input);
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .show();    //显示对话框
    }
}
