package com.zhongdi.miluo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;

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

        isInit=true;
    }
    private void initLog() {

        ViseLog.getLogConfig()
                .configAllowLog(false)//是否输出日志
                .configShowBorders(true)//是否排版显示
                .configTagPrefix("ViseLog")//设置标签前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//个性化设置标签，默认显示包名
                .configLevel(Log.VERBOSE);//设置日志最小输出级别，默认Log.VERBOSE
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }
    private void initNet() {
        x.Ext.init(getApplication());

    }

    public static void start(MyApplication myApplication) {

        Intent intent = new Intent(myApplication,MyIntentService.class);
        myApplication.startService(intent);
    }
}
