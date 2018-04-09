package by.brstu.dmitry.garbagecollector.ui.home;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseInternetMvpPresenter;

@InjectViewState
public class HomePresenter extends BaseInternetMvpPresenter<HomeView> implements BaseInternetMvpPresenter.RobotConnectionListener {

    @Inject
    RequestInterface requestInterface;

    @Inject
    Context context;

    public HomePresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void onConnecting() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnecting() {

    }

    @Override
    public void onDisconnected() {

    }

    public void networkStateChanged(final boolean b) {
        connectionToRobotListener(this, requestInterface, b);
    }
}
