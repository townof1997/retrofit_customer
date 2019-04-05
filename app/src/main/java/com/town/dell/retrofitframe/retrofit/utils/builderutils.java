package com.town.dell.retrofitframe.retrofit.utils;

/**
 * Created by dell on 2019/4/4.
 */

public class builderutils {
//    public Retrofit build() {
//        if (baseUrl == null) {
//            throw new IllegalStateException("Base URL required.");
//        }
//
//        okhttp3.Call.Factory callFactory = this.callFactory;
//        if (callFactory == null) {
//            callFactory = new OkHttpClient();
//        }
//
//        Executor callbackExecutor = this.callbackExecutor;
//        if (callbackExecutor == null) {
//            callbackExecutor = platform.defaultCallbackExecutor();
//        }
//
//        // Make a defensive copy of the adapters and add the default Call adapter.
//        List<CallAdapter.Factory> adapterFactories = new ArrayList<>(this.adapterFactories);
//        adapterFactories.add(platform.defaultCallAdapterFactory(callbackExecutor));
//
//        // Make a defensive copy of the converters.
//        List<Converter.Factory> converterFactories = new ArrayList<>(this.converterFactories);
//
//        return new Retrofit(callFactory, baseUrl, converterFactories, adapterFactories,
//                callbackExecutor, validateEagerly);
//    }
//
//
//    //OkHttpClient okHttpClient = new OkHttpClient();
//    Retrofit retrofit = new Retrofit.Builder().baseUrl("www.xxxx.com").client(okHttpClient).build();
//
//
//    public <T> T create(final Class<T> service) {
//        Utils.validateServiceInterface(service);
//        if (validateEagerly) {
//            eagerlyValidateMethods(service);
//        }
//        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
//                new InvocationHandler() {
//                    private final Platform platform = Platform.get();
//
//                    @Override public Object invoke(Object proxy, Method method, Object... args)
//                            throws Throwable {
//                        // If the method is a method from Object then defer to normal invocation.
//                        if (method.getDeclaringClass() == Object.class) {
//                            return method.invoke(this, args);
//                        }
//                        if (platform.isDefaultMethod(method)) {
//                            return platform.invokeDefaultMethod(method, service, proxy, args);
//                        }
//                        ServiceMethod serviceMethod = loadServiceMethod(method);
//                        OkHttpCall okHttpCall = new OkHttpCall<>(serviceMethod, args);
//                        return serviceMethod.callAdapter.adapt(okHttpCall);
//                    }
//                });
//    }
//
//
//
//    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<>();
//    ServiceMethod loadServiceMethod(Method method) {
//        ServiceMethod result;
//        synchronized (serviceMethodCache) {
//            result = serviceMethodCache.get(method);
//            if (result == null) {
//                result = new ServiceMethod.Builder(this, method).build();
//                serviceMethodCache.put(method, result);
//            }
//        }
//        return result;
//    }
//
//
//    ServiceMethod(Builder<T> builder) {
//        this.callFactory = builder.retrofit.callFactory();
//        this.callAdapter = builder.callAdapter;
//        this.baseUrl = builder.retrofit.baseUrl();
//        this.responseConverter = builder.responseConverter;
//        this.httpMethod = builder.httpMethod;
//        this.relativeUrl = builder.relativeUrl;
//        this.headers = builder.headers;
//        this.contentType = builder.contentType;
//        this.hasBody = builder.hasBody;
//        this.isFormEncoded = builder.isFormEncoded;
//        this.isMultipart = builder.isMultipart;
//        this.parameterHandlers = builder.parameterHandlers;
//    }
//
//
//
//    public ServiceMethod build() {
//        callAdapter = createCallAdapter();
//        responseType = callAdapter.responseType();
//        if (responseType == Response.class || responseType == okhttp3.Response.class) {
//            throw methodError("'"
//                    + Utils.getRawType(responseType).getName()
//                    + "' is not a valid response body type. Did you mean ResponseBody?");
//        }
//        responseConverter = createResponseConverter();
//
//        for (Annotation annotation : methodAnnotations) {
//            parseMethodAnnotation(annotation);
//        }
//
//        if (httpMethod == null) {
//            throw methodError("HTTP method annotation is required (e.g., @GET, @POST, etc.).");
//        }
//
//        if (!hasBody) {
//            if (isMultipart) {
//                throw methodError(
//                        "Multipart can only be specified on HTTP methods with request body (e.g., @POST).");
//            }
//            if (isFormEncoded) {
//                throw methodError("FormUrlEncoded can only be specified on HTTP methods with "
//                        + "request body (e.g., @POST).");
//            }
//        }
//
//        int parameterCount = parameterAnnotationsArray.length;
//        parameterHandlers = new ParameterHandler<?>[parameterCount];
//        for (int p = 0; p < parameterCount; p++) {
//            Type parameterType = parameterTypes[p];
//            if (Utils.hasUnresolvableType(parameterType)) {
//                throw parameterError(p, "Parameter type must not include a type variable or wildcard: %s",
//                        parameterType);
//            }
//
//            Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
//            if (parameterAnnotations == null) {
//                throw parameterError(p, "No Retrofit annotation found.");
//            }
//
//            parameterHandlers[p] = parseParameter(p, parameterType, parameterAnnotations);
//        }
//
//        //省略部分代码。。。
//
//        return new ServiceMethod<>(this);
//    }
//
//    private CallAdapter<?> createCallAdapter() {
//        Type returnType = method.getGenericReturnType();
//        if (Utils.hasUnresolvableType(returnType)) {
//            throw methodError(
//                    "Method return type must not include a type variable or wildcard: %s", returnType);
//        }
//        if (returnType == void.class) {
//            throw methodError("Service methods cannot return void.");
//        }
//        Annotation[] annotations = method.getAnnotations();
//        try {
//            return retrofit.callAdapter(returnType, annotations);
//        } catch (RuntimeException e) { // Wide exception range because factories are user code.
//            throw methodError(e, "Unable to create call adapter for %s", returnType);
//        }
//    }
//
//
//    public CallAdapter<?> callAdapter(Type returnType, Annotation[] annotations) {
//        return nextCallAdapter(null, returnType, annotations);
//    }
//
//    public CallAdapter<?> nextCallAdapter(CallAdapter.Factory skipPast, Type returnType,
//                                          Annotation[] annotations) {
//        checkNotNull(returnType, "returnType == null");
//        checkNotNull(annotations, "annotations == null");
//
//        int start = adapterFactories.indexOf(skipPast) + 1;
//        for (int i = start, count = adapterFactories.size(); i < count; i++) {
//            CallAdapter<?> adapter = adapterFactories.get(i).get(returnType, annotations, this);
//            if (adapter != null) {
//                return adapter;
//            }
//        }
//
//        //移除处理代码省略。。。
//
//
//    private void parseMethodAnnotation(Annotation annotation) {
//        if (annotation instanceof DELETE) {
//            parseHttpMethodAndPath("DELETE", ((DELETE) annotation).value(), false);
//        } else if (annotation instanceof GET) {
//            parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
//        } else if (annotation instanceof HEAD) {
//            parseHttpMethodAndPath("HEAD", ((HEAD) annotation).value(), false);
//            if (!Void.class.equals(responseType)) {
//                throw methodError("HEAD method must use Void as response type.");
//            }
//        } else if (annotation instanceof PATCH) {
//
//            //省略部分代码。。。
//
//        }
//    }
//
//    private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
//        if (this.httpMethod != null) {
//            throw methodError("Only one HTTP method is allowed. Found: %s and %s.",
//                    this.httpMethod, httpMethod);
//        }
//        this.httpMethod = httpMethod;
//        this.hasBody = hasBody;
//
//        // 省略部分代码。。。
//
//        this.relativeUrl = value;
//        this.relativeUrlParamNames = parsePathParameters(value);
//    }
//
//
//
//    /** Builds an HTTP request from method arguments. */
//    Request toRequest(Object... args) throws IOException {
//        RequestBuilder requestBuilder = new RequestBuilder(httpMethod, baseUrl, relativeUrl, headers,
//                contentType, hasBody, isFormEncoded, isMultipart);
//
//        @SuppressWarnings("unchecked") // It is an error to invoke a method with the wrong arg types.
//                ParameterHandler<Object>[] handlers = (ParameterHandler<Object>[]) parameterHandlers;
//
//        //省略部分代码。。。
//
//        for (int p = 0; p < argumentCount; p++) {
//            handlers[p].apply(requestBuilder, args[p]);
//        }
//        return requestBuilder.build();
//    }
//
//    OkHttpCall okHttpCall = new OkHttpCall<>(serviceMethod, args);
//
//    private okhttp3.Call createRawCall() throws IOException {
//        Request request = serviceMethod.toRequest(args);
//        okhttp3.Call call = serviceMethod.callFactory.newCall(request);
//        if (call == null) {
//            throw new NullPointerException("Call.Factory returned null.");
//        }
//        return call;
//    }
//
//    Response<T> parseResponse(okhttp3.Response rawResponse) throws IOException {
//        ResponseBody rawBody = rawResponse.body();
//
//        // Remove the body's source (the only stateful object) so we can pass the response along.
//        rawResponse = rawResponse.newBuilder()
//                .body(new NoContentResponseBody(rawBody.contentType(), rawBody.contentLength()))
//                .build();
//
//        //处理返回码，代码省略。。。
//
//        ExceptionCatchingRequestBody catchingBody = new ExceptionCatchingRequestBody(rawBody);
//        try {
//            T body = serviceMethod.toResponse(catchingBody);
//            return Response.success(body, rawResponse);
//        } catch (RuntimeException e) {
//            // If the underlying source threw an exception, propagate that rather than indicating it was
//            // a runtime exception.
//            catchingBody.throwIfCaught();
//            throw e;
//        }
//    }
//
//    T toResponse(ResponseBody body) throws IOException {
//        return responseConverter.convert(body);
//    }
//
//    public <T> T create(final Class<T> service) {
//        //省略部分代码。。。。
//        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
//                new InvocationHandler() {
//                    private final Platform platform = Platform.get();
//
//                    @Override public Object invoke(Object proxy, Method method, Object... args)
//                            throws Throwable {
//                        // //省略部分代码。。。。
//                        return serviceMethod.callAdapter.adapt(okHttpCall);
//                    }
//                });
//    }
//
//    @Override
//    public <R> Call<R> adapt(Call<R> call) {
//        return call;
//    }
//
//
//    Call<Response<PersonalInfo>> call = personalProtocol.getPersonalListInfo(12);
//    call.enqueue(new Callback<Response<PersonalInfo>>() {
//        @Override
//        public void onResponse(Call<Response<PersonalInfo>> call, Response<Response<PersonalInfo>> response) {
//            //数据请求成功
//        }
//
//        @Override
//        public void onFailure(Call<Response<PersonalInfo>> call, Throwable t) {
//            //数据请求失败
//        }
//    });
}
