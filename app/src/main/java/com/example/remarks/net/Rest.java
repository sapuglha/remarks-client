package com.example.remarks.net;

import android.os.Build;

import com.example.remarks.BuildConfig;
import com.example.remarks.models.Annotation;
import com.example.remarks.models.Comment;
import com.ryanharter.auto.value.moshi.AutoValueMoshiAdapterFactory;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Rest {
    private static Rest instance;
    private RestService service;

    private Rest() {
        final Moshi moshi = new Moshi.Builder()
                .add(new AutoValueMoshiAdapterFactory())
                .build();


        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okhttpBuilder.addInterceptor(interceptor);
        }

        final OkHttpClient client = okhttpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        service = retrofit.create(RestService.class);
    }

    public static Rest getInstance() {
        if (null == instance) {
            instance = new Rest();
        }
        return instance;
    }

    public Call<ResponseBody> sendComment(Comment comment) {
        return service.sendComment(comment);
    }

    public Call<ResponseBody> sendAnnotation(Annotation annotation) {
        return service.sendAnnotation(annotation);
    }

    private interface RestService {
        @POST("comment/")
        Call<ResponseBody> sendComment(@Body Comment comment);

        @POST("annotation/")
        Call<ResponseBody> sendAnnotation(@Body Annotation annotation);
    }
}
