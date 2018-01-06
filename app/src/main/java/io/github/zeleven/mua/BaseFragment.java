package io.github.zeleven.mua;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected AppCompatActivity context; // context object
    protected View view; // fragment view object

    // If true, set back arrow in toolbar.
    protected boolean setDisplayHomeAsUpEnabled = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        context = (AppCompatActivity) getActivity();
        initView();
        return view;
    }

    /**
     * Get resource id of layout
     * @return resource id
     */
    public abstract int getLayoutId();

    /**
     * Get string resource id
     * @return string resource id
     */
    public abstract int getTitleResId();

    /**
     * Init view component
     */
    public void initView() {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getTitleResId());
        context.setSupportActionBar(toolbar);
        if (setDisplayHomeAsUpEnabled) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
