package by.brstu.dmitry.garbagecollector.application;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableManager {

    private static CompositeDisposable compositeDisposableForContinuousTasks;
    private static CompositeDisposable compositeDisposableForRapidTasks;

    static void addContinuous(Disposable disposable) {
        getCompositeDisposableForContinuousTasks().add(disposable);
    }

    public static void disposeContinuous() {
        getCompositeDisposableForContinuousTasks().dispose();
    }

    private static CompositeDisposable getCompositeDisposableForContinuousTasks() {
        if (compositeDisposableForContinuousTasks == null || compositeDisposableForContinuousTasks.isDisposed()) {
            compositeDisposableForContinuousTasks = new CompositeDisposable();
        }
        return compositeDisposableForContinuousTasks;
    }

    static void addRapid(Disposable disposable) {
        getCompositeDisposableForRapidTasks().add(disposable);
    }

    public static void disposeRapid() {
        getCompositeDisposableForRapidTasks().dispose();
    }

    private static CompositeDisposable getCompositeDisposableForRapidTasks() {
        if (compositeDisposableForRapidTasks == null || compositeDisposableForRapidTasks.isDisposed()) {
            compositeDisposableForRapidTasks = new CompositeDisposable();
        }
        return compositeDisposableForContinuousTasks;
    }

    private DisposableManager() {}
}
