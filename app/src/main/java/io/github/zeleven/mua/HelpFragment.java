package io.github.zeleven.mua;

import butterknife.BindString;

public class HelpFragment extends BaseFragment {
    @BindString(R.string.drawer_item_help) String TITLE;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_help;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
    }
}
