/**
 *
 */
package com.cc.library.view.util;

import android.content.Context;
import android.widget.Toast;

import com.cc.library.view.base.BaseApplication;


public class ToastUtil {

    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    private static Toast toast;
    private static Context context = BaseApplication.context;

    /**
     * 显示吐司 后出现的吐司立即取代之前的
     *
     * @param msg  消息
     * @param time 时间 0为短时间 否则为长时间
     */
    public static void showToast(String msg, int time) {
        if (null != toast)
            toast.cancel();

        if (time == 0)
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        else
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);

        toast.show();
    }

    /**
     * 显示吐司 后出现的吐司立即取代之前的
     *
     * @param msg  消息字符资源
     * @param time 时间 0为短时间 否则为长时间
     */
    public static void showToast(int msg, int time) {
        if (null != toast)
            toast.cancel();

        if (time == 0)
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        else
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);

        toast.show();
    }

}
