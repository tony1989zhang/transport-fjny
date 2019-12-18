package com.fjny.myapplication.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.fjny.myapplication.R;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetDoaluzhuangkuan;

public class DaoluzhuangkFragment extends BaseFragment {
    private TextView daoluzhuangtai;

    @Override
    int getLayoutId() {
        return R.layout.daoluzhuangkuang_fragment;
    }

    @Override
    void initView(View view) {
        daoluzhuangtai = view.findViewById(R.id.doaluzhuangk_text);
    }

    @Override
    void initData() {

        thread.start();
    }
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

            while (true){
                lookDaolu(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    });

    //获取道路状况
    private void lookDaolu(int roadId){
        GetDoaluzhuangkuan doaluzhuangkuan = new GetDoaluzhuangkuan(mContext);
        doaluzhuangkuan.getRoadId(1);
        doaluzhuangkuan.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                int zhuangtai = (int)data;
                daoluzhuangtai.setText(zhuangtai+"");
            }
        });
    }
}
