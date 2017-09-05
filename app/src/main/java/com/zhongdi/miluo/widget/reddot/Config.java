package com.zhongdi.miluo.widget.reddot;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

/**
 * Created by kenn on 2017/9/5.
 */

public interface Config {
    int getHeight();

    int getWidth();

    int getDesiredHeight();

    int getDesiredWidth();

    void setMaxWidth(int maxWidth);

    void setMaxHeight(int maxHeight);

    boolean setState(int[] state);

    void draw(@NonNull Canvas canvas);
}