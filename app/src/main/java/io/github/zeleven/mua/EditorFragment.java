package io.github.zeleven.mua;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import butterknife.BindView;

public class EditorFragment extends BaseFragment {
    @BindView(R.id.editor_viewpager) ViewPager editorViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_editor;
    }

    @Override
    public void initView() {
        toolbarTitle = "";
        super.initView();
        final ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(
                context.getSupportFragmentManager());
        editorViewPager.setAdapter(adapter);
        editorViewPager.setCurrentItem(0);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position == 0) {
                fragment = new EditFragment();
            } else {
                fragment = new MarkdownPreviewFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
