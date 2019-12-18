package com.fjny.myapplication.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fjny.myapplication.R;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.SetBalanceRequest;
import com.fjny.myapplication.ui.fragment.MyCarFragment;

public class setMoneyActivity extends BaseActivity implements View.OnClickListener {
    private TextView carId_text;
    int carId;
    private Button money20, money50, money100, moneyX;



    @Override
    int getLayoutId() {
        return R.layout.activity_set_money;
    }

    @Override
    void initView() {
        carId_text = findViewById(R.id.car_id);
        money20 = findViewById(R.id.money_20);
        money50 = findViewById(R.id.money_50);
        money100 = findViewById(R.id.money_100);
        moneyX = findViewById(R.id.money_x);

    }

    @Override
    void initParams() {
        Bundle bundle = getIntent().getExtras();
        carId = bundle.getInt("carId");
        carId_text.setText(carId+"");
        money20.setOnClickListener(this);
        money50.setOnClickListener(this);
        money100.setOnClickListener(this);
        moneyX.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.money_20:
                //充值余额 参数1：小车id 参数2:充值的金额
                setBalance(carId,20);
                break;
            case R.id.money_50:
                //充值余额 参数1：小车id 参数2:充值的金额
                setBalance(carId,50);
                break;
            case R.id.money_100:
                //充值余额 参数1：小车id 参数2:充值的金额
                setBalance(carId,100);
                break;
            case R.id.money_x:
                //充值余额 参数1：小车id 参数2:充值的金额
                Toast.makeText(setMoneyActivity.this, "还未实现该功能", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //充值余额
    void setBalance(int carId, int money) {
        SetBalanceRequest setBalanceRequest = new SetBalanceRequest(setMoneyActivity.this);
        setBalanceRequest.setMoney(carId, money);
        setBalanceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                if (data.equals("ok")) {
                    Toast.makeText(setMoneyActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
