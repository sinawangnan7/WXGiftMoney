package com.wangnan.wxgiftmoney.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * @ClassName: ActivityUtil
 * @Description: Activity跳转工具类
 * @Author wangnan7
 * @Date: 2018/4/16
 */

public class ActivityUtil {

    /**
     * 去授权"辅助服务"
     *
     * @param context
     */
    public static void toAuthAccessibilityService(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }
}
