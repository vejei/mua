package io.github.zeleven.mua;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

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

            Preference drawerHeaderPreference = findPreference("drawer_header");
            drawerHeaderPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    return true;
                }
            });

            Preference aboutPreference = findPreference("about");
            aboutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    return true;
                }
            });
        }
    }
}
