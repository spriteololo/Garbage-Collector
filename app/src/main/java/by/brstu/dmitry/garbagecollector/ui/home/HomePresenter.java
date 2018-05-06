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

    private ConnectionToRobot connectionToRobot;

    HomePresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        setRobotConnectionListener(this);
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
            getViewState().connectionState(InternetConnectionState.ConnectionType.ROBOT_DISCONNECTING);
        } else {
            getViewState().connectionState(InternetConnectionState.ConnectionType.NO_NETWORK_CONNECTION);
        }
        return 1;
    }

    @Override
    public short onDisconnected() {
        if (isInternetConnection(context)) {
            getViewState().connectionState(InternetConnectionState.ConnectionType.NO_CONNECTION_TO_ROBOT);
        } else {
            getViewState().connectionState(InternetConnectionState.ConnectionType.NO_NETWORK_CONNECTION);
        }

        return 1;
    }

    public void networkStateChanged(final boolean b) {
        if (b) {
            connectionToRobot = new ConnectionToRobot(requestInterface);
            connectionToRobot.execute();
        } else {
            if(connectionToRobot != null) {
                connectionToRobot.cancel(false);
            }
        }
    }

    public void networkStateTry() {
        connection = 0;
        disconnection = 0;
        if(connectionToRobot == null) {
            connectionToRobot = new ConnectionToRobot(requestInterface);
            connectionToRobot.execute();
        }
    }
}
