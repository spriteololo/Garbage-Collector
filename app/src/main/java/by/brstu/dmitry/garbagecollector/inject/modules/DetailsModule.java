package by.brstu.dmitry.garbagecollector.inject.modules;


import javax.inject.Singleton;

import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DetailsModule {

    @Provides
    @Singleton
    RequestInterface provideLoginInterface(final Retrofit retrofit) {
        return retrofit.create(RequestInterface.class);
    }
}