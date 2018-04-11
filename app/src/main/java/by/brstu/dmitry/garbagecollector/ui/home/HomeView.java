package by.brstu.dmitry.garbagecollector.ui.home;

import com.arellomobile.mvp.MvpView;

import by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType;

interface HomeView extends MvpView {
    void connectionState(@ConnectionType int connectingToRobot);
}
