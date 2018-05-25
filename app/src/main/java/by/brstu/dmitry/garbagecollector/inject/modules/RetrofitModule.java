package by.brstu.dmitry.garbagecollector.inject.modules;

import android.support.annotation.NonNull;

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
        return new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}