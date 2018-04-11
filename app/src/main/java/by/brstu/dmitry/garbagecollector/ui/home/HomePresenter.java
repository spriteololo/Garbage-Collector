package by.brstu.dmitry.garbagecollector.ui.home;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.InternetConnectionState;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseInternetMvpPresenter;


@InjectViewState
public class HomePresenter extends BaseInternetMvpPresenter<HomeView> implements BaseInternetMvpPresenter.RobotConnectionListener {

    @Inject
    RequestInterface requestInterface;

    @Inject
    Context context;

    HomePresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        setListener(this);
        ConnectionToRobot connectionToRobot = new ConnectionToRobot(requestInterface);
        connectionToRobot.execute();
    }

    @Override
    public short onConnecting() {
        getViewState().connectionState(InternetConnectionState.ConnectionType.CONNECTING_TO_ROBOT);
        return 1;
    }

    @Override
    public short onConnected() {
        getViewState().connectionState(InternetConnectionState.ConnectionType.CONNECTED_TO_ROBOT);
        return 1;
    }

    @Override
    public short onDisconnecting() {
        if (isInternetConnection(context)) {
            getViewState().connectionState(InternetConnectionState.ConnectionType.NO_NETWORK_CONNECTION);
        } else {
            getViewState().connectionState(InternetConnectionState.ConnectionType.ROBOT_DISCONNECTING);
        }
        return 1;
    }

    @Override
    public short onDisconnected() {
        if (isInternetConnection(context)) {
            getViewState().connectionState(InternetConnectionState.ConnectionType.NO_NETWORK_CONNECTION);
        } else {
            getViewState().connectionState(InternetConnectionState.ConnectionType.NO_CONNECTION_TO_ROBOT);
        }

        return 1;
    }

    public void networkStateChanged(final boolean b) {
        networkState(b);
    }
}
