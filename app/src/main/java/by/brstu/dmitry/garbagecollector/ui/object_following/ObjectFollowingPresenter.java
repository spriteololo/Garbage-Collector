package by.brstu.dmitry.garbagecollector.ui.object_following;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@InjectViewState
public class ObjectFollowingPresenter extends BaseMvpPresenter<ObjectFollowingView> {

    @Inject
    RequestInterface requestInterface;

    SensorsState sensorsState;
    private boolean isForwardSensor;



    ObjectFollowingPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    public void startFollowing(final boolean start) {
        if(start) {
            if(sensorsState == null) {
                sensorsState = new SensorsState();
                sensorsState.execute();
            }
        } else {
            if(sensorsState != null) {
                sensorsState.cancel(false);
            }
        }

    }

    public void setSensor(final boolean isForwardSensor) {
        this.isForwardSensor = isForwardSensor;
    }

    protected class SensorsState extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(final Void... voids) {

            while (true) {
                requestInterface.checkConnectionToRobot()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(final Disposable d) {
                            }

                            @Override
                            public void onNext(final ResponseBody responseBody) {
                                getViewState().setSensorData(true, responseBody);
                                Log.i("Sensor", "Next");
                            }

                            @Override
                            public void onError(final Throwable e) {
                                getViewState().setSensorData(true, null);
                                Log.i("Sensor", "Err");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                if (isCancelled()) return null;

                try {
                    TimeUnit.MILLISECONDS.sleep(Constants.CONNECTION_TO_ROBOT_DELAY_CHECK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isCancelled()) return null;

               /* if (leftWheelSpeed > Constants.MINIMUM_WHEEL_VALUE ||
                        rightWheelSpeed > Constants.MINIMUM_WHEEL_VALUE) {
                    Log.i("Moving", "Left " + leftWheelSpeed + " Right " + rightWheelSpeed);

                    requestInterface.move(leftDirection ? 1 : 0, leftWheelSpeed, rightDirection ? 1 : 0, rightWheelSpeed, Constants.BASE_TIME)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(final Disposable d) {

                                }

                                @Override
                                public void onNext(final ResponseBody responseBody) {
                                    Log.i("In", "OK");
                                }

                                @Override
                                public void onError(final Throwable e) {
                                    Log.i("In", "Error");
                                    //Log.e("FORWARD", System.currentTimeMillis() + "" + e.toString());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }*/
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            sensorsState = null;
        }
    }

}
