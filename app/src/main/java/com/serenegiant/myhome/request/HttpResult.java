package com.serenegiant.myhome.request;

import com.serenegiant.myhome.vo.DishParam;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yfb on 2016/7/11.
 */
public class HttpResult<T> {

    //用来模仿resultCode和resultMessage
    private String isSuccess;
    private String errCode;
    //用来模仿Data
    private T data;

    public T getData(){
        return data;
    }

    public String getIsSuccess(){

        return isSuccess;
    }

    public String getErrCode(){
        return errCode;
    }
}
