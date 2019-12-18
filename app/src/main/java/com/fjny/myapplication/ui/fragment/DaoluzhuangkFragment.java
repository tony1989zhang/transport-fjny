package com.fjny.myapplication.ui.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.fjny.myapplication.R;
import com.fjny.myapplication.model.HonlvdengInfo;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetDoaluzhuangkuan;
import com.fjny.myapplication.request.GetHonlvdnegRequest;

public class DaoluzhuangkFragment extends BaseFragment {
    private TextView [] road;//1~4道路 文本数组框
    private String [] text;
    private String [] color;
    private boolean don;
    private TextView textView1;

    @Override
    int getLayoutId() {
        return R.layout.daoluzhuangkuang_fragment;
    }

    @Override
    void initView(View view) {
        road = new TextView[4];
        road[0] = view.findViewById(R.id.raod1);
        road[1] = view.findViewById(R.id.raod2);
        road[2] = view.findViewById(R.id.raod3);
        road[3] = view.findViewById(R.id.raod4);

        textView1 = view.findViewById(R.id.texts);

    }

    @Override
    void initData() {
            don = true;
        text = new String[]{
                "通畅",
                "较通畅",
                "拥挤",
                "堵塞",
                "爆表"
        };
        color = new String[]{
                "#049B07",      // 通畅 颜色
                "#73C400",      // 较通畅 颜色
                "#CBCB20",      // 拥挤 颜色
                "#A55921",      // 堵塞 颜色
                "#4c060e"       // 爆表 颜色
        };

        thread.start();

        //获取1号路的红绿灯信息
        getHonlvdeng(1);


    }
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

            while (don){
                for (int i = 0; i<4;i++){
                    lookDaolu(i);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    //获取道路状况
    private void lookDaolu(final int roadId){
        GetDoaluzhuangkuan doaluzhuangkuan = new GetDoaluzhuangkuan(mContext);
        doaluzhuangkuan.getRoadId(1);
        doaluzhuangkuan.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                int zhuangtai = (int)data;
                road[roadId].setText(text[zhuangtai - 1]);
                road[roadId].setBackgroundColor(Color.parseColor(color[zhuangtai - 1]));
            }
        });
    }


    //获取红绿灯
    private void getHonlvdeng(int roadId){
        GetHonlvdnegRequest getHonlvdnegRequest = new GetHonlvdnegRequest(mContext);
        getHonlvdnegRequest.setRoadId(roadId);
        getHonlvdnegRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                HonlvdengInfo honlvdengInfo =(HonlvdengInfo)data;
                textView1.setText(""+honlvdengInfo.getRoadId()+","+honlvdengInfo.getRedTime()+","+honlvdengInfo.getGreenTime()+","+honlvdengInfo.getYellowTime());
            }
        });


    }


    @Override
    public void onPause() {
        super.onPause();
        don = false;
    }
}
