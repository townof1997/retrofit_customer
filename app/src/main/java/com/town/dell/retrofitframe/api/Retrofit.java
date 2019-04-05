package com.town.dell.retrofitframe.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by dell on 2019/4/5.
 */

public class Retrofit {
    private HttpUrl baseUrl;
    private Call.Factory callFactory;

    ConcurrentHashMap<Method, ServiceMethod> serivceMethodCache = new ConcurrentHashMap<>();

    public Retrofit(Builder builder) {
        baseUrl = builder.baseUrl;
        callFactory = builder.callFactory;
    }
    public HttpUrl getBaseUrl(){
        return baseUrl;
    }
    public Call.Factory callFactory(){
        return callFactory;
    }
    public <T> T create(final Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
            new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //  实现网络请求

                    //  采集数据
                    ServiceMethod serviceMethod = loadServiceMethod(method);
                    return serviceMethod.toCall(args);
                }
            });
    }

    private ServiceMethod loadServiceMethod(Method method) {

        ServiceMethod serviceMethod = serivceMethodCache.get(method);

        if(null == serviceMethod) {
            serviceMethod = new ServiceMethod.Builder(this,method).build();
            serivceMethodCache.putIfAbsent(method, serviceMethod);
        }
        return serviceMethod;
    }

    //构建这模式
    public static final class Builder {
        private HttpUrl baseUrl;
        private Call.Factory callFactory;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder callFactory(Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        public Retrofit build() {
            if(null == baseUrl) {
                throw new IllegalArgumentException("BaseUrl not set");
            }
            if (null == callFactory) {
                callFactory = new OkHttpClient();
            }
            return new Retrofit(this);
        }
    }

}
