package com.zhongdi.miluo.view;

import com.zhongdi.miluo.base.BaseView;
import com.zhongdi.miluo.model.SearchFund;

import java.util.List;


/**
 * Created by isfaaghyth on 6/17/17.
 */

public interface SearchView extends BaseView {
    void onSearchSuccess(List<SearchFund> body);
    void setListener();

    void onhotSearchSuccess(List<SearchFund> body);
}
