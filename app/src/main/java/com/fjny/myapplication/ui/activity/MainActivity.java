package com.fjny.myapplication.ui.activity;


import com.fjny.myapplication.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fjny.myapplication.factory.ToastFactory;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;

    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.my_car,R.id.chuxinghuanjing,R.id.daoluzhuangkuang,R.id.chengshigongjiao,
                R.id.chuangyimokuai,R.id.guanyu,R.id.lianxikefu
        )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    void initParams() {
       View view = navigationView.getHeaderView(0);
        TextView textView1 = (TextView)view.findViewById(R.id.name);
        TextView textView2 = (TextView)view.findViewById(R.id.contact);
        textView1.setText("姓名");
        textView2.setText("练习方式");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //第一个选项
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this,"第一个",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //第二个选项 用户退出
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 从 shared 中获取用户信息
                SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
                // 编辑 shared 中的 isGuide 字段
                SharedPreferences.Editor editor = shared.edit();
                // isGuide = true 下次启动时需要进入重新登录界面
                editor.putBoolean("isGuide", true);
                // apply 应用修改
                editor.apply();

                // 启动登录界面
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

                // 销毁当前界面 防止返回
                finish();
                //提示
                ToastFactory.show(MainActivity.this,"用户退出，返回登陆页面");
                return true;
            }
        });

        //第三个选项 退出程序
        menu.getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                //淡出动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
