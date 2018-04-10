package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.MvpView;

import java.util.concurrent.TimeUnit;

import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseInternetMvpPresenter<View extends MvpView> extends BaseMvpPresenter<View> {

    private boolean isConnected = false;
    private Disposable disposable = null;
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

        if (setOrRemove) {
            requestInterface.checkConnectionToRobot()
                    .subscribeOn(Schedulers.io())
                    .debounce(1000, TimeUnit.MILLISECONDS)
                    .repeat()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(final Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onNext(final ResponseBody responseBody) {
                                /*if (isConnected) {
                                    robotConnectionListener.onConnected();
                                } else {
                                    isConnected = true;
                                    robotConnectionListener.onConnecting();
                                }*/
                            Log.i("Timer", "Next");
                        }

                        @Override
                        public void onError(final Throwable e) {
                                /*if (isConnected) {
                                    isConnected = false;
                                    robotConnectionListener.onDisconnecting();
                                } else {
                                    robotConnectionListener.onDisconnected();
                                }*/
                            Log.i("Timer", "Err");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            disposable.dispose();
        }
    }


}
