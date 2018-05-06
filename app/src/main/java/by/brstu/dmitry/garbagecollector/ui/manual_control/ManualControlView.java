package by.brstu.dmitry.garbagecollector.ui.manual_control;

import com.arellomobile.mvp.MvpView;

import by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType;
import by.brstu.dmitry.garbagecollector.pojo.RefreshData;

public interface ManualControlView extends MvpView {
    void setLidState(boolean isClosed);

    void setBaseData(RefreshData refreshData);
}
