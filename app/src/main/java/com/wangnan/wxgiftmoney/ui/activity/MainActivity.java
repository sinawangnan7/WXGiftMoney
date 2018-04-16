package com.wangnan.wxgiftmoney.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wangnan.wxgiftmoney.R;
import com.wangnan.wxgiftmoney.service.MAccessibilityService;
import com.wangnan.wxgiftmoney.util.ActivityUtil;
import com.wangnan.wxgiftmoney.util.PermissionUtil;

public class MainActivity extends AppCompatActivity {

    /**
     * "自动抢红包"开关入口
     */
    private View mEntranceV;

    /**
     * "自动抢红包"开关状态视图
     */
    private TextView mSwitchStatusTV;

    /**
     * 选项1描述
     */
    private TextView mSelector1TV;

    /**
     * 选项1开关
     */
    private SwitchCompat mSelector1SC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewListener();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mEntranceV = findViewById(R.id.ll_entrance);
        mSwitchStatusTV = findViewById(R.id.tv_status);
        mSelector1TV = findViewById(R.id.tv_selector1);
        mSelector1SC = findViewById(R.id.sc_selector1);
    }

    /**
     * 初始化视图监听器
     */
    private void initViewListener() {
        // 为"自动抢红包开关按钮"设置点击监听
        mEntranceV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转辅助服务
                ActivityUtil.toAuthAccessibilityService(MainActivity.this);
            }
        });
        // 设置"选项1"开关选择监听器
        mSelector1SC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MAccessibilityService.isGetMyMoney = isChecked;
                updateUI();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mSelector1SC.setChecked(MAccessibilityService.isGetMyMoney);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * 更新视图UI
     */
    private void updateUI() {
        // 设置"辅助服务开启状态"提示信息
        boolean serviceState = PermissionUtil.getServiceState(this, MAccessibilityService.SERVCE_NAME);
        if (serviceState) {
            mSwitchStatusTV.setText(Html.fromHtml(getString(R.string.string_service_start)));
        } else {
            mSwitchStatusTV.setText(Html.fromHtml(getString(R.string.string_service_stop)));
        }
        // 是否抢自己发送的红包
        boolean isGetMyMoney = MAccessibilityService.isGetMyMoney;
        if (isGetMyMoney) {
            mSelector1TV.setText(Html.fromHtml(getString(R.string.app_function_selector1_start)));
        } else {
            mSelector1TV.setText(Html.fromHtml(getString(R.string.app_function_selector1_close)));
        }
    }
}
