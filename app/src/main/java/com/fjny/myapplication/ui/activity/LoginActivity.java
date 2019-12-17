package com.fjny.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fjny.myapplication.R;
import com.fjny.myapplication.factory.ToastFactory;

public class LoginActivity extends BaseActivity {
    private EditText etName;        // 姓名 编辑框
    private EditText etContact;     // 联系方式 编辑框
    private CheckBox cbRemember;    // 记住密码 检查框
    private Button btnEnter;        // 进入 按钮


    // 重写父类抽象方法 设置布局ID
    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    void initView() {
        etName = findViewById(R.id.et_name);
        etContact = findViewById(R.id.et_contact);
        cbRemember = findViewById(R.id.cb_remember);
        btnEnter = findViewById(R.id.btn_enter);
    }

    // 重写父类抽象方法 初始化数据
    @Override
    void initParams() {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取编辑框数据并剔除多余空格
                String name = etName.getText().toString().trim();
                String contact = etContact.getText().toString().trim();

                if("".equals(name) || "".equals(contact)){
                    //信息尾款提示
                    ToastFactory.show(LoginActivity.this,"信息不能为空！");
                }else{
                    //shared 定义用户存储数据
                    SharedPreferences shared = getSharedPreferences("userInfo",MODE_PRIVATE);
                    //编辑shared
                    SharedPreferences.Editor editor = shared.edit();
                    //存入姓名 联系方式 || 游客
                    editor.putString("name",name);
                    editor.putString("contact",contact);
                    editor.putBoolean("isGuide",true);

                    //如果选择自动登陆 则不存储为游客
                    if(cbRemember.isChecked()){
                        editor.putBoolean("iGuide",false);
                    }

                    //应用编辑提交
                    editor.apply();

                    //跳转到主页活动
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                    //跳转后销当前页面 防止返回
                    finish();
                }

            }

        });

    }
}
