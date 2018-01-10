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
    protected Toolbar toolbar;
    protected String toolbarTitle;

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
     * Init view component
     */
    public void initView() {
        toolbar = view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            if (toolbarTitle != null) {
                toolbar.setTitle(toolbarTitle);
            }
            context.setSupportActionBar(toolbar);
            if (setDisplayHomeAsUpEnabled) {
                context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }
}
