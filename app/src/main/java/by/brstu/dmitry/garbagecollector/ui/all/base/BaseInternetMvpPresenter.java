package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.MvpView;

import java.util.concurrent.TimeUnit;

import by.brstu.dmitry.garbagecollector.application.DisposingObserver;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseInternetMvpPresenter<View extends MvpView> extends BaseMvpPresenter<View> {

    private short connection = 0;
    private short disconnection = 0;
    private RobotConnectionListener robotConnectionListener;


    protected boolean isInternetConnection(@NonNull final Context context) {
        final ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (cm != null ? cm.getActiveNetworkInfo() : null)
                != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void resetConnectionState() {
        connection = 0;
        disconnection = 0;
    }

    public interface RobotConnectionListener {
        short onConnected();

        short onConnecting();

        short onDisconnected();

        short onDisconnecting();

        void checkUI(final boolean isConnect);
    }

    protected void setRobotConnectionListener(final RobotConnectionListener robotConnectionListener) {
        this.robotConnectionListener = robotConnectionListener;
    }

    protected void checkConnection(final RequestInterface requestInterface, final Scheduler scheduler) {
        requestInterface.checkConnectionToRobot()
                .doOnError(err -> action(false))
                .observeOn(scheduler)
                .retryWhen(o -> o.delay( 100, TimeUnit.MILLISECONDS, scheduler))
                .repeatWhen(o -> o.delay(1000, TimeUnit.MILLISECONDS, scheduler))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(next -> action(true))
                .subscribe(new DisposingObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        addContinuous(d);
                    }
                });
    }


    public synchronized void action(final boolean isConnect) {
        short result = 0;
        if (!isConnect) {
            Log.i("Timer22", "erroooor");
        } else {
            Log.i("Timer22", "next");
        }
        switch (isConnect ? connection : disconnection) {
            case 0:
                result += isConnect ? robotConnectionListener.onConnecting() : robotConnectionListener.onDisconnecting();
                if (isConnect) disconnection = 0;
                else connection = 0;
                break;
            case 1:
                result += isConnect ? robotConnectionListener.onConnected() : robotConnectionListener.onDisconnected();
                connection = (short) (isConnect ? 1 : 0);
                disconnection = (short) (isConnect ? 0 : 1);

                break;
            case 2:
                robotConnectionListener.checkUI(isConnect);
                break;
            default:
                break;
        }
        if (isConnect) connection += result;
        else disconnection += result;
    }


}
