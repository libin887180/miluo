package com.zhongdi.miluo.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.MyApplication;
import com.zhongdi.miluo.constants.ErrorCode;
import com.zhongdi.miluo.model.MResponse;
import com.zhongdi.miluo.util.AndroidUtil;
import com.zhongdi.miluo.util.AppUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * Created by libin on 2017/8/1.
 */

public class NetRequestUtil {

    public final Gson gson = new Gson();
    private volatile static NetRequestUtil instance;

    /**
     * Double Check 单例模式
     *
     * @return
     */
    public static NetRequestUtil getInstance() {
        if (instance == null) {
            synchronized (NetRequestUtil.class) {
                if (instance == null) {
                    instance = new NetRequestUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 网络请求的接口回调监听器
     */
    public abstract static class NetResponseListener<T> {
        public abstract void onSuccess(T response, int requestCode);

        public abstract void onFailed(T response, int requestCode);

        public abstract void onError(Throwable e);

        public abstract void onFinished();
    }

    /**
     * 文件下载的接口回调监听器
     */
    public abstract class NetDownLoadFileListener extends NetResponseListener<File> {

        public abstract void onSuccess(File response, int requestCode);

        public abstract void onLoading(long current, boolean isDownloading, int requestCode);
    }

    /**
     * 文件上传的接口回调监听
     */
    public abstract class NetUpLoadFileListener extends NetResponseListener {
        public abstract void onLoading(long current, boolean isDownloading, int requestCode);
    }


    /**
     * 异步get请求
     *
     * @param url
     * @param maps
     * @param requestCode
     * @param listener
     * @return
     */
    public Callback.Cancelable get(String url, Map<String, String> maps, final int requestCode, final NetResponseListener listener) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }

        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MResponse mResponse = gson.fromJson(result, getType(listener));//按正常响应解析
                listener.onSuccess(mResponse, requestCode);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
        return cancelable;
    }

    /**
     * 带缓存的异步get请求
     *
     * @param url
     * @param maps
     * @param requestCode
     * @param listener
     * @return
     */
    public Callback.Cancelable getCache(String url, Map<String, String> maps, final int requestCode, final NetResponseListener listener) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }

        // 默认缓存存活时间, 单位:毫秒（如果服务器没有返回有效的max-age或Expires则参考）
        params.setCacheMaxAge(1000 * 60);//设置缓存当这个缓存事件过了的时候, 这时候就会不走这个onCache方法, 直接发起网络请求,
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                ViseLog.e("走的cache" + result);
                MResponse mResponse = gson.fromJson(result, getType(listener));//按正常响应解析
                listener.onSuccess(mResponse, requestCode);

                return true;//这里返回一个true, 就是走了cache就不再发起网络请求了, 返回一个false, 就是不信任缓存数据, 再次发起网络请求
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    ViseLog.w("没走cache" + result);
                    //如果走了cache方法返回了true, 将不再发起网络请求, 这里拿到的result就是null,
                    MResponse mResponse = gson.fromJson(result, getType(listener));//按正常响应解析
                    listener.onSuccess(mResponse, requestCode);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
        return cancelable;
    }

    /**
     * 获取第一级type
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 异步post请求
     *
     * @param url
     * @param maps
     * @param requestCode
     * @param listener
     * @return
     */
    public Callback.Cancelable post(final String url, Map<String, String> maps, final int requestCode, final NetResponseListener listener) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(30*1000);//设置连接超时时间
//        params.addHeader("Content-Type", "application/json");
//        params.setAsJsonContent(true);
        params.setHeader("plam", "andorid");//平台
//        params.setHeader("deviceid", CommonUtils.getDeviceId(MyApplication.getInstance()));//设备号
        params.setHeader("mac", AndroidUtil.getMacAddress(MyApplication.getInstance()));//mac地址
        params.setHeader("versionName", AppUtil.getVersionName(MyApplication.getInstance()));//APP版本名称
        params.setHeader("versionCode", AppUtil.getVersionCode(MyApplication.getInstance()) + "");//APP版本号
        params.setHeader("OSVersionCode", AppUtil.getOSVersionCode() + "");//操作系统版本号
        params.setHeader("OSVersionName", AppUtil.getOSVersionName());//获取操作系统版本名.
        params.setHeader("OSVersionDisplayName", AppUtil.getOSVersionDisplayName());//操作系统版本显示名
        params.setHeader("ModelName", AppUtil.getModelName());//设备名称
        params.setHeader("ProductName", AppUtil.getProductName());//产品名称
        params.setHeader("BrandName", AppUtil.getBrandName());//品牌名称
        params.setHeader("IpAdress", AndroidUtil.getLocalIpAddress(MyApplication.getInstance()));//本地Ip地址
        if (maps != null && !maps.isEmpty()) {
//            for (Map.Entry<String, String> entry : maps.entrySet()) {
//                params.addParameter("requestParameter", entry.getValue());
//            }
//            String requestParameter = AES.encrypt(gson.toJson(maps));
            String requestParameter = gson.toJson(maps);
            ViseLog.e(requestParameter);
            params.setBodyContent(requestParameter);//加入参数
        }

        ViseLog.setTag("Url").i(url);
//        ViseLog.setTag("Headers").w(params.getHeaders());
        ViseLog.setTag("params").v(url+params.getBodyContent());
        Callback.Cancelable post = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String response) {
                if (response != null) {
//                    String result = AES.decrypt(response);
//                    if (!StringUtil.isEmpty(result)) {
                        ViseLog.setTag("response").v(url+response);
                        MResponse mResponse = gson.fromJson(response, getType(listener));//按正常响应解析
                        if (TextUtils.equals(mResponse.getCode(), ErrorCode.SUCCESS)) {
                            listener.onSuccess(mResponse, requestCode);
                        } else {
                            listener.onFailed(mResponse, requestCode);
                        }
//                    }else{
//                        listener.onError(new Throwable("aes decrypt error!"));
//                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

                listener.onFinished();

            }
        });
        return post;
    }

    /**
     * 带缓存的异步post请求
     *
     * @param url
     * @param maps
     * @param requestCode
     * @param listener
     * @return
     */
    public Callback.Cancelable postCache(String url, Map<String, String> maps, final int requestCode, final NetResponseListener listener) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        // 默认缓存存活时间, 单位:毫秒（如果服务器没有返回有效的max-age或Expires则参考）
        params.setCacheMaxAge(1000 * 60);//设置缓存当这个缓存事件过了的时候, 这时候就会不走这个onCache方法, 直接发起网络请求,
        Callback.Cancelable post = x.http().post(params, new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String result) {
                ViseLog.e("走的cache" + result);
                MResponse mResponse = gson.fromJson(result, getType(listener));//按正常响应解析
                listener.onSuccess(mResponse, requestCode);
                return true;//这里返回一个true, 就是走了cache就不再发起网络请求了, 返回一个false, 就是不信任缓存数据, 再次发起网络请求
            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    ViseLog.i(result);
                    MResponse mResponse = gson.fromJson(result, getType(listener));//按正常响应解析
                    listener.onSuccess(mResponse, requestCode);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
        return post;
    }

    /**
     * 文件上传
     *
     * @param url
     * @param maps
     * @param file
     * @param requestCode
     * @param clazz
     * @param listener
     * @return
     */
    public Callback.Cancelable upLoadFile(String url, Map<String, String> maps, Map<String, File> file, final int requestCode, final Class<? extends MResponse> clazz, final NetUpLoadFileListener listener) {
        RequestParams params = new RequestParams(url);
        if (maps != null && !maps.isEmpty()) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (file != null && !maps.isEmpty()) {
            for (Map.Entry<String, File> entry : file.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue().getAbsoluteFile());
            }
        }
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        params.setMultipart(true);//这个是标示上传的文件内容的,
        Callback.Cancelable cancelable = x.http().post(params, new Callback.ProgressCallback<String>() {

            @Override
            public void onSuccess(String result) {
                MResponse mResponse = gson.fromJson(result, clazz);//按正常响应解析
                listener.onSuccess(mResponse, requestCode);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                listener.onLoading(current, isDownloading, requestCode);
            }
        });

        return cancelable;
    }

    /**
     * 文件下载
     *
     * @param url
     * @param filepath
     * @param requestCode
     * @param listener
     */
    public void downLoadFile(String url, String filepath, final int requestCode, final NetDownLoadFileListener listener) {

        RequestParams params = new RequestParams(url);
        params.setAutoRename(true);// 断点续传, 也就是说支持中断之后再继续下载,
        params.setSaveFilePath(filepath);//设置文件保存的路径
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(final File result) {
                listener.onSuccess(result, requestCode);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                listener.onError(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(final long total, final long current, final boolean isDownloading) {
                listener.onLoading(current, isDownloading, requestCode);
            }
        });

    }

}
