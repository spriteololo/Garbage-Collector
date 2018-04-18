package by.brstu.dmitry.garbagecollector.ui.home;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.application.InternetConnectionState.ConnectionType;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpActivity;
import by.brstu.dmitry.garbagecollector.ui.all.base.NetworkStateReceiver;
import by.brstu.dmitry.garbagecollector.ui.auto_moving.AutoMovingFragment;
import by.brstu.dmitry.garbagecollector.ui.login.LoginFragment;
import by.brstu.dmitry.garbagecollector.ui.login.LoginInterface;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.ManualControlView;
import by.brstu.dmitry.garbagecollector.ui.moves_recording.MovesRecordingFragment;
import by.brstu.dmitry.garbagecollector.ui.object_following.ObjectFollowingFragment;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;


public class HomeScreenActivity extends BaseMvpActivity implements NavigationView.OnNavigationItemSelectedListener,
        LoginInterface,
        HomeView,
        NetworkStateReceiver.NetworkStateReceiverListener,
        ManualControlView {

    @InjectPresenter
    HomePresenter homePresenter;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private String screenName;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getApplicationComponent().inject(this);
        setContentView(R.layout.activity_home_screen);
    }

    @Override
    protected void onViewsBinded() {
        setToolbar(toolbar, getString(R.string.app_name), true);

        navigator.applyCommand(new Replace(Constants.Screens.MANUAL_CONTROL_SCREEN, null)); //TODO Back to HomeScreen

        drawerLayout.addDrawerListener(new DrawerListener() {

            @Override
            public void onClosed() {
                if (screenName != null) {
                    navigator.applyCommand(new BackTo(null));
                    navigator.applyCommand(new Replace(screenName, null));
                    screenName = null;
                }
            }
        });

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.getNavigatorHolder().setNavigator(navigator);

        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        networkStateReceiver.updateInfo(this);
    }

    @Override
    protected void onPause() {
        BaseApplication.getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            // Guest
            case R.id.nav_login:
                screenName = Constants.Screens.LOGIN_SCREEN;
                break;
            case R.id.nav_home:
                screenName = Constants.Screens.HOME_SCREEN;
                break;

            //User
            case R.id.manual_control:
                screenName = Constants.Screens.MANUAL_CONTROL_SCREEN;
                break;
            case R.id.auto_moving:
                screenName = Constants.Screens.AUTO_MOVING_SCREEN;
                break;
            case R.id.object_following:
                screenName = Constants.Screens.OBJECT_FOLLOWING_SCREEN;
                break;
            //Admin
            case R.id.moves_recording:
                screenName = Constants.Screens.MOVES_RECORDING_SCREEN;
                break;
        }

        return true;
    }


    private final Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.fragment_container) {

        @Override
        protected Fragment createFragment(final String screenKey, final Object data) {
            switch (screenKey) {
                //Guest
                case Constants.Screens.LOGIN_SCREEN:
                    return LoginFragment.getInstance();
                case Constants.Screens.HOME_SCREEN:
                    return HomeFragment.getInstance();
                //User
                case Constants.Screens.MANUAL_CONTROL_SCREEN:
                    return ManualControlFragment.getInstance();
                case Constants.Screens.AUTO_MOVING_SCREEN:
                    return AutoMovingFragment.getInstance();
                case Constants.Screens.OBJECT_FOLLOWING_SCREEN:
                    return ObjectFollowingFragment.getInstance();
                //Admin
                case Constants.Screens.MOVES_RECORDING_SCREEN:
                    return MovesRecordingFragment.getInstance();

                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void setupFragmentTransactionAnimation(final Command command, final Fragment currentFragment,
                                                         final Fragment nextFragment, final FragmentTransaction fragmentTransaction) {
            final ViewGroup viewGroup = findViewById(R.id.fragment_container);
            TransitionManager.beginDelayedTransition(viewGroup);
        }

        @Override
        protected void showSystemMessage(final String message) {
            Toast.makeText(HomeScreenActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }

        @Override
        public void applyCommand(final Command command) {
            super.applyCommand(command);
        }
    };

    @Override
    public void setDrawerChangingGroupsVisibility(final int typeOfUser) {
        final Menu nav_menu = navigationView.getMenu();
        if (typeOfUser == 0) {
            // Admin
            nav_menu.setGroupVisible(R.id.admin_group, true);
            nav_menu.setGroupVisible(R.id.user_group, true);
            setToolbarTitle("Admin");
        }

        if (typeOfUser == 1) {
            // User
            nav_menu.setGroupVisible(R.id.admin_group, false);
            nav_menu.setGroupVisible(R.id.user_group, true);
            setToolbarTitle("User");
        }

        if (typeOfUser == 2) {
            // Guest
            nav_menu.setGroupVisible(R.id.admin_group, false);
            nav_menu.setGroupVisible(R.id.user_group, false);
            setToolbarTitle("Login");
        }

        navigator.applyCommand(new Replace(Constants.Screens.HOME_SCREEN, null));
    }

    @Override
    public void networkAvailable() {
        connectionState(ConnectionType.CONNECTED_TO_NETWORK);
        homePresenter.networkStateChanged(true);
    }

    @Override
    public void networkUnavailable() {
        connectionState(ConnectionType.NO_NETWORK_CONNECTION);
        homePresenter.networkStateChanged(false);
    }
}
