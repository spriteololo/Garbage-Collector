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
        try {
            getCompositeDisposableForContinuousTasks().dispose();
        } catch (Exception ignored) {

        }
    }

    private static CompositeDisposable getCompositeDisposableForContinuousTasks() {
        if (compositeDisposableForContinuousTasks == null || compositeDisposableForContinuousTasks.isDisposed()) {
            compositeDisposableForContinuousTasks = new CompositeDisposable();
        }
        return compositeDisposableForContinuousTasks;
    }

    static void addRapid(Disposable disposable) {
        if (disposable != null) {
            getCompositeDisposableForRapidTasks().add(disposable);
        }
    }

    public static void disposeRapid() {
        try {
            getCompositeDisposableForRapidTasks().dispose();
        } catch (Exception ignored) {

        }
    }

    private static CompositeDisposable getCompositeDisposableForRapidTasks() {
        if (compositeDisposableForRapidTasks == null) {
            compositeDisposableForRapidTasks = new CompositeDisposable();
        }
        return compositeDisposableForRapidTasks;
    }

    private DisposableManager() {
    }

    public static void disposeAll() {
        disposeContinuous();
        disposeRapid();
    }
}
