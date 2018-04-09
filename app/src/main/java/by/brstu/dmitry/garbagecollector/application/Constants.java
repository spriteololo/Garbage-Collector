package by.brstu.dmitry.garbagecollector.application;

import by.brstu.dmitry.garbagecollector.BuildConfig;

public class Constants {

    public static final short CONNECTION_TO_ROBOT_DELAY_CHECK = 1000;

    public interface BaseApi {
        String BASE_URL = "http://192.168.43.3/"; //TODO
    }




    public static final int VERSION_CODE = BuildConfig.VERSION_CODE;
    public static final String VERSION_NAME = BuildConfig.VERSION_NAME;


    public class Screens {
        public static final String LOGIN_SCREEN = "login_screen_fragment";
        public static final String HOME_SCREEN = "home_screen_fragment";

        public static final String MANUAL_CONTROL_SCREEN = "manual_control_screen_fragment";
        public static final String AUTO_MOVING_SCREEN = "auto_moving_screen_fragment";

        public static final String MOVES_RECORDING_SCREEN = "moves_recording_screen_fragment";

    }

    public interface NetworkingConfig {
        int TIMEOUT = 0;
    }

}
