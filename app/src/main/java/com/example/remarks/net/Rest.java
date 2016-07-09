package com.example.remarks.net;

import com.example.remarks.BuildConfig;
import com.example.remarks.models.Comment;
import com.ryanharter.auto.value.moshi.AutoValueMoshiAdapterFactory;
import com.squareup.moshi.Moshi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Rest {
    RestService service;

    public Rest() {
        final Moshi moshi = new Moshi.Builder()
                .add(new AutoValueMoshiAdapterFactory())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        service = retrofit.create(RestService.class);
    }

    public Call<ResponseBody> send(Comment comment) {
        return service.sendComment(comment);
    }

    private interface RestService {
        @POST("comment/")
        Call<ResponseBody> sendComment(@Body Comment user);
    }
}
