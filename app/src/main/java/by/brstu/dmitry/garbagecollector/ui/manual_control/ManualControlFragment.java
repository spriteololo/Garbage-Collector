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
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.application.BaseApplication;
import by.brstu.dmitry.garbagecollector.application.Constants;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.joystick_mode.JoystickFragment;
import by.brstu.dmitry.garbagecollector.ui.manual_control.stels_mode.StelsFragment;
import by.brstu.dmitry.garbagecollector.ui.pagerTab.CustomPagerBar;
import by.brstu.dmitry.garbagecollector.ui.viewPager.CustomViewPager;


public class ManualControlFragment extends BaseMvpFragment implements ManualControlView{

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
    protected void onViewsBinded() {
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.manual_control);
        }

        pagerTab.setGuideline(guideLine);
    }

    public void setPaging(final boolean setPaging) {
        viewPager.setPagingEnabled(setPaging);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return StelsFragment.getInstance(0);
                case 1:
                    return JoystickFragment.getInstance(1);
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return Constants.Screens.MANUAL_STELS_SCREEN;
                case 1:
                    return Constants.Screens.MANUAL_JOYSTICK_SCREEN;
                default:
                    return null;
            }
        }

    }


}
