package com.serenegiant.myhome.request;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.serenegiant.myhome.vo.DishParam;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.functions.Func1;


/**
 * Created by yfb on 2016/7/8.
 */
public class HttpMethods {

    public static final String BASE_URL = "http://api.jiaonizuocai.com/v2/dish/";
    public static final String TEST_URL = "http://192.168.0.10:8080/demo/";
//    public static final String SHOT_MESSAGE_URL = "http://comm.zxyq.com.cn:7080/ext/IsendSms/";
//    public static final String VIDEO_SIGN_URL = "http://api.96296edu.com:8097/mobservice/";
//    public static final String UPLOAD_URL = "http://pic.zxyq.com.cn/yepic/info/";
//    //上传视频图片返回时所需要拼接上的地址前缀
//    public static final String PIC_URL = "http://pic.zxyq.com.cn/newimg/ClassVideo/VideoPic/";
//    public static final String AVATER_URL = "http://pic.zxyq.com.cn/yepic/info/";
//    public static final String AVATER_ADD_URL = "http://pic.zxyq.com.cn/newimg/ClassVideo/Header/";
//
//    public static final String url_update_version = "http://schbd.zxyq.com.cn/tcbdVersion/";



    private static final int CONNECT_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 5;
    private static final int WRITE_TIMEOUT = 5;

    private Retrofit retrofit;
    //手动创建一个OkHttpClient并设置超时时间
    OkHttpClient httpClientBuilder = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false).build();

    //构造方法私有
    private HttpMethods() {


        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    //构造方法私有
    private HttpMethods(String url, int mode) {

        if(mode == 1){
            retrofit = new Retrofit.Builder()
                    .client(httpClientBuilder)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(url)
                    .build();
        }else{
            retrofit = new Retrofit.Builder()
                    .client(httpClientBuilder)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(url)
                    .build();
        }
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        public T call(HttpResult<T> httpResult) {
            return httpResult.getData();
        }
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    //获取有参单例
    public static HttpMethods getInstance(String url){
        return new HttpMethods(url,2);
    }

    //获取有参单例
    public static HttpMethods getInstance(String url, int mode){
        return new HttpMethods(url,mode);
    }


    interface IGetDish{
        @FormUrlEncoded
        @POST("info")
        Observable<String> getDish(@Field("code")String code,@Field("appid")String appid,
                                   @Field("cpcode")String cpcode,@Field("device")String device,
                                   @Field("pregInfo")String pregInfo,@Field("t")String t,
                                   @Field("token")String token,@Field("User-Agent") String agent);
    }

    public void getDish(String code,Observer observer){
        IGetDish getDish = retrofit.create(IGetDish.class);
        getDish.getDish(code,"5","602e50548e4d610e2a478b3181efff83",
                "and#Redmi Note 4X#7.0#3.0.0#1080#1920#xiaomi#wifi#505#com.jnzc.shipudaquan#5",
                "","1532073297","","xhapp#and#3.0.0")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    interface IUpdateVersion{
        @GET("queryMaxVersion.action")
        Observable<String> checkVersion();
    }

    public void checkVersion(Observer observer){
        IUpdateVersion updateVersion = retrofit.create(IUpdateVersion.class);
        updateVersion.checkVersion()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    interface IUserInfo{
        @GET("MyServlet")
        Observable<String> getUserInfo(@Query("mac") String mac);
    }

    public void getUserInfo(Observer observer,String mac){
        IUserInfo userInfo = retrofit.create(IUserInfo.class);
        userInfo.getUserInfo(mac)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public Observable<String> getUserInfoNew(String mac){
        IUserInfo userInfo = retrofit.create(IUserInfo.class);
        return userInfo.getUserInfo(mac);
    }


    interface ITiInfo{
        @GET("getGroupVersion.do")
        Observable<String> getTiInfo(@Query("mac") String mac);
    }

    public void getTiInfo(Observer observer,String mac){
        ITiInfo tiInfo = retrofit.create(ITiInfo.class);
        tiInfo.getTiInfo(mac)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    interface IAnalysisVersion{
        @GET("syncPaperVersion.do")
        Observable<String> getAnalysisVersion();
    }

    public Observable<String> getAnalysisVersion(){
        IAnalysisVersion analysisVersion = retrofit.create(IAnalysisVersion.class);
        return analysisVersion.getAnalysisVersion();
    }

    interface ISyncInfo{
        @GET("queryUserPaperForSynchrAll.action")
        Observable<String> syncInfo(@Query("mac") String mac, @Query("synTime") String synTime);
    }

    public Observable<String> syncInfo(String mac, String syncTime){
        ISyncInfo syncInfo = retrofit.create(ISyncInfo.class);
        return syncInfo.syncInfo(mac,syncTime);
    }


    interface IExamListInfo{
        @GET("examlist")
        Observable<String> getList(@Query("PageCount") String pageCount, @Query("currentPage") String currentPage);
    }

    public Observable<String> getExamList(String pageCount, String currentPage){
        IExamListInfo examListInfo = retrofit.create(IExamListInfo.class);
        return examListInfo.getList(pageCount,currentPage);
    }

}
