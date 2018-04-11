package by.brstu.dmitry.garbagecollector.ui.manual_control;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.InternetConnectionState;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseInternetMvpPresenter;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Dmitry on 03.03.2018.
 */

@InjectViewState
public class ManualControlPresenter extends BaseInternetMvpPresenter<ManualControlView> {

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


}
