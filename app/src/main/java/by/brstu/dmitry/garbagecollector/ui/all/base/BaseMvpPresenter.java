package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseMvpPresenter<View extends MvpView> extends MvpPresenter<View> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void unSubscribeOnDestroy(@NonNull final Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
