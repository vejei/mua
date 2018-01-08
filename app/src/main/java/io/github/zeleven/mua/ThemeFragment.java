package io.github.zeleven.mua;

import butterknife.BindString;

public class ThemeFragment extends BaseFragment {
    @BindString(R.string.drawer_item_theme_switch) String TITLE;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_theme;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
    }
}
