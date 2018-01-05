package io.github.zeleven.mua;

import butterknife.BindString;

public class SyncFragment extends BaseFragment {

    @BindString(R.string.drawer_item_sync)
    String title;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sync;
    }

    @Override
    public void initView() {
        fragmentToolbarTitle = title;
        super.initView();
    }
}
