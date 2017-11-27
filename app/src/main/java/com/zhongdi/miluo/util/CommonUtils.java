package com.zhongdi.miluo.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.zhongdi.miluo.ui.activity.MainActivity;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CommonUtils {
	public static final int NETTYPE_WIFI = 0x01;     // wifi网络
	public static final int NETTYPE_CMWAP = 0x02;    // cmwap网络
	public static final int NETTYPE_CMNET = 0x03;    // cmnet网络
	private static final String TAG = "Utils";
	/**
	 * 检测网络状态
	 */
	public static boolean checkNetWorkStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		// if (null != netInfo && netInfo.isAvailable()) {
		if (null != netInfo && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 将dp值转换为对应的px值
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static float dp2px(Context context, int dpValue) {
		// 计算公式:1dp*像素密度/160 = 实际像素数
		// pixs =dips * (densityDpi/160).
		// dips=(pixs*160)/densityDpi
		
		float scale = context.getResources().getDisplayMetrics().densityDpi;
		return dpValue * scale / 160;
	}
	
	/**
	 * 根据手机的分辨率从dp的单位转成为px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px2(Context context, float dpValue) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		return (int) (dpValue * height / 50 + 0.5f);
	}
	
	public static int dip2px3(Context context, float dpValue) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		return (int) (dpValue * height / 25 + 0.5f);
	}
	
	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static String sub(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	//默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2){
		return div(v1,v2,DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 * @param v2
	 * @return 两个参数的和
	 */
	public static String add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toString();
	}
	// 获得屏幕宽度
	public static int getScreenWidth(Activity activity) {
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		return display.getWidth();
	}

	// 获得屏幕高度
	public static int getScreenHeight(Activity activity) {
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * 判断是否存在SD卡
	 *
	 * @return
	 */
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡或手机内存卡路径
	 *
	 * @return
	 */
	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
			// /data/data/
		}
	}

	/**
	 * 设置文件存储路径
	 *
	 * @return
	 */
	public static String getSaveFilePath() {
		if (hasSDCard()) {
			return getRootFilePath() + "com.milanotech.sdsc/files";
		} else {
			return getRootFilePath() + "com.milanotech.sdsc/files";
		}
	}

	/**
	 * 检测网络状态
	 * @param context
	 * @return true-有网 false-断网
	 */
	public static boolean checkNetState(Context context) {
		boolean netstate = false;
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						netstate = true;
						break;
					}
				}
			}
		}
		return netstate;
	}

	/**
	 * 获取当前网络类型
	 *
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public static int getNetworkType(Context context) {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	/**
	 * 截取字符串
	 *
	 * @param str
	 *            要截取的原字符串
	 * @param len
	 *            需要显示的长度(<font color="red">注意：长度是以byte为单位的，一个汉字是2个byte</font>)
	 * @param symbol
	 *            用于表示省略的信息的字符，如“...”,“>>>”等。
	 * @return 返回处理后的字符串
	 */
	public static String getLimitLengthString(String str, int len, String symbol)
			throws UnsupportedEncodingException {
		if(TextUtils.isEmpty(str)){
			return "";
		}
		int counterOfDoubleByte = 0;
		byte[] b = str.getBytes("GBK");
		if (b.length <= len)
			return str;
		for (int i = 0; i < len; i++) {
			if (b[i] < 0)
				counterOfDoubleByte++;
		}

		if (counterOfDoubleByte % 2 == 0)
			return new String(b, 0, len, "GBK") + symbol;
		else
			return new String(b, 0, len - 1, "GBK") + symbol;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 *
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 描述：获取字符串的长度.
	 *
	 * @param str
	 *            指定的字符串
	 * @return 字符串的长度（中文字符计2个）
	 */
	public static int strLength(String str) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		if (!isEmpty(str)) {
			// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
			for (int i = 0; i < str.length(); i++) {
				// 获取一个字符
				String temp = str.substring(i, i + 1);
				// 判断是否为中文字符
				if (temp.matches(chinese)) {
					// 中文字符长度为2
					valueLength += 2;
				} else {
					// 其他字符长度为1
					valueLength += 1;
				}
			}
		}
		return valueLength;
	}

	/**
	 * 字符串转换为相应的ArrayList
	 */
	public static ArrayList<String> arrayToList(String str) {
		String[] strArray = null;
		ArrayList<String> tempList = new ArrayList<String>();
		if (str != null && (!str.equals(""))) {
			strArray = str.split(";"); // 拆分字符为";" ,然后把结果交给数组strArray
			for (int i = 0; i < strArray.length; i++) {
				tempList.add(strArray[i]);
				Log.i(TAG, "strArray[" + i + "]:" + strArray[i]);
			}
			strArray = null;
		}
		Log.i(TAG, "tempList.size:" + tempList.size());
		return tempList;
	}
	/**
	 * 如果输入法在窗口上已经显示，则隐藏，反之则显示
	 */
	public static void setWindowSoftInput(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 打开软键盘 view为接受软键盘输入的视图，SHOW_FORCED表示强制显示
	 */
	public static void openWindowSoftInput(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 关闭软键盘
	 */
	public static void closeWindowSoftInput(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
	}

	/**
	 * 获取软键盘状态 返回true，输入法打开 false,关闭
	 */
	public static boolean getWindowSoftInput(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.isActive();
	}

	/**
	 * 判断应用程序是否在前台运行
	 */
	public static boolean isRunningForeground(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		String currentPackageName = cn.getPackageName();
		return !TextUtils.isEmpty(currentPackageName)
				&& currentPackageName.equals(context.getPackageName());
	}

	/**
	 * 返回特定字符串之前的字符串
	 *
	 * @param str
	 *            要处理的字符串
	 * @param flag
	 *            特定字符串
	 * @return 截取字符之前的字符串
	 */
	public static String subFrontString(String str, String flag) {
		if (TextUtils.isEmpty(str) || TextUtils.isEmpty(flag)) {
			return str;
		}
		return str.substring(0, str.indexOf(flag));
	}

	/**
	 * 初始化 本地log文件 目录
	 */
	public static String initLogPath(Context context) {
		String path_local = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// 优先保存到SD卡中
			path_local = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "cdsc";
		} else {
			// 如果SD卡不存在，就保存到本应用的目录下
			path_local = context.getFilesDir().getAbsolutePath() + File.separator + "cdsc_log";
		}
		File file = new File(path_local);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path_local;
	}


	/**
	 * Forward to MainActivity when user click on "Back" button
	 * if Current activity is launched from status bar, otherwise close current activity
	 */
	public static void forwardToHomeOrCloseActivity(Activity context) {
		LogUtil.d("checkIfLaunchFromStatusBar = " + CommonUtils.checkIfLaunchFromStatusBar(context));
		if (CommonUtils.checkIfLaunchFromStatusBar(context)) {
			Intent intent = new Intent(context, MainActivity.class);
			context.startActivity(intent);
			context.finish();
			return;
		}
		context.finish();
	}


	/**
	 * Check if Current activity is launched from status bar
	 */
	public static boolean checkIfLaunchFromStatusBar(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> runningTaskInfoList = activityManager.getRunningTasks(1);
		if (runningTaskInfoList != null) {
			for (ActivityManager.RunningTaskInfo info : runningTaskInfoList) {
				if (info.baseActivity != null && info.topActivity != null) {
					String bClassName = info.baseActivity.getClassName();
					String tClassName = info.topActivity.getClassName();
					if (bClassName != null && bClassName.equals(tClassName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = tm.getDeviceId();
			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);
			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}
			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			}
			json.put("device_id", device_id);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDeviceId(Context context) {
		try {
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = tm.getDeviceId();
			if (TextUtils.isEmpty(device_id)) {
				device_id = "";
			}
			return device_id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
