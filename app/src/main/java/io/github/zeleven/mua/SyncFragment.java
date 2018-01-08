package io.github.zeleven.mua;

import butterknife.BindString;

public class SyncFragment extends BaseFragment {
    @BindString(R.string.drawer_item_sync) String TITLE;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sync;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
    }
}
