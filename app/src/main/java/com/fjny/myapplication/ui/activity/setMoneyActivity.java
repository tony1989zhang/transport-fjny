package com.fjny.myapplication.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fjny.myapplication.R;
import com.fjny.myapplication.dao.RecordDao;
import com.fjny.myapplication.factory.DialogFactory;
import com.fjny.myapplication.factory.EditDialogFactory;
import com.fjny.myapplication.factory.ToastFactory;
import com.fjny.myapplication.model.RecordInfo;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.SetBalanceRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class setMoneyActivity extends BaseActivity implements View.OnClickListener {
    private TextView carId_text;
    int carId;
    private Button money20, money50, money100, moneyX;
    private RecordDao recordDao;    // 充值记录 数据库访问器



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
        recordDao = new RecordDao(setMoneyActivity.this);
    }

    // 重写顶部标题栏按钮监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 返回按钮被单击事件
            case android.R.id.home:
                // 销毁当前Activity
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                EditDialogFactory.show(setMoneyActivity.this, "自定义充值", new EditDialogFactory.OnListener() {
                    @Override
                    public void onAfter(String input) {
                        int moneys = Integer.parseInt(input);
                        if (moneys>999||moneys<1){
                            ToastFactory.show(setMoneyActivity.this,"一次只能充值1~999的元");
                        }else {
                            try {
                                setBalance(carId,moneys);
                            }catch (Exception e){
                                ToastFactory.show(setMoneyActivity.this,"亲输入整数");
                            }
                        }
                    }
                });
                break;

        }
    }

    //充值余额
    void setBalance(final int carId, final int money) {
        SetBalanceRequest setBalanceRequest = new SetBalanceRequest(setMoneyActivity.this);
        setBalanceRequest.setMoney(carId, money);
        setBalanceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {

                if (data.equals("ok")) {
                    DialogFactory.showDialog(setMoneyActivity.this,"充值","确定要给"+carId+"小车"+"充值"+money+"元吗",new DialogFactory.OnLitener(){
                        @Override
                        public void onAfter() {

                            // 初始化充值记录信息类
                            RecordInfo record = new RecordInfo();
                            // 记录充值车辆
                            record.setCarId(carId);
                            // 记录充值金额
                            record.setMoney(money);

                            // 使用shared获得充值者并记录
                            SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
                            record.setUserName(shared.getString("name", "name"));

                            // 获取当前时间
                            Date currentTime = new Date();
                            // 设置时间格式
                            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:mm");
                            // 记录充值时间
                            record.setChargeDate(formatter.format(currentTime));

                            // 使用充值记录数据库访问器
                            // 增加一条充值记录数据到数据库表
                            recordDao.insert(record);

                            ToastFactory.show(setMoneyActivity.this, "充值成功", true);

                        // 充值结束后销毁当前页面
                        finish();
                            /*Toast.makeText(setMoneyActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                            finish();*/

                        }
                    });
                } else {
                    ToastFactory.show(setMoneyActivity.this, "充值失败", true);
                }
            }
        });
    }
}
