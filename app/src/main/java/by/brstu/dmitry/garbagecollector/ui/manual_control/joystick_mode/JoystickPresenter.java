package by.brstu.dmitry.garbagecollector.ui.manual_control.joystick_mode;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;

@InjectViewState
public class JoystickPresenter extends BaseMvpPresenter<JoystickManualView> {

    boolean isCentered = true;
    int lastAngle;
    int lastStrength;

    @Inject
    RequestInterface requestInterface;

    JoystickPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    public void onMove(int angle, int strength) {
        lastAngle = angle -= angle < 90 ? -270 : 90;
        lastStrength = strength *= 255;
        //Log.i("Joystick", angle + "");
        if(isCentered) {
            isCentered = false;
            //TODO
        }
    }
}
