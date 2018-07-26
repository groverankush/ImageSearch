package com.ankushgrover.imagesearch.app;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 24/7/18.
 */
public class RetroInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);
        return response;
    }
}
