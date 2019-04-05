package com.town.dell.retrofitframe.retrofit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.town.dell.retrofitframe.Bean.PersonalInfo;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell on 2019/4/4.
 */

public class retrofitUtils {

    //Retrofit2基本使用
    //先定义一个PersonalProtocol的java接口
    public interface PersonalProtocol {
        /**
         * 用户信息
         * @param page
         * @return
         */
        @FormUrlEncoded
        @POST("user/personal_list_info")
        Call<Response<PersonalInfo>> getPersonalListInfo(@Field("cur_page") int page);
    }
    /**
     * @FormUrlEncoded注解表示from表单，另外还有@Multipart等注解，如果接口不需要传递参数，那么@FormUrlEncoded以及@Multipart需要去掉，具体原因后面章节会讲到。
     @POST表示post请求，此外还可以使用@GET请求
     * */
    private void requestRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("www.xxxx.com/").build();
        PersonalProtocol personalProtocol = retrofit.create(PersonalProtocol.class);
        Call<Response<PersonalInfo>> call = personalProtocol.getPersonalListInfo(12);
        call.enqueue(new Callback<Response<PersonalInfo>>() {
            @Override
            public void onResponse(Call<Response<PersonalInfo>> call, Response<Response<PersonalInfo>> response) {
                //数据请求成功
            }

            @Override
            public void onFailure(Call<Response<PersonalInfo>> call, Throwable t) {
                //数据请求失败
            }
        });
    }
    /*
    * 首先将域名传入构造一个Retrofit，然后通过retrofit中的create方法传入一个Java接口并得到一个PersonalProtocol(当然PersonalProtocol这个对象是经过处理了的，这个后面会讲到)调用getPersonalListInfo(12)然后返回一个Call，最后这个Call调用了enqueue方法去异步请求http，这就是一个基本的Retrofit的网络请求。Retrofit2中Call接口的默认实现是OkHttpCall，它默认使用OkHttp3作为底层http请求client。
    * */

    //2,retrofit的用法
    OkHttpClient okHttpClient = new OkHttpClient();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("www.xxxx.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .build();
    //addConverterFactory方法中使用了gson去解析json，如果接口返回的json中int为异常数据，我们做了一个简单的转换，给它一个默认值为0：
    public static Gson buildGson() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(int.class, new GsonIntegerDefaultAdapter())
                .create();
        return gson;
    }

    public static class GsonIntegerDefaultAdapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为int类型,如果后台返回""或者null,则返回0
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

}
