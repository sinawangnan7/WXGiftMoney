package com.wangnan.wxgiftmoney.util;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * @ClassName: PermissionUtil
 * @Description: 权限检查工具类
 * @Author wangnan7
 * @Date: 2018/4/16
 */

public class PermissionUtil {

    /**
     * 获取指定AccessibilityService的授权状态（服务是否已授权开启）
     *
     * @param context
     * @param serviceName 服务名称
     */
    public static boolean getServiceState(Context context, String serviceName) {
        int enable = 0;
        try {
            // 是否允许辅助服务运行（当前应用程序是否有辅助服务，有辅助服务返回1，没有辅助服务返回0）
            enable = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        if (enable == 1) {
            // 获取所有可使用的服务（会以String形式返回，多个服务用":"分割）
            String accessibilityString = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            // 创建字符串分割器（用":"分割字符串）
            TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
            splitter.setString(accessibilityString);
            // 遍历服务名称（查是否含有名为serviceName辅助服务）
            while (splitter.hasNext()) {
                String accessibilityService = splitter.next();
                if (accessibilityService.equals(serviceName)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
}
