package by.brstu.dmitry.garbagecollector.ui.object_following;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import by.brstu.dmitry.garbagecollector.application.DisposingObserver;
import by.brstu.dmitry.garbagecollector.application.RefreshDataConverter;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ObjectFollowingScreen.IObjectFollowing;
import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@InjectViewState
public class ObjectFollowingPresenter extends BaseMvpPresenter<ObjectFollowingView> implements IObjectFollowing.Actions {

    @Inject
    IObjectFollowing interactor;

    Disposable sensorsState;
    private boolean isForwardSensor;

    private boolean realFollow = false;

    private boolean realForward;

    private int speed = 0;

    private final int time = 2;

    private final short someValue = 30;


    ObjectFollowingPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        interactor.setActions(this);
    }

    public boolean startFollowing() {
        this.realFollow = !realFollow;
        return realFollow;
    }

    public void startFollowing(final boolean realFollow) {
        this.realFollow = realFollow;
    }


    public void setSensor(final boolean isForwardSensor) {
        this.isForwardSensor = isForwardSensor;
    }

    public boolean startReading() {
        if (sensorsState == null || sensorsState.isDisposed()) {
            sensorsState = Observable.interval(200, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .subscribe(a -> next());

            return true;
        } else {
            sensorsState.dispose();
            return false;
        }
    }

    public void stopReading() {
        if(sensorsState != null && !sensorsState.isDisposed()) {
            sensorsState.dispose();
        }
    }

    @Override
    public void onSuccess(Observable<ResponseBody> observable, @DataType int type) {
        switch (type) {
            case DataType.MOVE_WHEELS: {
                observable.observeOn(AndroidSchedulers.mainThread())
                        .map(RefreshDataConverter::convertData)
                        .doOnNext(next -> {
                            getViewState().setSensorData(next);
                            evaluate(isForwardSensor ? next.getFrontInfra() : next.getBackInfra(), isForwardSensor);
                        })
                        .doOnError(Throwable::printStackTrace)
                        .subscribe(new DisposingObserver<RefreshData>() {
                            @Override
                            public void onSubscribe(final Disposable d) {
                                //addRapid(d);
                            }
                        });
                break;
            }
            case DataType.FRONT_INFRA: {
                observable.observeOn(AndroidSchedulers.mainThread())
                        .map(RefreshDataConverter::convertData)
                        .doOnNext(next -> {
                            getViewState().setSensorData(true, next);
                            evaluate(next.getFrontInfra(), true);
                        })
                        .doOnError(Throwable::printStackTrace)
                        .subscribe(new DisposingObserver<RefreshData>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                //addRapid(d);
                            }
                        });
                break;
            }
            case DataType.BACK_INFRA: {
                observable.observeOn(AndroidSchedulers.mainThread())
                        .map(RefreshDataConverter::convertData)
                        .doOnNext(next -> {
                            getViewState().setSensorData(false, next);
                            evaluate(next.getBackInfra(), false);
                        })
                        .doOnError(Throwable::printStackTrace)
                        .subscribe(new DisposingObserver<RefreshData>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                //addRapid(d);
                            }
                        });
                break;
            }
            case DataType.BASE_DATA:
                break;
        }
    }

    @Override
    public void onError() {

    }

    void next() {
        if (speed == 0) {
            if (realFollow) {
                if (isForwardSensor) {
                    getFrontInfra();
                } else {
                    getBackInfra();
                }
            } else {
                getBackInfra();
                getFrontInfra();
            }
        } else {
            move();
        }
    }

    private void move() {
        interactor.moveRobot(realFollow ? (realForward ? 1 : -1) * speed : 0, time);
    }

    private void getBackInfra() {
        interactor.getBackInfra();
    }

    private void getFrontInfra() {
        interactor.getFrontInfra();
    }

    private void evaluate(short infraData, boolean isForwardSensor) {
        boolean goToObstacle = Math.abs(infraData - someValue) > someValue * 0.1f;
        realForward = goToObstacle == isForwardSensor;

        if (goToObstacle) {
            speed = Constants.MINIMUM_SPEED_VALUE + Math.abs(infraData - someValue) * 4;
            if (speed > 255) speed = 255;
        } else {
            speed = 0;
        }
        Log.i("speedInfra", infraData + "");
        Log.i("speed", speed + "");
    }

}
