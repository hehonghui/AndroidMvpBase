package com.simple.mvpbase.demo.detail;

import android.util.Log;

/**
 * Created by mrsimple on 13/2/17.
 */

public class MockedDetailModel implements DetailModel {

    @Override
    public void fetchDetail(DetailListener listener) {
        Log.e("", "### MockedDetailModel  fetchDetail ") ;
        if ( listener != null ) {
            listener.onFetched();
        }
    }
}
