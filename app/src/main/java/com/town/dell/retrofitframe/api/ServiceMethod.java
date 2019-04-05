package com.town.dell.retrofitframe.api;

import com.town.dell.retrofitframe.api.http.DELETE;
import com.town.dell.retrofitframe.api.http.FIELD;
import com.town.dell.retrofitframe.api.http.GET;
import com.town.dell.retrofitframe.api.http.POST;
import com.town.dell.retrofitframe.api.http.PUT;
import com.town.dell.retrofitframe.api.http.QUERY;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by dell on 2019/4/5.
 */

public class ServiceMethod {
    private final HttpUrl baseUrl;
    private final Call.Factory callfactory;
    private String httpMethod;
    private String relativeUrl;
    ParameterHandler[] parameterHandlers;
    private HttpUrl.Builder urlBuilder;
    private FormBody.Builder formBuilder;
    private FormBody formBody;

    public ServiceMethod(Builder builder) {
        baseUrl = builder.retrofit.getBaseUrl();
        callfactory = builder.retrofit.callFactory();
        httpMethod = builder.httpMethod;
        relativeUrl = builder.relativeUrl;
        parameterHandlers = builder.parameterHandlers;
    }

    //返回OKHTTPd的call
    public Call toCall(Object[] args) {
        // 创建OKHttp的Request
        Request.Builder requestBuilder = new Request.Builder();
        //1.1确定地址
        if (null == urlBuilder) {
            urlBuilder = baseUrl.newBuilder(relativeUrl);
        }
        //
        for (int i = 0; i < parameterHandlers.length; i++) {
            parameterHandlers[i].apply(this,String.valueOf(args[i]));
        }
        if(null != formBuilder) {
            formBody = formBuilder.build();
        }
        requestBuilder.url(urlBuilder.build());
        Request request = requestBuilder.method(httpMethod, formBody).build();

        //创建 Call



        return callfactory.newCall(request);
    }

    public void addQueryParams(String name, String value) {
        urlBuilder.addQueryParameter(name,value);


    }

    public void addFormFiled(String name, String value) {
        if(null == formBuilder) {
            formBuilder = new FormBody.Builder();
        }
        formBuilder.add(name,value);
    }

    public static final class Builder {
        private final Retrofit retrofit;
        private Annotation[] methodAnnotations;
        private final Annotation[][] parameterAnnotations;
        private String httpMethod;
        private String relativeUrl;
        ParameterHandler[] parameterHandlers;

        public Builder (Retrofit retrofit,Method method) {
            this.retrofit = retrofit;
            //获得方法注解
            methodAnnotations = method.getAnnotations();
            //方法参数的注解
            parameterAnnotations = method.getParameterAnnotations();


        }
        public ServiceMethod build() {
            //处理方法注解
            for (Annotation methodAnnotation:methodAnnotations) {
                paraseMethodAnnotation(methodAnnotation);
            }
            //处理参数注解
            parameterHandlers = new ParameterHandler[parameterAnnotations.length];
            //遍历参数注解
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] paramterAnnotation = parameterAnnotations[i];
                //遍历一个参数上的所有注解
                for (Annotation annotation: paramterAnnotation) {
                    if(annotation instanceof QUERY) {
                        QUERY query = (QUERY) annotation;
                        String value = query.value();
                        parameterHandlers[i] = new ParameterHandler.QUERY(value);
                    } else if(annotation instanceof FIELD) {
                        FIELD field = (FIELD)annotation;
                        String value = field.value();
                        parameterHandlers[i] = new ParameterHandler.FIELD(value);
                    } else if(annotation instanceof PUT) {
                        PUT put = (PUT)annotation;
                        String value = put.value();
                        parameterHandlers[i] = new ParameterHandler.PUT(value);
                    } else if(annotation instanceof GET) {
                        GET get = (GET)annotation;
                        String value = get.value();
                        parameterHandlers[i] = new ParameterHandler.GET(value);
                    } else if(annotation instanceof POST) {
                        POST post = (POST)annotation;
                        String value = post.value();
                        parameterHandlers[i] = new ParameterHandler.POST(value);
                    } else if(annotation instanceof DELETE) {
                        DELETE delete = (DELETE)annotation;
                        String value = delete.value();
                        parameterHandlers[i] = new ParameterHandler.DELETE(value);
                    }
                }
            }

            return new ServiceMethod(this);
        }

        private void paraseMethodAnnotation(Annotation methodAnnotation) {
            if (methodAnnotation instanceof GET) {
                httpMethod = "GET";
                GET get = (GET) methodAnnotation;
                relativeUrl = get.value();
            } else if(methodAnnotation instanceof POST) {
                httpMethod = "POST";
                POST post = (POST) methodAnnotation;
                relativeUrl = post.value();
            } else if(methodAnnotation instanceof DELETE) {
                httpMethod = "DELETE";
                DELETE delete = (DELETE) methodAnnotation;
                relativeUrl = delete.value();
            } else if(methodAnnotation instanceof PUT) {
                httpMethod = "PUT";
                PUT put = (PUT) methodAnnotation;
                relativeUrl = put.value();
            }

        }
    }
}
