package by.brstu.dmitry.garbagecollector.ui.object_following;

import com.arellomobile.mvp.MvpView;

import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
import okhttp3.ResponseBody;

interface ObjectFollowingView extends MvpView{
    void setSensorData(final boolean isForwardSensor, RefreshData refreshData);
    void setSensorData(RefreshData refreshData);
}
