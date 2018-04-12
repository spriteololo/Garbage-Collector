package by.brstu.dmitry.garbagecollector.application;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import by.brstu.dmitry.garbagecollector.R;

import static by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType.CONNECTED_TO_NETWORK;
import static by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType.CONNECTED_TO_ROBOT;
import static by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType.CONNECTING_TO_ROBOT;
import static by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType.NO_CONNECTION_TO_ROBOT;
import static by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType.NO_NETWORK_CONNECTION;
import static by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType.ROBOT_DISCONNECTING;

public class InternetConnectionState {

    @IntDef({NO_NETWORK_CONNECTION,
            NO_CONNECTION_TO_ROBOT,
            ROBOT_DISCONNECTING,
            CONNECTING_TO_ROBOT,
            CONNECTED_TO_ROBOT,
            CONNECTED_TO_NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionType {
        int NO_NETWORK_CONNECTION = R.string.no_network_connection;
        int NO_CONNECTION_TO_ROBOT = R.string.no_connection_to_robot;
        int ROBOT_DISCONNECTING = R.string.robot_is_disconnecting;
        int CONNECTING_TO_ROBOT = R.string.connecting_to_robot;
        int CONNECTED_TO_ROBOT = R.string.connected_to_robot;
        int CONNECTED_TO_NETWORK = R.string.connected_to_network;
    }
}
