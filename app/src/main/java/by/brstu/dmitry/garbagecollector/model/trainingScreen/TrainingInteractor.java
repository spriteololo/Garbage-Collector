package by.brstu.dmitry.garbagecollector.model.trainingScreen;

import javax.inject.Inject;

import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.inject.RequestInterface;
import by.brstu.dmitry.garbagecollector.model.BaseInteractor;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class TrainingInteractor implements ITraining {

    @Inject
    BaseInteractor baseInteractor;

    @Inject
    RequestInterface requestInterface;

    private ITraining.Actions actions;

    public TrainingInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(Actions trainingPresenter) {
        this.actions = trainingPresenter;
    }

    @Override
    public void moveRobot(int left, int right, int time) {
        final Observable<ResponseBody> a =
                requestInterface.move(left >= 0 ? 1 : 0,
                        Math.abs(left),
                        right >= 0 ? 1 : 0,
                        Math.abs(right),
                        time)
                        .subscribeOn(Schedulers.io());
        baseInteractor.move(a, actions);
    }
}
