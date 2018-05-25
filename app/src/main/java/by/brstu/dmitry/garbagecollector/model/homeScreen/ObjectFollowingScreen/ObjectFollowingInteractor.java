package by.brstu.dmitry.garbagecollector.model.homeScreen.ObjectFollowingScreen;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.model.BaseInteractor;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ObjectFollowingInteractor implements IObjectFollowing {

    @Inject
    BaseInteractor baseInteractor;

    @Inject
    RequestInterface requestInterface;

    private IObjectFollowing.Actions actions;

    public ObjectFollowingInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(Actions objectFollowingPresenter) {
        actions = objectFollowingPresenter;
    }

    @Override
    public void moveRobot(int speed, int time) {
        Observable<ResponseBody> observable = requestInterface.move(speed > 0 ? 1 : 0,
                Math.abs(speed),
                speed > 0 ? 1 : 0,
                Math.abs(speed), time)
                .subscribeOn(Schedulers.io());
        baseInteractor.move(observable, actions);
    }

    @Override
    public void getFrontInfra() {
        Observable<ResponseBody> observable = requestInterface.forwardInfra()
                .subscribeOn(Schedulers.io());
        baseInteractor.getFrontInfra(observable, actions);
    }

    @Override
    public void getBackInfra() {
        Observable<ResponseBody> observable = requestInterface.backwardInfra()
                .subscribeOn(Schedulers.io());
        baseInteractor.getBackInfra(observable, actions);
    }


}
