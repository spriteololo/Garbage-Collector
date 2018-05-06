package by.brstu.dmitry.garbagecollector.ui.manual_control;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.pojo.RefreshData;
import by.brstu.dmitry.garbagecollector.ui.BatteryView;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;
import by.brstu.dmitry.garbagecollector.ui.lidState.LidStateView;
import by.brstu.dmitry.garbagecollector.ui.manual_control.compass_mode.CompassFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.joystick_mode.JoystickFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode.StelsFragment;
import by.brstu.dmitry.garbagecollector.ui.pagerTab.CustomPagerBar;
import by.brstu.dmitry.garbagecollector.ui.seekBar.CustomSeekBar;
import by.brstu.dmitry.garbagecollector.ui.temperatureView.TemperatureView;
import by.brstu.dmitry.garbagecollector.ui.viewPager.CustomViewPager;


public class ManualControlFragment extends BaseMvpFragment implements ManualControlView, SeekBar.OnSeekBarChangeListener {

    @InjectPresenter
    ManualControlPresenter manualControlPresenter;

    public static ManualControlFragment getInstance() {
        return new ManualControlFragment();
    }

    @BindView(R.id.mmm)
    TextView tv;

    @BindView(R.id.guideline)
    Guideline guideLine;

    @BindView(R.id.manual_control_view_pager)
    CustomViewPager viewPager;

    @BindView(R.id.pager_tab_manual_control)
    CustomPagerBar pagerTab;

    @BindView(R.id.manual_control_temperature)
    TemperatureView temperatureView;

    @BindView(R.id.lid_state_view)
    LidStateView lidStateView;

    @BindView(R.id.manual_control_battery)
    BatteryView batteryView;

    @BindView(R.id.manual_control_fullness)
    BatteryView fullnessView;

    boolean closed;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manual_control, container, false);
    }

    @Override
    public void onResume() {
        manualControlPresenter.startRefresh();
        super.onResume();
    }

    @Override
    public void onPause() {
        manualControlPresenter.stopRefresh();
        super.onPause();
    }

    @Override
    protected void onViewsBinded() {

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.manual_control);
        }

        pagerTab.setGuideline(guideLine);

        lidStateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manualControlPresenter.lidOnClick(closed);
                if (closed) {
                    lidStateView.setClosed(true);
                    closed = false;
                } else {
                    lidStateView.setClosed(false);
                    closed = true;
                }
            }
        });
    }

    public void setPaging(final boolean setPaging) {
        viewPager.setPagingEnabled(setPaging);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        temperatureView.setCurrentTemperature(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void setLidState(boolean isClosed) {
        lidStateView.setClosed(isClosed);//TODO
    }

    @Override
    public void setBaseData(RefreshData refreshData) {
        temperatureView.setCurrentTemperature(refreshData.getTemperature());
        lidStateView.setClosed(!refreshData.isLidOpen());
        batteryView.setmLevel(refreshData.getCharge());
        fullnessView.setmLevel(refreshData.getFullness());
    }

    private class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return CompassFragment.getInstance(0);
                case 1:
                    return JoystickFragment.getInstance(1);
                case 2:
                    return StelsFragment.getInstance(2);
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return Constants.Screens.MANUAL_COMPASS_SCREEN;
                case 1:
                    return Constants.Screens.MANUAL_JOYSTICK_SCREEN;
                case 2:
                    return Constants.Screens.MANUAL_STELS_SCREEN;
                default:
                    return null;
            }
        }

    }


}
