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

    @GET("f")
    Observable<ResponseBody> forwardInfra(); //forward sensor

    @GET("b")
    Observable<ResponseBody> backwardInfra();

    @GET("m")
    Observable<ResponseBody> move(@Query("a") int leftDirection, @Query("b") int leftSpeed, @Query("c") int rightDirection, @Query("d") int rightSpeed,  @Query("t") int TIME);



    @GET("a")//TODO
    Observable<ResponseBody> checkLidState();

    @GET("i")
    Observable<ResponseBody> refreshBaseData();

    @GET("o")
    Observable<ResponseBody> openLid();

    @GET("k")
    Observable<ResponseBody> closeLid();
}