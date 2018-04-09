package by.brstu.dmitry.garbagecollector.inject;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("f")
    Observable<ResponseBody> checkConnectionToRobot(); //forward sensor

    @GET("m")
    Observable<ResponseBody> forward(@Query("a") int leftDirection, @Query("b") int leftSpeed, @Query("c") int rightDirection, @Query("d") int rightSpeed,  @Query("t") int TIME);

    @GET("b")
    Observable<ResponseBody> backward();

}