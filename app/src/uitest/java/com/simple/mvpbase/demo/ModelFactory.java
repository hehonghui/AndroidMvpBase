package com.simple.mvpbase.demo;

import com.simple.mvpbase.demo.detail.DetailModel;
import com.simple.mvpbase.demo.detail.MockedDetailModel;

/**
 * Created by mrsimple on 13/2/17.
 */

public class ModelFactory {

    public static DetailModel createDetailModel() {
        return new MockedDetailModel() ;
    }
}
