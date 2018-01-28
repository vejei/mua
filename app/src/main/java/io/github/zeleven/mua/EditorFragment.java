package io.github.zeleven.mua;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class EditorFragment extends BaseEditorFragment {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editor_viewpager) ViewPager editorViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_editor;
    }

    @Override
    public void initView() {
        getArgs();
        super.initView();
        toolbar.setTitle("");
        context.setSupportActionBar(toolbar);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        setViewPager();
        setViewPagerListener();
    }

    public void getArgs() {
        Bundle args = getArguments();
        if (args != null) {
            fromFile = args.getBoolean(Constants.BUNDLE_KEY_FROM_FILE);
            if (fromFile) {
                saved = args.getBoolean(Constants.BUNDLE_KEY_SAVED);
                fileName = args.getString(Constants.BUNDLE_KEY_FILE_NAME);
                filePath = args.getString(Constants.BUNDLE_KEY_FILE_PATH);
                if (filePath != null) {
                    fileContent = FileUtils.readContentFromPath(filePath, true);
                }
            }
        }
    }

    public void setViewPager() {
        final ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(
                getChildFragmentManager());
        editorViewPager.setAdapter(adapter);
    }

    public void setViewPagerListener() {
        editorViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    EventBus.getDefault().post(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
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
                fragment = new PreviewFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditorAction editorAction = new EditorAction(context);
        switch (item.getItemId()) {
            case R.id.preview:
                // switch to preview page
                editorAction.toggleKeyboard(0);
                editorViewPager.setCurrentItem(1, true);
                break;
            case R.id.edit:
                // switch to edit page
                editorViewPager.setCurrentItem(0, true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
