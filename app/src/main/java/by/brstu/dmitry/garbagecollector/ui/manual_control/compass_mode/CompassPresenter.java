package by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.TimeUnit;

import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import by.brstu.dmitry.garbagecollector.ui.compass.CompassCustomView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

@InjectViewState
public class CompassPresenter extends BaseMvpPresenter<CompassView> {


    public void beginStudying(CompassCustomView compassView) {
        /*sendRequest()*/
    }

    protected class Studying extends AsyncTask<Void, Void, Void> {
        private final RequestInterface requestInterface;

        public Studying(RequestInterface requestInterface) {
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
                                Log.i("Study", "Next");
                            }

                            @Override
                            public void onError(final Throwable e) {
                                Log.i("Study", "Err");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                if(isCancelled()) return null;

                try {
                    TimeUnit.MILLISECONDS.sleep(Constants.CONNECTION_TO_ROBOT_DELAY_CHECK);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(isCancelled()) return null;
            }
        }
    }
}
