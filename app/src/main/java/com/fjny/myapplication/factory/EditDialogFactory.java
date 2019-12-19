package com.fjny.myapplication.factory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class EditDialogFactory {

    //定义事件监听器接口
    public interface OnListener{
        void onAfter(String input);
    }

    //定义显示静态方法
    public static  void  show(final Context context, String title, final OnListener listener){
        final EditText et = new EditText(context);

        new AlertDialog.Builder(context).setTitle(title).setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = et.getText().toString();
                if (input.equals("")){
                    ToastFactory.show(context,"内容不能为空");
                }else {
                    listener.onAfter(input);
                }
            }
        });
    }
}
