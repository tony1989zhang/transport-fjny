package com.fjny.myapplication.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.fjny.myapplication.R;
import com.fjny.myapplication.ui.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ChengshigonjiaoFragment extends BaseFragment {
    private ViewPager pager;
    private TabLayout tab;
    private List<String> titles;
    private List<BaseFragment> fragments;
    private ViewPagerAdapter adapter;

    @Override
    int getLayoutId() {
        return R.layout.chengshigojiao_fragment;
    }

    @Override
    void initView(View view) {
        tab =view.findViewById(R.id.tab);
        pager =view.findViewById(R.id.pager);
    }

    @Override
    void initData() {
        titles =new ArrayList<>();
        titles.add("1号站台");
        titles.add("2号站台");
        fragments =new ArrayList<BaseFragment>();
        fragments.add(new BusStationOneFragment());
        fragments.add(new BusStationTwoFragment());
        FragmentManager fm =getChildFragmentManager();
        adapter =new ViewPagerAdapter(fm,titles,fragments);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
    }
}
