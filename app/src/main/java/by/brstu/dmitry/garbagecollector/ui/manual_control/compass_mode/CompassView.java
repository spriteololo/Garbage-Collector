package by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode;

import com.arellomobile.mvp.MvpView;

public interface CompassView extends MvpView{

    void startListening();
    void stopListening();

    void setDataOnCompass(float degree);

    void clearRotation();
}
