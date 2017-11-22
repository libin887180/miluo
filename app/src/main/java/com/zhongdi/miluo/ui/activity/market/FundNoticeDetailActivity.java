package com.zhongdi.miluo.ui.activity.market;

import android.graphics.Canvas;
import android.os.Bundle;

import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;
import com.vise.log.ViseLog;
import com.zhongdi.miluo.R;
import com.zhongdi.miluo.base.BaseActivity2;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * bananlar详情页面
 */
public class FundNoticeDetailActivity extends BaseActivity2 implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener {

    @BindView(R.id.pdfView)
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url = "";
        //String titleName = "";
        if (getIntent().getExtras() != null) {
            url = getIntent().getStringExtra("url");
            ViseLog.d(url);
            //titleName = getIntent().getStringExtra("title");
        }
        displayFromFile1(url, url.substring(url.lastIndexOf("=",url.length()-1))+".pdf");
    }

    /**
     * 获取打开网络的pdf文件
     *
     * @param fileUrl
     * @param fileName
     */
    private void displayFromFile1(String fileUrl, String fileName) {
        try{
            pdfView.fileFromLocalStorage(this, this, this, fileUrl, fileName);   //设置pdf文件地址
        }catch (Exception e){
            showToast("文件格式错误");
        }

    }

    /**
     * 翻页回调
     *
     * @param page
     * @param pageCount
     */
    @Override
    public void onPageChanged(int page, int pageCount) {
//        Toast.makeText(mContext, "page= " + page +
//                " pageCount= " + pageCount, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载完成回调
     *
     * @param nbPages 总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
//        Toast.makeText(MainActivity.this, "加载完成" + nbPages, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
        // Toast.makeText( MainActivity.this ,  "pageWidth= " + pageWidth + "
        // pageHeight= " + pageHeight + " displayedPage="  + displayedPage , Toast.LENGTH_SHORT).show();
    }
}
