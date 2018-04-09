package by.brstu.dmitry.garbagecollector.inject;

import javax.inject.Singleton;

import by.brstu.dmitry.garbagecollector.inject.modules.DetailsModule;
import by.brstu.dmitry.garbagecollector.inject.modules.NetworkModule;
import by.brstu.dmitry.garbagecollector.inject.modules.RetrofitModule;
import by.brstu.dmitry.garbagecollector.inject.modules.RootModule;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseInternetMvpPresenter;
import by.brstu.dmitry.garbagecollector.ui.home.HomePresenter;
import by.brstu.dmitry.garbagecollector.ui.home.HomeScreenActivity;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlPresenter;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RetrofitModule.class, DetailsModule.class,
        RootModule.class})
public interface ApplicationComponent {
    void inject(ManualControlFragment manualControlFragment);

    void inject(ManualControlPresenter manualControlPresenter);

    void inject(HomePresenter homePresenter);

    void inject(HomeScreenActivity homeScreenActivity);
}