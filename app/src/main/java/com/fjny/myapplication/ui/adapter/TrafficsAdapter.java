package com.fjny.myapplication.ui.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fjny.myapplication.R;
import com.fjny.myapplication.model.TrafficInfo;
import com.fjny.myapplication.ui.fragment.BaseFragment;

import java.util.List;

public class TrafficsAdapter extends BaseAdapter {
    private Context context;
    private List<TrafficInfo> traffic;

    private TextView RoadId;
    private TextView RedTime;
    private TextView GreenTime;
    private TextView YellowTime;

    public TrafficsAdapter(Context context, List<TrafficInfo> traffics) {
        this.context =context;
        this.traffic =traffics;
    }

    @Override
    public int getCount() {

        return traffic.size();
    }

    @Override
    public Object getItem(int i) {
        return traffic.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
            if (convertView ==null){
                view =inflater.inflate(R.layout.item_spinner,null);
            } else {
                view =convertView;
            }
        RoadId = view.findViewById(R.id.item_roadid);
            RedTime =view.findViewById(R.id.item_redtime);
            GreenTime =view.findViewById(R.id.item_greentime);
            YellowTime =view.findViewById(R.id.item_yellowtime);

            RoadId.setText(traffic.get(i).getRoadId()+"");
            RedTime.setText(traffic.get(i).getRedTime()+"");
            GreenTime.setText(traffic.get(i).getGreenTime()+"");
            YellowTime.setText(traffic.get(i).getYellowTime()+"");
            return view;
    }
}
