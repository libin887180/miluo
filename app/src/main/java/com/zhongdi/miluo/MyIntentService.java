package com.zhongdi.miluo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.xutils.x;

/**
 * Created by kenn on 2017/9/17.
 */
/**
 * 将MyApplication中onCreate方法内容耗时的初始化工作移动到该类中
 */
public class MyIntentService extends IntentService {

    // 问题：由于将NoHttp的初始化工作移动到了子线程，当主线程使用NoHttp发现没有初始化完成，报异常了。

    // 方案一：使用boolean值进行初始化工作的标记，如果完成boolean为true，可以在使用该工具的地方每隔一个时间段判断一下。
    // 方案二：当初始化工作完成后，发出一个通知，如果有观察者，则进行后续工作的处理

    public static boolean isInit=false;// 标记是否初始化完成
    public MyIntentService() {
        super("init");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("初始化","**************");
        TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
        TwinklingRefreshLayout.setDefaultFooter(BallPulseView.class.getName());
        initLog();
        initNet();

    }
    private void initLog() {
        Logger.init("miluo")
                .methodCount(2) // 方法栈打印的个数，默认是 2
//                .hideThreadInfo() // // 隐藏线程信息，默认显示
                .methodOffset(2) // 设置调用堆栈的函数偏移值，默认是 0
                .logLevel(LogLevel.FULL);
//                .logAdapter(new AndroidLogAdapter()); // 自定义一个打印适配器
        isInit=true;
    }
    private void initNet() {
        x.Ext.init(getApplication());

    }

    public static void start(MyApplication myApplication) {

        Intent intent = new Intent(myApplication,MyIntentService.class);
        myApplication.startService(intent);
    }
}
