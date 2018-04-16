package com.wangnan.wxgiftmoney.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @ClassName: WXViewIDUtil
 * @Description: 微信视图ID工具类
 * @Author wangnan7
 * @Date: 2018/4/16
 */

public class WXViewIDUtil {

    /**
     * 获取微信群聊界面 - 红包视图 - 查看红包/领取红包(TextView)视图ID
     *
     * （注：微信每次发版都会修改红包视图ID, 该方法用于区分微信版本获取视图ID，最低兼容至微信6.6.5版本）
     */
    public static String getWXGiftMoneyViewID(Context context) {
        switch (getWXVersionCode(context)) {
            case "6.6.5":
                return "com.tencent.mm:id/aeb";
            case "6.6.6":
                return "com.tencent.mm:id/ae_";
            default:
                return "com.tencent.mm:id/aeb";
        }
    }

    /**
     * 获取微信红包界面 - 金钱图标视图ID
     *
     * （注：微信每次发版都会修改金钱图标视图ID, 该方法用于区分微信版本获取视图ID，最低兼容至微信6.6.5版本）
     */
    public static String getWXGiftMoneyOpenID(Context context) {
        switch (getWXVersionCode(context)) {
            case "6.6.5":
                return "com.tencent.mm:id/c4q";
            case "6.6.6":
                return "com.tencent.mm:id/c31";
            default:
                return "com.tencent.mm:id/c4q";
        }
    }

    /**
     * 获取微信版本名称
     */
    public static String getWXVersionCode(Context context) {
        String wxPackageName = "com.tencent.mm";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(wxPackageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
