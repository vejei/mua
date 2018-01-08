package io.github.zeleven.mua;

import butterknife.BindString;

public class WordCloudFragment extends BaseFragment {
    @BindString(R.string.drawer_item_wordcloud) String TITLE;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wordcloud;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
    }
}
