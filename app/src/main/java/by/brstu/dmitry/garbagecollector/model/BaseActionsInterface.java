package by.brstu.dmitry.garbagecollector.model;

import by.brstu.dmitry.garbagecollector.application.Constants.DataType;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface BaseActionsInterface {

    void onSuccess(Observable<ResponseBody> observable, @DataType int type);

    void onError();
}
