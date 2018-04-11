package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.MvpView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseInternetMvpPresenter<View extends MvpView> extends BaseMvpPresenter<View> {

    private short connection = 0;
    private short disconnection = 0;
    private boolean isConnectedToInternet = false;
    private RobotConnectionListener robotConnectionListener;


    protected boolean isInternetConnection(@NonNull final Context context) {
        final ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (cm != null ? cm.getActiveNetworkInfo() : null)
                != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public interface RobotConnectionListener {
        short onConnected();

        short onConnecting();

        short onDisconnected();

        short onDisconnecting();
    }

    protected void setListener(final RobotConnectionListener robotConnectionListener) {
        this.robotConnectionListener = robotConnectionListener;
    }
    protected void networkState(final boolean b) {
        this.isConnectedToInternet = b;

    }

    protected class ConnectionToRobot extends AsyncTask<Void, Void, Void> {
        private final RequestInterface requestInterface;
        Timer timer;

        public ConnectionToRobot(RequestInterface requestInterface) {
            this.requestInterface = requestInterface;
            timer = new Timer();
        }

        @Override
        protected Void doInBackground(final Void... voids) {

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    requestInterface.checkConnectionToRobot()
                            .subscribeOn(Schedulers.io())
                            .debounce(5000, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(final Disposable d) {
                                }

                                @Override
                                public void onNext(final ResponseBody responseBody) {
                                    if (isConnectedToInternet) {
                                        action(true);
                                    }
                                    Log.i("Timer", "Next");
                                }

                                @Override
                                public void onError(final Throwable e) {
                                    if (isConnectedToInternet) {
                                        action(false);
                                    } else {
                                        onNext(null);
                                    }
                                    Log.i("Timer", "Err");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 2000);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            timer.cancel();
        }
    }

    private void action(final boolean isConnect) {
        short result = 0;

        switch (isConnect ? connection : disconnection) {
            case 0:
                result += isConnect ? robotConnectionListener.onConnecting() : robotConnectionListener.onDisconnecting();
                if (isConnect) disconnection = 0;
                else connection = 0;
                break;
            case 1:
                result += isConnect ? robotConnectionListener.onConnected() : robotConnectionListener.onDisconnected();
            default:
                break;
        }
        if (isConnect) connection += result;
        else disconnection += result;
    }


}
