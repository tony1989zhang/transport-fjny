
package com.fjny.myapplication.ui.fragment;

import android.icu.text.UnicodeSetSpanner;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fjny.myapplication.R;
import com.fjny.myapplication.model.ChuxinghuanjingInfo;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetChuxinghuanjingRequest;

import org.w3c.dom.Text;

public class ChuxinghuanjingFragment extends BaseFragment {
    private boolean isF5;//是否开启线程
    private TextView mTvT;
    private TextView mTvPm;
    private TextView mTvCo2;
    private TextView mTvLight;
    private TextView mTvHumidity;

    @Override
    int getLayoutId() {
        return R.layout.chuxinghuanjing_fragment;
    }

    @Override
    void initView(View view) {
        mTvT = view.findViewById(R.id.env_T);
        mTvPm = view.findViewById(R.id.env_Pm);
        mTvCo2 = view.findViewById(R.id.env_Co2);
        mTvLight = view.findViewById(R.id.env_Light);
        mTvHumidity = view.findViewById(R.id.env_Humidity);
    }

    @Override
    void initData() {
        //初始化开启线程
        isF5 = true;
        if (isF5) {
            thread.start();
        }

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChuxinghuanjingInfo chuxinghuanjingInfo = (ChuxinghuanjingInfo) msg.obj;
            //设置值
            String tem = chuxinghuanjingInfo.getTemp() < 10 ? "0"+chuxinghuanjingInfo.getTemp(): chuxinghuanjingInfo.getTemp()+"";
            mTvT.setText(tem + "°C");
            mTvPm.setText(chuxinghuanjingInfo.getPm() + "");
            mTvCo2.setText(chuxinghuanjingInfo.getCo2() + "");
            mTvLight.setText(chuxinghuanjingInfo.getLight() + "");
            mTvHumidity.setText(chuxinghuanjingInfo.getHumidity() + "");
        }
    };
    Thread thread = new Thread() {
        @Override
        public void run() {
            while (isF5) {
                GetChuxinghuanjing();
                try {
                    //三秒显示刷新一次
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //获取出行环境的数值
    void GetChuxinghuanjing() {
        GetChuxinghuanjingRequest chuxinghuanjingRequest = new GetChuxinghuanjingRequest(mContext);
        chuxinghuanjingRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                if (data != null) {
                    ChuxinghuanjingInfo chuxinghuanjingInfo = (ChuxinghuanjingInfo) data;
                    Message message = new Message();
                    message.obj = chuxinghuanjingInfo;
                    handler.sendMessage(message);
                }
            }
        });
    }
    //切换页面后线程停止
    @Override
    public void onPause() {
        super.onPause();
        isF5 = false;
    }
}
