package by.brstu.dmitry.garbagecollector.ui.auto_moving;

import android.os.Bundle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;

public class AutoMovingFragment extends BaseMvpFragment {
    public static AutoMovingFragment getInstance() {
        return new AutoMovingFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_auto_moving, container, false);
        return v;
    }

    @Override
    protected void onViewsBinded() {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.auto_moving);
        }

    }
}
