package by.brstu.dmitry.garbagecollector.application;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import by.brstu.dmitry.garbagecollector.BuildConfig;

public class Constants {

    public static final short CONNECTION_TO_ROBOT_DELAY_CHECK = 1000;
    public static final short MINIMUM_SPEED_VALUE = 130;
    public static final short MAXIMUM_SPEED_VALUE = 255;
    public static final float MINIMUM_DISTANCE_VALUE = 5;
    public static final float MAXIMUM_DISTANCE_VALUE = 50;
    public static final int BASE_TIME = 5; //300 ms
    public static final float WIDTH_OF_ROBIN = 0.1f;

    public interface BaseApi {
        //String BASE_URL = "http://192.168.43.3/";
        //String BASE_URL_UNCOMMENT = "http://google.com/"; //TODO
        String BASE_URL = "http://192.168.43.215:800/";

    }




    public static final int VERSION_CODE = BuildConfig.VERSION_CODE;
    public static final String VERSION_NAME = BuildConfig.VERSION_NAME;


    public class Screens {
        public static final String LOGIN_SCREEN = "login_screen_fragment";
        public static final String HOME_SCREEN = "home_screen_fragment";

        public static final String MANUAL_CONTROL_SCREEN = "manual_control_screen_fragment";
        public static final String AUTO_MOVING_SCREEN = "auto_moving_screen_fragment";
        public static final String OBJECT_FOLLOWING_SCREEN = "object_following_fragment";

        public static final String MOVES_RECORDING_SCREEN = "moves_recording_screen_fragment";
        public static final String MANUAL_JOYSTICK_SCREEN = "Joystick mode";
        public static final String MANUAL_STELS_SCREEN = "Airplane mode";
        public static final String MANUAL_COMPASS_SCREEN = "Compass mode";

    }

    public interface NetworkingConfig {
        int TIMEOUT = 200;
    }

    public class Extras {
        public static final String POSITION_KEY = "Position";
    }

    @IntDef({
            DataType.MOVE_WHEELS,
            DataType.LID_OPEN,
            DataType.LID_CLOSED,
            DataType.FRONT_INFRA,
            DataType.BACK_INFRA,
            DataType.BASE_DATA
    })

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataType {
        int MOVE_WHEELS = 60;
        int LID_OPEN = 61;
        int LID_CLOSED = 62;
        int FRONT_INFRA = 63;
        int BACK_INFRA = 64;
        int BASE_DATA = 65;
    }
}
