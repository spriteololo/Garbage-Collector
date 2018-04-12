package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.MvpView;

import java.util.concurrent.TimeUnit;

import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import io.reactivex.Observer;
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

    public interface RobotConnectionListener {
        short onConnected();

        short onConnecting();

        short onDisconnected();

        short onDisconnecting();
    }

    protected void setRobotConnectionListener(final RobotConnectionListener robotConnectionListener) {
        this.robotConnectionListener = robotConnectionListener;
    }

    protected class ConnectionToRobot extends AsyncTask<Void, Void, Void> {
        private final RequestInterface requestInterface;

        public ConnectionToRobot(RequestInterface requestInterface) {
            this.requestInterface = requestInterface;
        }

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
                                action(true);
                                Log.i("Timer", "Next");
                            }

                            @Override
                            public void onError(final Throwable e) {
                                action(false);
                                Log.i("Timer", "Err");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                if(isCancelled() && disconnection > 1) return null;

                try {
                    TimeUnit.MILLISECONDS.sleep(Constants.CONNECTION_TO_ROBOT_DELAY_CHECK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(isCancelled() && disconnection > 1) return null;
            }
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
