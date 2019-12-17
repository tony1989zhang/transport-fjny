package com.fjny.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetUtil {
    Handler handler = new Handler(Looper.getMainLooper());

    //成功事件监听
    public interface onLinstener {
        void success(String result);
        void error(String massage);
    }

    //判断网络是否可用
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean inNetworkOk(Context context){
        boolean inNetworkOk = false;
        ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        inNetworkOk = !(conn == null || conn.getActiveNetwork() == null);
        return inNetworkOk;
    }

    private HttpURLConnection getHttpUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        return (HttpURLConnection)url.openConnection();
    }

    private void UrlTop(HttpURLConnection conn) throws ProtocolException {
        conn.setRequestMethod("POST");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
    }


    private void sendParams(HttpURLConnection conn,String params) throws IOException {
        OutputStream os = null;
        OutputStreamWriter osw = null;
        try {
            os = conn.getOutputStream();
            osw = new OutputStreamWriter(os);
            osw.write(params);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os != null) os.close();
            if(osw != null) osw.close();
        }
    }

    private String receiveData(HttpURLConnection conn) throws IOException {
        String result = "";
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(br);
            String line;

            while ((line = br.readLine()) != null){
                if(result.equals("")){
                    result += line;
                }else {
                    result += "\n" + line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null)is.close();
            if(isr != null)isr.close();
            if(br != null)br.close();
        }
        return result;
    }

    //创建连接
    private String postData(String urlString,String params) throws IOException, JSONException {
        String result = "";
        HttpURLConnection conn = getHttpUrl(urlString);
        UrlTop(conn);
        sendParams(conn,params);
        String temp = receiveData(conn);
        JSONObject json = new JSONObject(temp);
        result = json.getString("serverinfo");

        return result;
    }
    //监听线程方法
    private void listenerThread(final String urlString , final String params, final onLinstener linstener){
        new Thread(){
            @Override
            public void run() {
                super.run();
                if (linstener != null){
                    String retuls = "";
                    try {
                        retuls =  postData(urlString,params);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    linstener.success(retuls);
                }
            }
        }.start();
    }
    //定义异步请求
    public void asynRequest(String urlString, String parms, final onLinstener linstener){

       listenerThread(urlString,parms,new NetUtil.onLinstener(){
           @Override
           public void success(final String result) {
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       linstener.success(result);
                   }
               });
           }

           @Override
           public void error(final String massage) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        linstener.error(massage);
                    }
                });
           }
       });
    }
}
