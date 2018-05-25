package by.brstu.dmitry.garbagecollector.application;

import android.app.Application;

import by.brstu.dmitry.garbagecollector.inject.ApplicationComponent;
import by.brstu.dmitry.garbagecollector.inject.DaggerApplicationComponent;
import by.brstu.dmitry.garbagecollector.inject.modules.NetworkModule;
import by.brstu.dmitry.garbagecollector.inject.modules.RetrofitModule;
import by.brstu.dmitry.garbagecollector.inject.modules.RootModule;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class BaseApplication extends Application {

    private static ApplicationComponent applicationComponent;
    private static Cicerone<Router> cicerone;

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public static Router getRouter() {
        return cicerone.getRouter();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplicationComponent();
        initCicerone();
    }

    private void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .rootModule(new RootModule(getApplicationContext()))
                .retrofitModule(new RetrofitModule(Constants.BaseApi.BASE_URL))
                .networkModule(new NetworkModule(Constants.NetworkingConfig.TIMEOUT))
                .build();
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }
}