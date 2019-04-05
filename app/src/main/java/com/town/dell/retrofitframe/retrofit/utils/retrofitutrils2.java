package com.town.dell.retrofitframe.retrofit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.town.dell.retrofitframe.Bean.PersonalInfo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by dell on 2019/4/4.
 */

public class retrofitutrils2 {

    public interface PersonalProtocol {
        /**
         * 用户信息
         * @param page
         * @return
         */
        @FormUrlEncoded
        @POST("user/personal_list_info")
        rx.Observable<PersonalInfo> getPersonalListInfo(@Field("cur_page") int page);
//    Call<Response<PersonalInfo>> getPersonalListInfo(@Field("cur_page") int page);
    }



    public static Gson buildGson() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(int.class, new retrofitUtils.GsonIntegerDefaultAdapter())
                .create();
        return gson;
    }

    public void httpMethod() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("www.xxxx.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //addCallAdapterFactory方法中使用了RxJavaCallAdapterFactory,网络请求也需要修改：
        PersonalProtocol personalProtocol = retrofit.create(PersonalProtocol.class);
//        rx.Observable<PersonalInfo> observable = personalProtocol.getPersonalListInfo(12);
        rx.Observable<PersonalInfo> observable  = personalProtocol.getPersonalListInfo(12);
//        rx.Observable<PersonalInfo> observable  = personalProtocol.getPersonalListInfo(12);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())    ////最后在主线程中执行
                .subscribe(new Subscriber<PersonalInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //请求失败
                    }

                    @Override
                    public void onNext(PersonalInfo personalInfo) {
                        //请求成功
                    }
                });
    }

}
