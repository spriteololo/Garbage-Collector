package by.brstu.dmitry.garbagecollector.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import by.brstu.dmitry.garbagecollector.R;
import by.brstu.dmitry.garbagecollector.ui.all.base.BaseMvpFragment;


public class LoginFragment extends BaseMvpFragment implements View.OnClickListener {

    @BindView(R.id.activity_login_admin_button)
    FrameLayout adminBtn;

    @BindView(R.id.activity_login_user_button)
    FrameLayout userBtn;

    @BindView(R.id.activity_login_guest_button)
    FrameLayout guestBtn;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        return v;
    }

    @Override
    protected void onViewsBinded() {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.login);
        }

        adminBtn.setOnClickListener(this);
        userBtn.setOnClickListener(this);
        guestBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.activity_login_admin_button:
                ((LoginInterface) getActivity()).setDrawerChangingGroupsVisibility(0);
                break;
            case R.id.activity_login_user_button:
                ((LoginInterface) getActivity()).setDrawerChangingGroupsVisibility(1);
                break;
            case R.id.activity_login_guest_button:
                ((LoginInterface) getActivity()).setDrawerChangingGroupsVisibility(2);
                break;
        }
    }
}