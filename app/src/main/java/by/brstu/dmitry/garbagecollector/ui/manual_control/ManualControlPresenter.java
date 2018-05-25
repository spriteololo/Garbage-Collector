package by.brstu.dmitry.garbagecollector.ui.manual_control;

import com.arellomobile.mvp.InjectViewState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import by.brstu.dmitry.garbagecollector.application.DisposingObserver;
import by.brstu.dmitry.garbagecollector.application.RefreshDataConverter;
import by.brstu.dmitry.garbagecollector.model.homeScreen.ManualControlScreen.IManualControl;
import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpPresenter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

@InjectViewState
public class ManualControlPresenter extends BaseMvpPresenter<ManualControlView> implements IManualControl.Actions {

    @Inject
    IManualControl interactor;

    private Disposable baseCheckDisposable;

    public ManualControlPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setActions(this);
    }


    void lidOnClick(boolean closed) {
        if (closed) {
            interactor.openLid();
        } else {
            interactor.closeLid();
        }
    }

    public void checkBaseData() {
        interactor.checkBaseData();
    }


    public void stopCheckBaseData() {
        if (baseCheckDisposable != null && !baseCheckDisposable.isDisposed()) {
            baseCheckDisposable.dispose();
        }
    }

    @Override
    public void onSuccess(Observable<ResponseBody> observable, @DataType int type) {
        switch (type) {

            case DataType.BACK_INFRA:
                break;
            case DataType.BASE_DATA:
                observable.retryWhen(o -> o.delay(100, TimeUnit.MILLISECONDS))
                        .repeatWhen(o -> o.delay(1000, TimeUnit.MILLISECONDS))
                        .map(RefreshDataConverter::convertData)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(next -> getViewState().setBaseData(next))
                        .subscribe(new DisposingObserver<RefreshData>() {
                            @Override
                            public void onSubscribe(final Disposable d) {
                                addContinuous(d);
                                baseCheckDisposable = d;
                            }
                        });//TODO BASE DATA RX*/
                break;
            case DataType.FRONT_INFRA:
                break;
            case DataType.MOVE_WHEELS:
                break;
            case DataType.LID_CLOSED:
                observable.observeOn(AndroidSchedulers.mainThread())
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
                break;
            case DataType.LID_OPEN:
                observable.observeOn(AndroidSchedulers.mainThread())
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
                break;
        }
    }

    @Override
    public void onError() {

    }
}
