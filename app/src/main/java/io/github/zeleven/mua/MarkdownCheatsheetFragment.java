package io.github.zeleven.mua;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindString;
import butterknife.BindView;

public class MarkdownCheatsheetFragment extends BaseFragment {
    @BindView(R.id.docs_webview) WebView docsWebView;
    @BindString(R.string.docs_fragment_tooblar_title) String TITLE;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_markdown_cheatsheet;
    }

    @Override
    public void initView() {
        toolbarTitle = TITLE;
        super.initView();
        setHasOptionsMenu(false);
        WebSettings webSettings = docsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultFontSize(16);

        docsWebView.setVerticalScrollBarEnabled(false);
        docsWebView.setHorizontalScrollBarEnabled(false);
        docsWebView.setWebChromeClient(new WebChromeClient());
        docsWebView.loadUrl("file:///android_asset/markdown-cheatsheet.html");
    }
}
