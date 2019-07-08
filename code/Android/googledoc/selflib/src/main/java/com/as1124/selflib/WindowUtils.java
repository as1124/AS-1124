package com.as1124.selflib;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 布局窗口相关公用方法
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WindowUtils {

	/**
	 * 获取手机状态栏高度
	 *
	 * @param activity 具体activity
	 * @return Height count by pixel
	 */
	public static int getStatusBarHeight(Activity activity) {
		Resources resources = activity.getResources();
        return resources.getDimensionPixelSize(
                resources.getIdentifier("status_bar_height", "dimen", "android"));
	}

	/**
	 * 获取手机虚拟导航按键栏的高度
	 *
	 * @param activity 具体的activity
	 * @return Height count by pixel
	 */
	public static int getNavigationHeight(Activity activity) {
		Resources resources = activity.getResources();
        return resources.getDimensionPixelSize(
                resources.getIdentifier("navigation_bar_height", "dimen", "android"));
	}

	/**
	 * 设置顶部状态栏颜色
	 *
	 * @param activity 具体的Activity实例
	 * @param color    颜色值
	 */
	public static void setStatusBarColor(Activity activity, int color) {
		if (Build.VERSION.SDK_INT >= 21) {
			activity.getWindow().setStatusBarColor(color);
		}
	}

	/**
     * 设置Activity全屏显示并以沉浸式状态栏展现,
     * Make sure calling must after {@link Activity#setContentView(int)} is called
	 *
	 * @param activity       需要处理的Activity
	 * @param statusBarColor 需要设置的状态栏颜色, 默认值为{@link Color#TRANSPARENT}
	 * @param addTopPadding  是否提升顶部内边距来适应全屏布局
	 */
	public static void fullScreen(Activity activity, int statusBarColor, boolean addTopPadding) {
		Window window = activity.getWindow();
		WindowManager.LayoutParams windowParams = window.getAttributes();

		// Android P以上版本使用Google官方推荐：设置成【缺口刘海屏】模式
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			windowParams.flags = windowParams.flags | WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			// [5.0, ~)之间的版本
			View decorView = window.getDecorView();
			View contentView = decorView.findViewById(android.R.id.content);
			int viewRootColor = Color.WHITE;
			if (contentView != null) {
				View activityRoot = ((ViewGroup) contentView).getChildAt(0);
				Drawable bg = activityRoot.getBackground();
				if (bg != null && bg instanceof ColorDrawable) {
					viewRootColor = ((ColorDrawable) bg).getColor();
				}
			}
			// 两个flag要结合使用，表示让应用的布局内容占用系统状态栏的空间
			if (window.getStatusBarColor() == Color.WHITE || viewRootColor == Color.WHITE
					|| statusBarColor == Color.WHITE) {
				// 白底黑字
				decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			} else {
				decorView.setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			}

			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

			window.setStatusBarColor(statusBarColor);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// [4.4, 5.0)之间的版本
			// 半透明状态栏、半透明导航栏
			windowParams.flags = windowParams.flags | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
		} else {
			// 不支持沉浸式状态栏
			return;
		}
		window.setAttributes(windowParams);

		if (addTopPadding) {
			resolvePadding(activity);
		}
	}

	/**
     * 因为设置了layout_full_screen, 所以徐要将Activity的整体下移StatusBar#height高度以及NavigationBar#height
     * 高度内的显示进行处理，以免遮挡
	 *
	 * @param activity 需要处理的Activity
	 */
	public static void resolvePadding(Activity activity) {
		// 系统提供的UI元素填充容器
		View contentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
		if (contentView != null) {
			View activityRoot = ((ViewGroup) contentView).getChildAt(0);
			if (activityRoot != null) {
				activityRoot.setPadding(activityRoot.getPaddingLeft(),
                        activityRoot.getPaddingTop() + getStatusBarHeight(activity),
                        activityRoot.getPaddingRight(),
						activityRoot.getPaddingBottom());
			}

			// ATTENTION 需要判断是否有虚拟导航栏并且导航栏是显示的
			int navigationHeight = getNavigationHeight(activity);
			Log.i(WindowUtils.class.getSimpleName(), "虚拟导航栏高度=== " + navigationHeight);
		}
	}

	/**
	 * ATTENTION 沉浸式状态栏下设置Light_status_bar的话就变得不是沉浸式状态栏了
	 *
	 * @param activity
	 */
	public static void lightStatusBar(Activity activity) {
		// light_status_bar 设置后,状态信息颜色由白色变成黑色
		View contentView = activity.getWindow().getDecorView();
		contentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
	}

}
