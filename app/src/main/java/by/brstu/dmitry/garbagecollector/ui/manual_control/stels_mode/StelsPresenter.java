package by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import by.brstu.dmitry.garbagecollector.application.DisposingObserver;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.OtherManualModes.IStels;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@InjectViewState
public class StelsPresenter extends BaseMvpPresenter<StelsView> implements IStels.Actions {

    @Inject
    IStels interactor;

    private int leftWheelSpeed;
    private int rightWheelSpeed;
    private boolean leftDirection;
    private boolean rightDirection;

    private Disposable robotMoving;
    private Byte isStarted = 0;

    StelsPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        interactor.setActions(this);
    }

    void setProgressChange(final boolean rightWheel, int i) {
        if (rightWheel) {
            rightDirection = (i < 255);
            rightWheelSpeed = Math.abs(i - 255) * (Constants.MAXIMUM_SPEED_VALUE - Constants.MINIMUM_SPEED_VALUE) / 255 + Constants.MINIMUM_SPEED_VALUE;
            if (rightWheelSpeed > 255) {
                rightWheelSpeed = 255;
            }
            if (rightWheelSpeed == 130) rightWheelSpeed = 0;

        } else {
            leftDirection = (i < 255);

            leftWheelSpeed = Math.abs(i - 255) * (Constants.MAXIMUM_SPEED_VALUE - Constants.MINIMUM_SPEED_VALUE) / 255 + Constants.MINIMUM_SPEED_VALUE;
            if (leftWheelSpeed > 255) {
                leftWheelSpeed = 255;
            }
            if (leftWheelSpeed == 130) leftWheelSpeed = 0;
        }
    }


    void movingControl(final boolean isStart) {

        if (isStart) {
            if (++isStarted == 1) {
                robotMoving = Observable.interval(200, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .subscribe(a -> robotMoving());
            }
        } else {
            if (--isStarted == 0) {
                if (robotMoving != null && !robotMoving.isDisposed())
                    robotMoving.dispose();
            }
        }
        Log.i("Moving", "isStarted = " + isStarted);
    }

    private void robotMoving() {
        interactor.moveRobot(leftDirection ? leftWheelSpeed : -leftWheelSpeed,
                rightDirection ? rightWheelSpeed : -rightWheelSpeed,
                Constants.BASE_TIME);
    }

    @Override
    public void onSuccess(Observable<ResponseBody> observable, @DataType int type) {
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
                observable.observeOn(AndroidSchedulers.mainThread())
                        .doOnError(err -> getViewState().movingAvailability(false))
                        .doOnNext(responseBody -> {
                            getViewState().movingAvailability(true);
                            String s = responseBody.string();
                            Log.i("MovingPrevious", s.subSequence(s.length() - 4, s.length()) + "");
                            Log.i("Moving", "Left " + leftWheelSpeed + " Right " + rightWheelSpeed);
                        })
                        .subscribe(new DisposingObserver<ResponseBody>() {
                            @Override
                            public void onSubscribe(final Disposable d) {
                                //addRapid(d);
                            }
                        });
                break;
        }
    }

    @Override
    public void onError() {

    }
}
