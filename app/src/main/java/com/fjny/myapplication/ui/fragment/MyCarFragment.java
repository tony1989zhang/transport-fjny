package com.fjny.myapplication.ui.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fjny.myapplication.R;
import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetBalanceRequest;
import com.fjny.myapplication.request.SetBalanceRequest;
import com.fjny.myapplication.utils.Session;

public class MyCarFragment extends BaseFragment {
    private TextView carBalance;

    @Override
    int getLayoutId() {
        return R.layout.my_car_fragment;
    }

    @Override
    void initView(View view) {
        carBalance = view.findViewById(R.id.car_balance);
    }

    @Override
    void initData() {
        //初始化ip和服务器类型
        Session.ip = AppConfig.IP_DEFAULT;
        Session.ipFlag = AppConfig.IP_LOCAL;
        //获取余额 参数1:小车id
        getBalance(1);
        //充值余额 参数1：小车id 参数2:充值的金额
        setBalance(1,100);
    }


    //获取余额
    void getBalance(int carId){
        GetBalanceRequest getBalaceRequest = new GetBalanceRequest(mContext);
        getBalaceRequest.getId(carId);
        getBalaceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                carBalance.setText(data.toString());
            }
        });
    }
    //充值余额
    void setBalance(int carId,int money){
        SetBalanceRequest setBalanceRequest = new SetBalanceRequest(mContext);
        setBalanceRequest.setMoney(carId,money);
        setBalanceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                if (data.equals("ok")){
                    Toast.makeText(mContext,"充值成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //回到这个页面记得刷新
    @Override
    public void onResume() {
        super.onResume();
        getBalance(1);
    }
}
