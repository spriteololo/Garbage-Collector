package by.brstu.dmitry.garbagecollector.inject.modules;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import by.brstu.dmitry.garbagecollector.ui.all.base.NetworkStateReceiver;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    private final int timeout;

    public NetworkModule(final int timeout) {
        this.timeout = timeout;
    }

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(timeout, TimeUnit.SECONDS);

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    NetworkStateReceiver getNetworkState() {
        return new NetworkStateReceiver();
    }
}