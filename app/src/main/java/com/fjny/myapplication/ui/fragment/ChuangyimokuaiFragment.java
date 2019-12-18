package com.fjny.myapplication.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.fjny.myapplication.R;

public class ChuangyimokuaiFragment extends BaseFragment {
    private Button button;


    @Override
    int getLayoutId() {
        return R.layout.chuangyimokuai_fragment;
    }

    @Override
    void initView(View view) {
        button = view.findViewById(R.id.btn_color1);
    }

    @Override
    void initData() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
