package com.fjny.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.fjny.myapplication.R;
import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.factory.SpinnerFactory;
import com.fjny.myapplication.factory.ToastFactory;
import com.fjny.myapplication.model.CarInfo;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetCarSpeedRequest;
import com.fjny.myapplication.request.SetBalanceRequest;
import com.fjny.myapplication.request.SetCarMoveRequest;
import com.fjny.myapplication.request.getBalanceRequest;
import com.fjny.myapplication.service.CarInfoService;
import com.fjny.myapplication.ui.activity.setMoneyActivity;
import com.fjny.myapplication.utils.Session;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyCarFragment extends BaseFragment {
    private TextView carBalance;
    private String[] cars;
    private Spinner spinner;
    private int [] carPhots;
    private int carId;

    private ImageView carPhoto;//车的图片
    private TextView xhText;//型号
    private TextView fdjText;//发动机
    private TextView cjText;//车架
    private TextView qclxText;//汽车类型
    private TextView pzlxText;//牌照类型
    private TextView cphmText;//车牌号码
    private TextView wzcsText;//违章次数
    private TextView zkfText;//总扣分
    private TextView zfkText;//总罚款

    private Button btnSetMoney;//充值按钮

    private TextView carSpeed;//车速显示
    private int isCarSpeed;//车速
    private Switch doStopCar;//车辆启停
    private Spinner parkRate;//费率选择

    private String soCharged;//这么收费
    private boolean isHourCharged;//计时计费开关

    @Override
    int getLayoutId() {
        return R.layout.my_car_fragment;
    }

    @Override
    void initView(View view) {
        carBalance = view.findViewById(R.id.money);
        spinner = view.findViewById(R.id.xlk);
        carPhoto = view.findViewById(R.id.car_photo);


        xhText = view.findViewById(R.id.xh_text);
        fdjText = view.findViewById(R.id.fdj_text);
        cjText = view.findViewById(R.id.cj_text);
        qclxText = view.findViewById(R.id.qclx_text);
        pzlxText = view.findViewById(R.id.cplx_text);
        cphmText = view.findViewById(R.id.cphm_text);
        wzcsText = view.findViewById(R.id.wzcs_text);
        zkfText = view.findViewById(R.id.zkf_text);
        zfkText = view.findViewById(R.id.zfk_text);

        btnSetMoney = view.findViewById(R.id.czye);

        carSpeed = view.findViewById(R.id.car_speed);
        doStopCar = view.findViewById(R.id.stop_car);
        parkRate = view.findViewById(R.id.park_rate);

    }

    @Override
    void initData() {
        //初始化ip和服务器类型
        Session.ip = AppConfig.IP_DEFAULT;
        Session.ipFlag = AppConfig.IP_LOCAL;

        //初始化关闭计时收费
        isHourCharged = false;

        //下拉框目录
        cars = new String[]{
                "车辆1:",
                "车辆2:",
                "车辆3:",
                "车辆4:"
        };
        //汽车图片
        carPhots = new int[]{
                R.drawable.bmw_x5,
                R.drawable.amg_gt,
                R.drawable.baojun_310,
                R.drawable.toyota_carola
        };
        //下拉框
        SpinnerFactory.getSpinner(mContext, cars, spinner,R.layout.item_spinner, new SpinnerFactory.SpinnerListener() {
            @Override
            public void onSelector(int position) {
                carId = position + 1;
                //获取余额 参数1:小车id
                getBalance(carId);
                //获取小车参数
                getXmlData(position);
                //获取小车图片
                carPhoto.setImageResource(carPhots[position]);
                //获取小车速度
                getCarSpeed(carId);
            }
        });
        spinner.setSelection(0);
        //点击进入充值页面
        btnSetMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), setMoneyActivity.class);
                Bundle bundle = new Bundle();
                bundle.isEmpty();
                bundle.putInt("carId",carId);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //设置汽车动作
        doStopCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String stop = isChecked ? "Stop" : "Move";
                setCarAction(carId,stop);
                if (isChecked){
                    ToastFactory.show(mContext,"停车成功");
                }else {
                    ToastFactory.show(mContext,"启动成功");
                }
            }
        });
        //费率选择
        SpinnerFactory.getSpinner(mContext, new String[]{"按小时收费(每小时10元)", "按次收费(每次50元)"}, parkRate, R.layout.item_spinner2, new SpinnerFactory.SpinnerListener() {
            @Override
            public void onSelector(int position) {
                if (position == 0){
                    soCharged = "hour";
                }else {
                    soCharged = "count";
                }
            }
        });
        //初始化选择按小时收费
        parkRate.setSelection(0);
    }

    //获取余额方法
    void getBalance(int carId) {
        getBalanceRequest getBalaceRequest = new getBalanceRequest(mContext);
        getBalaceRequest.getId(carId);
        getBalaceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                carBalance.setText( "￥"+ data+".00");
            }
        });
    }

    //设置车信息方法
    Handler handler = new  Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CarInfo carInfo = (CarInfo)msg.obj;
            xhText.setText(carInfo.getMake());
            fdjText.setText(carInfo.getEngine());
            cjText.setText(carInfo.getFrame());
            qclxText.setText(carInfo.getType());
            pzlxText.setText(carInfo.getLicenseType());
            cphmText.setText(carInfo.getLicenseNum());
            wzcsText.setText(carInfo.getVioNum());
            zkfText.setText(carInfo.getPoints());
            zfkText.setText(carInfo.getFine());

        }
    };
    //获取Xml获得的数据方法
    public void getXmlData(final int position){
        //获取xml文件只能在线程中
        new Thread(){
            @Override
            public void run() {
                super.run();
                InputStream is = mContext.getResources().openRawResource(R.raw.car_info);
                try {
                    List<CarInfo> list= CarInfoService.getFormXML(is);
                    CarInfo carInfo = list.get(position);
                    Message message = new Message();
                    message.obj = carInfo;
                    handler.sendMessage(message);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    //获取小车当前速度
    public void getCarSpeed(final int carId){
        GetCarSpeedRequest getCarSpeedRequest = new GetCarSpeedRequest(mContext);
        getCarSpeedRequest.getCarId(carId);
        getCarSpeedRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                carSpeed.setText(data+"");
                isCarSpeed = Integer.parseInt(data.toString());
                //判断是否是停车
                if (isCarSpeed == 0){
                    //判断车辆动作
                    doStopCar.setChecked(true);
                    //禁用下拉框
                    parkRate.setEnabled(false);
                    //开始收费
                    if (soCharged.equals("hour")){
                        isHourCharged = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (isHourCharged){
                                    hourCharged();
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }else if (soCharged.equals("count")){
                        countCharged();
                    }
                }else {
                    //判断车辆动作
                    doStopCar.setChecked(false);
                    //启动下拉框
                    parkRate.setEnabled(true);
                    //停止收费
                    isHourCharged = false;
                }
            }
        });
    }

    //设置小车动作
    public void setCarAction(final int carId, String carAction){
        SetCarMoveRequest setCarMoveRequest = new SetCarMoveRequest(mContext);
        setCarMoveRequest.getCarId(carId);
        setCarMoveRequest.getCarAction(carAction);
        setCarMoveRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                if (data.equals("ok")){
                    getCarSpeed(carId);
                }
            }
        });
    }



    //按小时收费
    public void hourCharged(){
        SetBalanceRequest setBalanceRequest = new SetBalanceRequest(mContext);
        setBalanceRequest.setMoney(carId,-10);
        setBalanceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                if (data.toString().equals("ok")){
                    getBalance(carId);
                }
            }
        });
    }
    //按次数收费
    public void countCharged(){
        SetBalanceRequest setBalanceRequest = new SetBalanceRequest(mContext);
        setBalanceRequest.setMoney(carId,-50);
        setBalanceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                if (data.toString().equals("ok")){
                    getBalance(carId);
                }
            }
        });
    }

    //返回页面重新加载余额
    @Override
    public void onResume() {
        super.onResume();
        if (carId != 0){
            getBalance(carId);
            getCarSpeed(carId);
        }
    }
}
