package by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.ICompass;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import by.brstu.dmitry.garbagecollector.ui.compass.CompassCustomView;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

@InjectViewState
public class CompassPresenter extends BaseMvpPresenter<CompassView> implements SensorEventListener,
        CompassCustomView.RotationCallback,
        ICompass.Actions {

    @Inject
    ICompass interactor;

    CompassPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        interactor.setActions(this);
    }

    private void clearRotation() {
        getViewState().clearRotation();
    }

    @Override
    public void rotationStart() {
        Log.e("TIME2", "START");
    }

    /*rotation - angle on which it was rotated
     * millis - time of this rotation*/
    @Override
    public void rotationEnd(final long millis, final float rotation) {
        Log.e("TIME2", millis + " " + rotation + "degrees");

    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        float degree = Math.round(event.values[0]);
        getViewState().setDataOnCompass(degree);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
}
