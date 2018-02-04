package io.github.zeleven.mua;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;

public class HelpFragment extends BaseFragment {
    @BindString(R.string.drawer_item_help) String TITLE;
    @BindView(R.id.docs_webview) WebView webView;

    private SharedPreferences sharedPreferences;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_help;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        setWebView();
    }

    public void setWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultFontSize(16);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());
        String value = sharedPreferences.getString("language", "");
        Locale locale;
        if (value.equals("auto")) {
            locale = Locale.getDefault();
        } else {
            if (value.contains("_")) {
                String[] parts = value.split("_");
                locale = new Locale(parts[0], parts[1]);
            } else {
                locale = new Locale(value);
            }
        }
        webView.loadUrl("file:///android_asset/markdown-cheatsheet-"
                + locale.getLanguage() + ".html");
    }
}
