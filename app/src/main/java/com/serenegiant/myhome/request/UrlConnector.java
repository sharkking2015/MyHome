package com.serenegiant.myhome.request;

import android.util.Log;

import com.serenegiant.myhome.vo.DishParam;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UrlConnector {
    public static void test(){
        HttpMethods.getInstance(HttpMethods.BASE_URL,2).getDish("5473688", new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.i("postresult",s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("postresult",e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i("postresult","complete");
            }
        });
    }
}
