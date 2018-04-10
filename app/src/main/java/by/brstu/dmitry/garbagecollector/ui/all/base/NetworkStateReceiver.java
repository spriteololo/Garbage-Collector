package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class NetworkStateReceiver extends BroadcastReceiver {

    private NetworkStateReceiverListener listener;
    private boolean currentConnection;
    private boolean previousConnection;

    public NetworkStateReceiver() {
        this.currentConnection = false;
        this.previousConnection = true;
    }

    public void onReceive(final Context context, final Intent intent) {
        if (context == null) {
            return;
        }

        final ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        currentConnection = (cm != null ? cm.getActiveNetworkInfo() : null)
                != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();

        if (!(currentConnection && previousConnection)) {
            notifyState();
        }

        previousConnection = currentConnection;
    }

    private void notifyState() {
        if (listener == null) {
            return;
        }

        if (currentConnection) {
            listener.networkAvailable();
        } else {
            listener.networkUnavailable();
        }
    }

    public void addListener(@NonNull final NetworkStateReceiverListener listener) {
        this.listener = listener;
    }

    public void updateInfo(Context context) {
        final ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (listener == null) {
            return;
        }
        if ((cm != null ? cm.getActiveNetworkInfo() : null)
                != null && cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
            listener.networkAvailable();
        } else {
            listener.networkUnavailable();
        }
    }

    public interface NetworkStateReceiverListener {

        void networkAvailable();

        void networkUnavailable();
    }
}