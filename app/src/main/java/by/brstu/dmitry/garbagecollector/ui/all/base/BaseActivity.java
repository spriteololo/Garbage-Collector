package by.brstu.dmitry.garbagecollector.ui.all.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {

    @ConnectionType protected int connectingToRobotState;

    @Override
    public void setContentView(final int layoutResID) {
        super.setContentView(layoutResID);

        onViewCreated();
    }

    @Override
    public void setContentView(final View view) {
        super.setContentView(view);

        onViewCreated();
    }

    @Override
    public void setContentView(final View view, final ViewGroup.LayoutParams params) {
        super.setContentView(view, params);

        onViewCreated();
    }

    @Override
    protected void attachBaseContext(final Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void onViewCreated() {
        ButterKnife.bind(this);
        onViewsBinded();
    }

    protected void setToolbar(@NonNull final Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    protected void setToolbar(@NonNull final Toolbar toolbar, @NonNull final CharSequence title, final boolean isShowHomeButton) {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHomeButton);
        }
    }

    protected void setToolbarTitle(@NonNull final CharSequence title) {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected abstract void onViewsBinded();

    public void connectionState(@ConnectionType final int connectingToRobotState) {
        runOnUiThread(() -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(createGradient(connectingToRobotState));
                getSupportActionBar().setSubtitle(getResources().getString(connectingToRobotState));
            }
        });
        this.connectingToRobotState = connectingToRobotState;
    }

    private GradientDrawable createGradient(@ConnectionType final int type) {
        int[] colors = new int[3];
        final int good = -16760832; //FF004000
        final int bad = -12582912; //#FF400000

        if(type == ConnectionType.CONNECTED_TO_NETWORK) {
            colors = new int[]{good, Color.BLACK, Color.BLACK};
        }

        if(type == ConnectionType.CONNECTING_TO_ROBOT ) {
            colors = new int[]{good, good, Color.BLACK};
        }

        if(type == ConnectionType.CONNECTED_TO_ROBOT ) {
            colors = new int[]{good, good, good};
        }

        if(type == ConnectionType.ROBOT_DISCONNECTING) {
            colors = new int[]{bad, bad, bad};
        }

        if(type == ConnectionType.NO_CONNECTION_TO_ROBOT) {
            colors = new int[]{bad, bad, Color.BLACK};
        }

        if (type == ConnectionType.NO_NETWORK_CONNECTION) {
            colors = new int[]{bad, Color.BLACK, Color.BLACK};
        }

        final GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gd.setCornerRadius(0f);

        return gd;
    }
}