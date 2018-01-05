package io.github.zeleven.mua;

public class FileListFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_filelist;
    }

    @Override
    public void initView() {
        displayHomeAsUpEnabled = false;
        super.initView();
    }
}
