package io.github.zeleven.mua;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
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
        private AppCompatActivity context;
        private SharedPreferences sharedPref;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
            context = (AppCompatActivity) getContext();
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            settingPreferences();
        }

        /**
         * Preference item setting
         */
        public void settingPreferences() {
            final SharedPreferences.Editor editor = sharedPref.edit();

            // language setting
            final ListPreference languagePref = (ListPreference) findPreference("language");
            if (languagePref != null) {
                final CharSequence[] entries = languagePref.getEntries();
                String value = sharedPref.getString("language", "");
                int index = languagePref.findIndexOfValue(value);
                languagePref.setSummary(entries[index]);
                languagePref.setOnPreferenceChangeListener(
                        new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        editor.putString("language", (String) newValue).commit();
                        languagePref.setSummary(entries[languagePref.findIndexOfValue(
                                (String) newValue)]);
                        Intent intent = new Intent(context, MainActivity.class);
                        context.finish();
                        startActivity(intent);
                        return true;
                    }
                });
            }

            // drawer header image and text setting
//            Preference drawerHeaderPref = findPreference("drawer_header");
//            drawerHeaderPref.setOnPreferenceClickListener(
//                    new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    return true;
//                }
//            });

            // file extension setting
            final ListPreference fileExtPref = (ListPreference) findPreference("file_extension");
            if (fileExtPref != null) {
                int index = fileExtPref.findIndexOfValue(sharedPref.getString("file_extension", ""));
                final CharSequence[] entries = fileExtPref.getEntries();
                fileExtPref.setSummary(entries[index]);
                fileExtPref.setOnPreferenceChangeListener(
                        new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        editor.putString("file_extension", (String) newValue).commit();
                        int newIndex = fileExtPref.findIndexOfValue((String) newValue);
                        fileExtPref.setSummary(entries[newIndex]);
                        return true;
                    }
                });
            }

            // sync options setting
            final ListPreference syncPref = (ListPreference) findPreference("sync");
            if (syncPref != null) {
                int index = syncPref.findIndexOfValue(sharedPref.getString("sync", ""));
                final CharSequence[] entries = syncPref.getEntries();
                syncPref.setSummary(entries[index]);
                syncPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        editor.putString("sync", (String) newValue).commit();
                        int newIndex = syncPref.findIndexOfValue((String) newValue);
                        syncPref.setSummary(entries[newIndex]);
                        return true;
                    }
                });
            }

            // network setting
            final CheckBoxPreference networkPref = (CheckBoxPreference) findPreference("network");
            if (networkPref != null) {
                boolean isChecked = sharedPref.getBoolean("network", true);
                networkPref.setChecked(isChecked);
                if (isChecked) {
                    networkPref.setSummary(R.string.pref_summary_wifi_sync);
                } else {
                    networkPref.setSummary(R.string.pref_summary_4g_sync);
                }
                networkPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        editor.putBoolean("network", (Boolean) newValue).commit();
                        networkPref.setChecked((Boolean) newValue);
                        if ((Boolean) newValue) {
                            networkPref.setSummary(R.string.pref_summary_wifi_sync);
                        } else {
                            networkPref.setSummary(R.string.pref_summary_4g_sync);
                        }
                        return false;
                    }
                });
            }

            // update preference setting, setting version name and version code
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
            Preference versionPref = findPreference("check_update");
            String appVersionText = getResources().getString(R.string.app_version_text);
            versionPref.setSummary(appVersionText + " " + versionName
                    + " ( " + versionCode + " ) ");

            // About preference setting, open AboutFragment on preference click
            Preference aboutPref = findPreference("about");
            aboutPref.setOnPreferenceClickListener(
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
