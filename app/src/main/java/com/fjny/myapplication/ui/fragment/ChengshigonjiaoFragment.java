package com.fjny.myapplication.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.fjny.myapplication.R;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetBusStation;

import java.util.List;

public class ChengshigonjiaoFragment extends BaseFragment {

    private boolean isgo;
    private TextView Bus1;
    @Override
    int getLayoutId() {
        return R.layout.chengshigojiao_fragment;
    }

    @Override
    void initView(View view) {
        Bus1 = view.findViewById(R.id.bus1);
    }

    @Override
    void initData() {
        //初始化开启线程
        isgo = true;



        //开启线程
        if (isgo) {
            thread.start();
        }
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isgo){

                //一号站台
                //一号站台
                //一号站台
                getBusStation(1);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    //连接站台数据
    public void getBusStation(int BusStationId){
        GetBusStation getBusStation = new GetBusStation(mContext);
        getBusStation.getBusStationId(BusStationId);
        getBusStation.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                List<Integer> list = (List<Integer>)data;

                //这边输入1号和0号公交
                Bus1.setText(list.get(0)+","+list.get(1));
            }
        });
    }

    //关闭时关闭线程
    @Override
    public void onPause() {
        super.onPause();
        isgo = false;
    }
}
