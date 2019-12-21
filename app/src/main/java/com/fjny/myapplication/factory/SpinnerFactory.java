package com.fjny.myapplication.factory;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fjny.myapplication.R;

public class SpinnerFactory {
    //定义项目选中事件监听器 公开接口
    public interface SpinnerListener{
        void onSelector(int position);
    }
    public static void getSpinner(Context context, String[] datas, Spinner spinner, int layoutId, final SpinnerListener listener){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, layoutId,datas);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onSelector(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
