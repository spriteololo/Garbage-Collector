package by.brstu.dmitry.garbagecollector.ui.manual_control;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.application.RefreshDataConverter;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@InjectViewState
public class ManualControlPresenter extends BaseMvpPresenter<ManualControlView> {

    @Inject
    RequestInterface requestInterface;

    @Inject
    Context context;

    int[][] last100Positions = new int[2][100];

    public ManualControlPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

    }

    void sendData(final float[] toSend) {

        final int[] mapped = map(toSend);


        int leftSpeed = 0;
        int rightSpeed = 0;
        int leftDirection = 0; //forward
        int rightDirection = 0;


        leftDirection = rightDirection = mapped[1] < 0 ? 0 : 1;

        for (int i = 0; i < mapped.length; i++) {
            mapped[i] = Math.abs(mapped[i]);
        }
        leftSpeed = rightSpeed = mapped[1];

        /*if(mapped[1] > 100 &&) {

        }*/

        if (leftSpeed < 110) leftSpeed = 0;
        if (rightSpeed < 110) rightSpeed = 0;


        requestInterface.forward(leftDirection, leftSpeed, rightDirection, rightSpeed, 5)
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

    private int[] map(final float[] initArray) {

        final int[] newArr = new int[3];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = (int) Math.floor((-250 + 500 / (1 + (Math.pow(Math.exp(1), (-5 * initArray[i])))))); // 250 - max value
        }

        Log.e("sent to: ", newArr[0] + "\t\t " + newArr[1] + "\t\t " + newArr[2]);

        return newArr;

    }

    void lidOnClick(boolean closed) {
        if (closed) {
            requestInterface.openLid()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(final Disposable d) {

                        }

                        @Override
                        public void onNext(final ResponseBody responseBody) {
                            //TODO getViewState().setLidState(true);
                        }

                        @Override
                        public void onError(final Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            requestInterface.closeLid()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(final Disposable d) {

                        }

                        @Override
                        public void onNext(final ResponseBody responseBody) {
                            //TODO getViewState().setLidState(true);
                        }

                        @Override
                        public void onError(final Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private RefreshThread refreshThread;

    public void startRefresh() {
        refreshThread = new RefreshThread();
        refreshThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void stopRefresh() {
        if (refreshThread != null) {
            refreshThread.cancel(true);
        }
    }


    class RefreshThread extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(final Void... voids) {

            while (true) {
                requestInterface.refreshBaseData()
                        .subscribeOn(Schedulers.io())
                        .map(new Function<ResponseBody, RefreshData>() {
                            @Override
                            public RefreshData apply(ResponseBody responseBody) throws Exception {
                                return RefreshDataConverter.convertData(responseBody);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RefreshData>() {
                            @Override
                            public void onSubscribe(final Disposable d) {
                            }

                            @Override
                            public void onNext(final RefreshData refreshData) {
                                getViewState().setBaseData(refreshData);
                                Log.i("Timer", "Next" + refreshData.toString());
                            }

                            @Override
                            public void onError(final Throwable e) {

                                Log.i("Timer", "Err");
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
            }
        }
    }
}
