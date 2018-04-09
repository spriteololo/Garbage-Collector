package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseInternetMvpPresenter<View extends MvpView> extends BaseMvpPresenter<View> {

    private boolean isConnected = false;

    protected boolean isInternetConnection(@NonNull final Context context) {
        final ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (cm != null ? cm.getActiveNetworkInfo() : null)
                != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public interface RobotConnectionListener {
        void onConnecting(); //Requires onConnected in the end of implementation

        void onConnected();

        void onDisconnecting(); //Requires onDisconnected in the end of implementation

        void onDisconnected();
    }

    protected void connectionToRobotListener(final RobotConnectionListener robotConnectionListener,
                                             final RequestInterface requestInterface,
                                             final boolean setOrRemove) {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                requestInterface.checkConnectionToRobot()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(final Disposable d) {

                            }

                            @Override
                            public void onNext(final ResponseBody responseBody) {
                                if (isConnected) {
                                    robotConnectionListener.onConnected();
                                } else {
                                    isConnected = true;
                                    robotConnectionListener.onConnecting();
                                }
                            }

                            @Override
                            public void onError(final Throwable e) {
                                if (isConnected) {
                                    isConnected = false;
                                    robotConnectionListener.onDisconnecting();
                                } else {
                                    robotConnectionListener.onDisconnected();
                                }
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                Looper.loop();
            }
        };//TODO

        if(setOrRemove) {
            handler.postDelayed(runnable, Constants.CONNECTION_TO_ROBOT_DELAY_CHECK);
        } else {
            handler.removeCallbacks(runnable);
        }
    }


}
