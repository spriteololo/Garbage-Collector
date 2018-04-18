package by.brstu.dmitry.garbagecollector.ui.object_following;

import com.arellomobile.mvp.MvpView;

import okhttp3.ResponseBody;

interface ObjectFollowingView extends MvpView{
    void setSensorData(final boolean isForwardSensor, ResponseBody responseBody);
}
