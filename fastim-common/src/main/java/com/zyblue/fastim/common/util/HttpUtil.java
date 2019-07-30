package com.zyblue.fastim.common.util;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpUtil {

    public static Response doPost(String url, Object body) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), com.alibaba.fastjson.JSON.toJSONString(body));

        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            throw new NullPointerException(e.toString());
        }
    }
}
