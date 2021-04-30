package com.zyblue.fastim.common.util;

import com.zyblue.fastim.common.codec.Invocation;
import com.zyblue.fastim.common.codec.InvocationType;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

public class HttpUtil {

    public static String doPost(String url, Object body) throws Exception{
        OkHttpClient client = new OkHttpClient.Builder()
                // 连接超时2秒
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                // 读取超时2秒
                .readTimeout(1000, TimeUnit.MILLISECONDS)
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                JacksonUtils.obj2json(new Invocation<>(InvocationType.HTTP1.getVal(), body)));

        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        if(responseBody == null){
            throw new Exception("response is null");
        }
        return responseBody.string();
    }
}
