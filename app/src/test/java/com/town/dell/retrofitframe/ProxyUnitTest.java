package com.town.dell.retrofitframe;

import com.town.dell.retrofitframe.Bean.PersonalInfo;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell on 2019/4/5.
 */

public class ProxyUnitTest {
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
    @Test
    public void testRetrofit() throws Exception {       //  动态代理

        PersonalProtocol personalProtocol = (PersonalProtocol) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{PersonalProtocol.class},
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName()); //getPersonalListInfo
                System.out.println(Arrays.toString(args));  //[1]
                return null;
            }
        });
        personalProtocol.getPersonalListInfo(1);
    }

    @Test
    public void testRetrofit2() throws Exception {       //  动态代理

      Retrofit retrofit = new Retrofit.Builder().baseUrl("").build();
      PersonalProtocol personalProtocol = retrofit.create(PersonalProtocol.class);
    }

    @Test
    public void testRetrofit3() throws Exception {       //  动态代理

//        com.town.dell.retrofitframe.api.Retrofit retrofit = new  com.town.dell.retrofitframe.api.Retrofit.Builder().baseUrl("").build();
//        PersonalProtocol personalProtocol = retrofit.create(PersonalProtocol.class);
//        okhttp3.Call call = personalProtocol.getPersonalListInfo(3);
//        okhttp3.Response response = call.execute();
//        System.out.println(response.body().string());

    }

}
