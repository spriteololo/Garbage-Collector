package by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode;

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
public class StelsPresenter extends BaseMvpPresenter<StelsView> {

    @Inject
    RequestInterface requestInterface;

    private int leftWheelSpeed;
    private int rightWheelSpeed;
    private boolean leftDirection;
    private boolean rightDirection;

    private RobotMoving robotMoving;
    private Byte isStarted = 0;

    StelsPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    public void setProgressChange(final boolean rightWheel, final int i) {
        if (rightWheel) {
            rightWheelSpeed = Math.abs(i - 255) > Constants.MINIMUM_WHEEL_VALUE ? Math.abs(i - 255) : 0;
            rightDirection = (i < 255);
        } else {
            leftWheelSpeed = Math.abs(i - 255) > Constants.MINIMUM_WHEEL_VALUE ? Math.abs(i - 255) : 0;
            leftDirection = (i < 255);
        }
    }


    public void movingControl(final boolean isStart) {

        if (isStart) {
            if (++isStarted == 1) {
                robotMoving = new RobotMoving();
                robotMoving.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        } else {
            if (--isStarted == 0) {
                if (robotMoving != null) {
                    robotMoving.cancel(false);
                }
            }
        }
        Log.i("Moving", "isStarted = " + isStarted);
    }

    protected class RobotMoving extends AsyncTask<Void, Void, Void> {

        public RobotMoving() {
        }

        @Override
        protected Void doInBackground(final Void... voids) {

            Log.i("Moving", "async");
            while (true) {
                if (leftWheelSpeed > Constants.MINIMUM_WHEEL_VALUE ||
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

                }

                if (isCancelled()) return null;

                try {
                    TimeUnit.MILLISECONDS.sleep(Constants.BASE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isCancelled()) return null;
            }

        }
    }
}
