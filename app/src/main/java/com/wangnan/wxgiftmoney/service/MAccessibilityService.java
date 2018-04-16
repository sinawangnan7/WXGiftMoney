package com.wangnan.wxgiftmoney.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.wangnan.wxgiftmoney.util.WXViewIDUtil;

import java.util.List;

/**
 * @ClassName: MAccessibilityService
 * @Description: 辅助服务
 * @Author wangnan7
 * @Date: 2018/4/16
 */

public class MAccessibilityService extends AccessibilityService {

    /**
     * 辅助服务名称（包名+"/"+完整类名）
     */
    public static final String SERVCE_NAME = "com.wangnan.wxgiftmoney/com.wangnan.wxgiftmoney.service.MAccessibilityService";

    /**
     * 是否抢自己发送的红包
     */
    public static boolean isGetMyMoney = true;

    /**
     * 辅助服务节点信息
     */
    private AccessibilityNodeInfo mNodeInfo;

    /**
     * 接收到辅助服务事件进行处理
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 1.参数合法性检测
        mNodeInfo = event.getSource();
        if (mNodeInfo == null) {
            return;
        }
        // 2.检查是否有红包视图（针对"群聊界面"）
        List<AccessibilityNodeInfo> wxGiftList = mNodeInfo.findAccessibilityNodeInfosByViewId(WXViewIDUtil.getWXGiftMoneyViewID(this));
        for (int i = 0; i < wxGiftList.size(); i++) {
            AccessibilityNodeInfo accessibilityNodeInfo = wxGiftList.get(i);
            // 群聊场景：自己发红包时，红包未被领取时，微信显示"查看红包"；其他人发红包时，红包未被领取时，微信显示"领取红包"
            if ((isGetMyMoney && accessibilityNodeInfo.getText().equals("查看红包")) || accessibilityNodeInfo.getText().equals("领取红包")) {
                // 对群聊的"红包视图"进行模拟点击
                accessibilityNodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return;
            }
        }
        // 3.检测红包界面是否已打开（针对"红包界面"）
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            List<AccessibilityNodeInfo> wxGiftClickList = mNodeInfo.findAccessibilityNodeInfosByViewId(WXViewIDUtil.getWXGiftMoneyOpenID(this));
            if (wxGiftClickList.size() > 0) { // 是否有开启红包金钱图标按钮
                AccessibilityNodeInfo accessibilityNodeInfo = wxGiftClickList.get(0);
                accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return;
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

}
