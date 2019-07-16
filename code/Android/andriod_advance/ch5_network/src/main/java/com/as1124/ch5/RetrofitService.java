package com.as1124.ch5;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 测试Retrofit框架的RESTful使用
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class RetrofitService {

    @GET("")
    public void getService() {
        // 具体的请求URL是 baseURL + 方法注解的URI形成的
        Retrofit retrofit = new Retrofit.Builder().baseUrl("").build();
//        retrofit.
    }

    @POST
    public void postService() {

    }
}
