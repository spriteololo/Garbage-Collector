package by.brstu.dmitry.garbagecollector.ui.manual_control.joystick_mode;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.IJoystick;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

@InjectViewState
public class JoystickPresenter extends BaseMvpPresenter<JoystickManualView> implements IJoystick.Actions {

    @Inject
    IJoystick interactor;


    JoystickPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        interactor.setActions(this);
    }


    @Override
    public void onSuccess(Observable<ResponseBody> observable, int type) {
        switch (type) {

            case DataType.BACK_INFRA:
                break;
            case DataType.BASE_DATA:
                break;
            case DataType.FRONT_INFRA:
                break;
            case DataType.LID_CLOSED:
                break;
            case DataType.LID_OPEN:
                break;
            case DataType.MOVE_WHEELS:
                break;
        }
    }

    @Override
    public void onError() {

    }

    public void onMove(int angle, int strength) {

    }
}
