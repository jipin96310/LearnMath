package com.zhaohengsun.learnmath.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Zhaoheng Sun on 2018/4/27.
 */

public class PopWindowUtil {

    private static PopWindowUtil instance;

    private PopupWindow mPopupWindow;

    // 私有化构造方法，变成单例模式
    private PopWindowUtil() {

    }

    // 对外提供一个该类的实例，考虑多线程问题，进行同步操作
    public static PopWindowUtil getInstance() {
        if (instance == null) {
            synchronized (PopWindowUtil.class) {
                if (instance == null) {
                    instance = new PopWindowUtil();
                }
            }
        }
        return instance;
    }


    public PopWindowUtil makePopupWindow(Context cx, View view, View view1, int color) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wmManager=(WindowManager) cx.getSystemService(Context.WINDOW_SERVICE);
        wmManager.getDefaultDisplay().getMetrics(dm);
        int Hight = dm.heightPixels;

        mPopupWindow = new PopupWindow(cx);

        mPopupWindow.setBackgroundDrawable(new ColorDrawable(color));
        view1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        // 设置PopupWindow的大小（宽度和高度）
        mPopupWindow.setWidth(view.getWidth());
        mPopupWindow.setHeight((Hight - view.getBottom()) * 2 / 3);
        // 设置PopupWindow的内容view
        mPopupWindow.setContentView(view1);
        mPopupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
        mPopupWindow.setTouchable(true); // 设置PopupWindow可触摸
        mPopupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

        return instance;
    }


    public PopupWindow showLocationWithAnimation(final Context cx, View view,
                                                 int xOff, int yOff, int anim) {
        // 弹出动画
        mPopupWindow.setAnimationStyle(anim);

        // 弹出PopupWindow时让后面的界面变暗
        WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
        parms.alpha =0.5f;
        ((Activity) cx).getWindow().setAttributes(parms);

        int[] positon = new int[2];
        view.getLocationOnScreen(positon);
        // 弹窗的出现位置，在指定view之下
        mPopupWindow.showAsDropDown(view, positon[0] + xOff, positon[1] + yOff);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // PopupWindow消失后让后面的界面变亮
                WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
                parms.alpha =1.0f;
                ((Activity) cx).getWindow().setAttributes(parms);

                if (mListener != null) {
                    mListener.dissmiss();
                }
            }
        });

        return mPopupWindow;
    }

    private interface OnDissmissListener{

        void dissmiss();

    }

    private OnDissmissListener mListener;

    public void setOnDissmissListener(OnDissmissListener listener) {
        mListener = listener;
    }
}