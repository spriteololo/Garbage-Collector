package by.brstu.dmitry.garbagecollector.application;

import by.brstu.dmitry.garbagecollector.BuildConfig;

public class Constants {

    public static final short CONNECTION_TO_ROBOT_DELAY_CHECK = 1000;
    public static final short MINIMUM_WHEEL_VALUE = 130;
    public static final short MINIMUM_DISTANCE_VALUE = 25;
    public static final int BASE_TIME = 3;

    public interface BaseApi {
        //String BASE_URL_UNCOMMENT = "http://192.168.43.3/";
        //String BASE_URL_UNCOMMENT = "http://google.com/"; //TODO
        String BASE_URL = "http://192.168.43.215/";

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
        int TIMEOUT = 5000;
    }

    public class Extras {
        public static final String POSITION_KEY = "Position";
    }
}
