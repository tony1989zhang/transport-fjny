package com.fjny.myapplication.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.fjny.myapplication.R;


public class LianxikefuFragment extends BaseFragment {
    private Button mBtnWebView;
    private Button mBtnPhone;

    @Override
    int getLayoutId() {
        return R.layout.lianxikefu_fragment;

    }

    @Override
    void initView(View view) {
        mBtnWebView  = view.findViewById(R.id.btn_webview);
        mBtnPhone = view.findViewById(R.id.btn_phone);
    }

    @Override
    void initData() {
        mBtnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.baidu.com");
                /*Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);*/
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                /*//如果用WebView加载：
                WebView.loadUrl("http://www.baidu.com");*/

            }
        });

        mBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到拨号界面
                // 需添加电话拨号权限 android.permission.CALL_PHONE
                Uri uri = Uri.parse("tel:10086");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });


    }
}
