package io.github.zeleven.mua;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import butterknife.BindString;

public class SettingsFragment extends BaseFragment {
    @BindString(R.string.drawer_item_settings) String TITLE;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
        getFragmentManager().beginTransaction()
                .add(R.id.pref_container, new PreferenceFragmentCustom())
                .commit();
    }

    public static class PreferenceFragmentCustom extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences);

            final AppCompatActivity context = (AppCompatActivity) getContext();

            // open AboutFragment on preference click
            Preference drawerHeaderPreference = findPreference("drawer_header");
            drawerHeaderPreference.setOnPreferenceClickListener(
                    new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    return true;
                }
            });

            // get version name and version code
            String versionName = "";
            int versionCode = 1;
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0);
                versionName = packageInfo.versionName;
                versionCode = packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(getClass().getName(), e.getMessage());
                e.printStackTrace();
            }
            // set version name and version code for preference item's summary
            Preference versionPreference = findPreference("check_update");
            String appVersionText = getResources().getString(R.string.app_version_text);
            versionPreference.setSummary(appVersionText + " " + versionName
                    + " ( " + versionCode + " ) ");

            // open AboutFragment on preference click
            Preference aboutPreference = findPreference("about");
            aboutPreference.setOnPreferenceClickListener(
                    new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    context.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new AboutFragment())
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
            });
        }
    }
}
