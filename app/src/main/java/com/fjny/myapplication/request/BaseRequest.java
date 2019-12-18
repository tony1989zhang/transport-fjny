package com.fjny.myapplication.request;

import android.content.Context;

import com.fjny.myapplication.utils.NetUtil;
import com.fjny.myapplication.utils.Session;

public abstract class BaseRequest {
    private NetUtil netUtil;
    private Context mContext;
    private String url;


    //得到数据返回监听器
    public interface BaseRequestListener{
        void onReturn(Object data);
    }

    public BaseRequest(Context context){
        this.mContext = context;
        url = "http://"+ Session.ip +8080+"/transportservice/type/jason/action/";
    }

    //获取id
    abstract String getUrl();
    //获取请求参数
    abstract String getParams();
    //分析处理数据
    abstract Object analyzeResponse(String responseString);

    //定义开始连接方法
    public void connec(final BaseRequestListener listener){
        netUtil = new NetUtil();
        netUtil.asynRequest(url + getUrl(), getParams(), new NetUtil.NetUtilLinstener() {
            @Override
            public void success(String result) {
                //请求成功
                if (listener != null){
                    listener.onReturn(analyzeResponse(result));
                }
            }
            @Override
            public void error(String massage) {
                //请求失败
            }
        });
    }

}
