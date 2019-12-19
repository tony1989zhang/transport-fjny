package com.fjny.myapplication.ui.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fjny.myapplication.R;
import com.fjny.myapplication.factory.SpinnerFactory;
import com.fjny.myapplication.model.HonlvdengInfo;
import com.fjny.myapplication.model.TrafficInfo;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetDoaluzhuangkuan;
import com.fjny.myapplication.request.GetHonlvdnegRequest;
import com.fjny.myapplication.request.GetTrafficRequest;
import com.fjny.myapplication.ui.adapter.TrafficsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DaoluzhuangkFragment extends BaseFragment {
    private TextView [] road;//1~4道路 文本数组框
    private Spinner sSortMode;
    private String [] sSpinner;
    private ListView lvTraffic;
    private TrafficsAdapter adapter;
    private String [] text;
    private String [] color;
    private List<TrafficInfo> traffics;
    private boolean don;
    private int SortMode;
    private int SortField;
//    private TextView textView1;

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
        lvTraffic =view.findViewById(R.id.road_traffic);
        sSortMode =view.findViewById(R.id.road_spinner);

//        textView1 = view.findViewById(R.id.texts);

    }

    @Override
    void initData() {
        traffics =new ArrayList<>();
        adapter =new TrafficsAdapter(mContext,traffics);
        lvTraffic.setAdapter(adapter);
        SortMode =0;
        SortField =0;
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
        sSpinner =new String[]{
                "路口升序",
                "路口降序",
                "红灯升序",
                "红灯降序",
                "绿灯升序",
                "绿灯降序",
                "黄灯升序",
                "黄灯降序"
        };

        thread.start();
SpinnerFactory.getSpinner(mContext,sSpinner,sSortMode,new SpinnerFactory.SpinnerListener(){
    @Override
    public void onSelector(int position) {
        SortMode =position%2==0? 0:1;

        SortField =position <2 ? 0 :position <4 ? 1 :position <6 ? 2 :3;

        queryTrafficInfo();
    }
});

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


    //获取红绿灯        GetHonlvdnegRequest getHonlvdnegRequest = new GetHonlvdnegRequest(mContext);
    //        getHonlvdnegRequest.setRoadId(roadId);
    //        getHonlvdnegRequest.connec(new BaseRequest.BaseRequestListener() {
    //            @Override
    //            public void onReturn(Object data) {
    //                HonlvdengInfo honlvdengInfo =(HonlvdengInfo)data;
    ////                textView1.setText(""+honlvdengInfo.getRoadId()+","+honlvdengInfo.getRedTime()+","+honlvdengInfo.getGreenTime()+","+honlvdengInfo.getYellowTime());
    //            }
    //        });
    private void getHonlvdeng(int roadId){

    }
    private void queryTrafficInfo(){
        traffics.clear();
        final int[] count ={0};
        for (int i=1 ;i<5;i++){
            GetTrafficRequest getTrafficRequest =new GetTrafficRequest(mContext);
            getTrafficRequest.setRoadId(i);
            getTrafficRequest.connec(new BaseRequest.BaseRequestListener() {
                @Override
                public void onReturn(Object data) {
                    traffics.add((TrafficInfo)data);
                    count[0]++;
                    if (count[0] ==4){
                        sortList();
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void sortList() {
        for (int i =0;i<traffics.size()-1;i++){
            for (int j=0;j<traffics.size()-i-1;j++){
                int[] list1 =new int[]{
                        traffics.get(j).getRoadId(),
                        traffics.get(j).getRedTime(),
                        traffics.get(j).getYellowTime(),
                        traffics.get(j).getGreenTime()
                };
                int[] list2 =new int[]{
                        traffics.get(j+1).getRoadId(),
                        traffics.get(j+1).getRedTime(),
                        traffics.get(j+1).getYellowTime(),
                        traffics.get(j+1).getGreenTime()
                };
                if (SortMode ==0){
                    if (list1[SortField] >list2[SortField]){
                        TrafficInfo temp =traffics.get(j);
                        traffics.set(j,traffics.get(j+1));
                        traffics.set(j+1,temp);
                    }
                } else {
                    if (list1[SortField] <list2[SortField]){
                        TrafficInfo temp =traffics.get(j);
                        traffics.set(j,traffics.get(j+1));
                        traffics.set(j+1,temp);
                    }
                }
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        don = false;
    }
}
