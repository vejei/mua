package io.github.zeleven.mua;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindString;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    protected AppCompatActivity appCompatActivity;
    protected boolean displayHomeAsUpEnabled = true;
    @BindString(R.string.app_name)
    protected String toolbarTitle;
    protected String fragmentToolbarTitle;
    protected ActionBar actionBar;
    protected Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        appCompatActivity = (AppCompatActivity) getActivity();
        actionBar = appCompatActivity.getSupportActionBar();
        initView();
        return view;
    }

    public abstract int getLayoutId();

    public void initView() {
        setHasOptionsMenu(false);
        if (displayHomeAsUpEnabled) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appCompatActivity.onBackPressed();
                }
            });
        }
        if (fragmentToolbarTitle != null) {
            actionBar.setTitle(fragmentToolbarTitle);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (displayHomeAsUpEnabled) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        actionBar.setTitle(toolbarTitle);
        setHasOptionsMenu(true);
    }
}
