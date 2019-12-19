package com.fjny.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fjny.myapplication.R;
import com.fjny.myapplication.config.AppConfig;
import com.fjny.myapplication.factory.SpinnerFactory;
import com.fjny.myapplication.model.CarInfo;
import com.fjny.myapplication.request.BaseRequest;
import com.fjny.myapplication.request.GetBalanceRequest;
import com.fjny.myapplication.request.SetBalanceRequest;
import com.fjny.myapplication.service.CarInfoService;
import com.fjny.myapplication.ui.activity.RecordActivity;
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
    private TextView cxczjl;        // 声明 查询充值记录 按钮

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
        cxczjl = view.findViewById(R.id.cxczjl);

    }

    @Override
    void initData() {
        //初始化ip和服务器类型
        Session.ip = AppConfig.IP_DEFAULT;
        Session.ipFlag = AppConfig.IP_LOCAL;

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
        SpinnerFactory.getSpinner(mContext, cars, spinner, new SpinnerFactory.SpinnerListener() {
            @Override
            public void onSelector(int position) {
                carId = position + 1;
                //获取余额 参数1:小车id
                getBalance(position + 1);
                //获取小车参数
                getXmlData(position);
                //获取小车图片
                carPhoto.setImageResource(carPhots[position]);
            }
        });


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
    }

    //获取余额
    void getBalance(int carId) {
        GetBalanceRequest getBalaceRequest = new GetBalanceRequest(mContext);
        getBalaceRequest.getId(carId);
        getBalaceRequest.connec(new BaseRequest.BaseRequestListener() {
            @Override
            public void onReturn(Object data) {
                carBalance.setText( "￥"+ data.toString() +".00");
            }
        });
    }




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

    //获取Xml获得的数据
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


        //跳转查询充值记录
        cxczjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RecordActivity.class));
            }
        });

    }

    //回到这个页面记得刷新
    @Override
    public void onResume() {
        super.onResume();
        getBalance(carId);
    }
}
