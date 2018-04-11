package by.brstu.dmitry.garbagecollector.inject.modules;

import android.support.annotation.NonNull;


import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @NonNull
    private final String BaseUrl;

    public RetrofitModule(@NonNull final String BaseUrl) {
        this.BaseUrl = BaseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@NonNull final OkHttpClient okHttpClient) {
        final OkHttpClient.Builder okHttpBuilder = okHttpClient.newBuilder();
        okHttpBuilder.connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}