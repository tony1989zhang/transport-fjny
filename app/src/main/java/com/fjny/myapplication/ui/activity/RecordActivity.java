package com.fjny.myapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fjny.myapplication.R;
import com.fjny.myapplication.dao.RecordDao;
import com.fjny.myapplication.model.RecordInfo;
import com.fjny.myapplication.ui.adapter.RecordsAdapter;

import java.util.List;

public class RecordActivity extends BaseActivity implements View.OnClickListener{
    private ListView lvRecords;         // 记录 列表视图
    private TextView tvTrip;            // 提示 文本框
    private RecordDao recordDao;        // record表访问器

    private List<RecordInfo> records;   // 充值记录类 集合数组
    @Override
    int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    void initView() {
        // 设置顶部原生标题栏标题 已在清单文件中设置
        // RecordActivity.this.setTitle("充值记录");

        /*// 显示顶部原生标题栏返回按钮
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        lvRecords = findViewById(R.id.lv_records);
        tvTrip = findViewById(R.id.tv_no_records);
    }

    @Override
    void initParams() {
        // 使用record表的访问器从数据库获取全部记录
        recordDao = new RecordDao(RecordActivity.this);
        records = recordDao.selectAll();

        // 如有记录则填充ListView并显示，否则隐藏ListView，显示提示TextView
        if (!records.isEmpty()) {
            lvRecords.setAdapter(new RecordsAdapter(RecordActivity.this, records));

            lvRecords.setVisibility(View.VISIBLE);
            tvTrip.setVisibility(View.GONE);
        } else {
            lvRecords.setVisibility(View.GONE);
            tvTrip.setVisibility(View.VISIBLE);
        }
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
        switch (v.getId()) {

        }
    }

}
