package com.example.remarks.net;

import com.example.remarks.BuildConfig;
import com.example.remarks.models.Annotation;
import com.example.remarks.models.Comment;
import com.example.remarks.models.Remark;

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
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(interceptor);
        }

        final OkHttpClient client = okHttpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        service = retrofit.create(RestService.class);
    }

    public static Rest getInstance() {
        if (null == instance) {
            instance = new Rest();
        }
        return instance;
    }

    public Call<ResponseBody> sendRemark(Remark remark) {
        if (remark.getClass() == Comment.class) {
            return service.sendComment((Comment) remark);
        } else if (remark.getClass() == Annotation.class) {
            return service.sendAnnotation((Annotation) remark);
        }

        return null;
    }

    private interface RestService {
        @POST("comment/")
        Call<ResponseBody> sendComment(@Body Comment comment);

        @POST("annotation/")
        Call<ResponseBody> sendAnnotation(@Body Annotation annotation);
    }
}
