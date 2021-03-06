package by.brstu.dmitry.garbagecollector.application;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DisposingObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    protected void addRapid(Disposable d) {
        DisposableManager.addRapid(d);
    }
    protected void addContinuous(Disposable d) {
        DisposableManager.addContinuous(d);
    }

    @Override
    public void onNext(T next) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }
}
