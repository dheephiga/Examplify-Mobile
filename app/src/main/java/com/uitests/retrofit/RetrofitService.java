package com.uitests.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitService {

    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;

    public RetrofitService() {
        initializeRetrofit();
    }

    public void initializeRetrofit() {
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(loggingInterceptor);

        Gson gson = new GsonBuilder()
                .setLenient() // Enable lenient parsing
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.234:9098/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientBuilder.build()) // Set OkHttpClient instance
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
