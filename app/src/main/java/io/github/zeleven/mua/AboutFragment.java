package io.github.zeleven.mua;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;

import butterknife.BindString;
import butterknife.BindView;

public class AboutFragment extends BaseFragment {
    @BindString(R.string.pref_title_about) String TITLE;

    @BindView(R.id.bottom_sheet) BottomSheetLayout bottomSheetLayout;
    @BindView(R.id.app_version_number) TextView appVersionNumber;
    @BindView(R.id.project_page_btn) Button projectPageBtn;
    @BindView(R.id.contact_btn) Button contactBtn;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            appVersionNumber.setText(versionName + " ( " + versionCode + " ) ");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(getClass().getName(), e.getMessage());
            e.printStackTrace();
        }

        projectPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUri(Constants.PROJECT_PAGE_URL);
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuSheet(MenuSheetView.MenuType.GRID);
            }
        });
    }

    public void showMenuSheet(MenuSheetView.MenuType menuType) {
        MenuSheetView menuSheetView = new MenuSheetView(context, menuType, "Contact me via...",
                new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.email:
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:" + Constants.MY_EMAIL));
                        startActivity(intent);
                        break;
                    case R.id.github:
                        openUri(Constants.MY_GITHUB);
                        break;
                    case R.id.zhihu:
                        openUri(Constants.MY_ZHIHU);
                        break;
                }
                if (bottomSheetLayout.isSheetShowing()) {
                    bottomSheetLayout.dismissSheet();
                }
                return true;
            }
        });
        menuSheetView.inflateMenu(R.menu.about_bottomsheet_menu);
        bottomSheetLayout.showWithSheetView(menuSheetView);
    }

    public void openUri(String uriString) {
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
