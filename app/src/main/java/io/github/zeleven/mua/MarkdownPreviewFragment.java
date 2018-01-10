package io.github.zeleven.mua;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

public class MarkdownPreviewFragment extends BaseFragment {
    @BindView(R.id.title) TextView title;
    @BindView(R.id.markdown_content) WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_markdown_preview;
    }

    @Override
    public void initView() {
        super.initView();
        setWebView();
    }

    @Subscribe
    public void onTitleChangedEvent(TitleChangedEvent event) {
        title.setText(event.title);
    }

    @Subscribe
    public void onContentChangedEvent(ContentChangedEvent event) {
        String content = event.content.replace("\n", "\\n").replace("\"", "\\\"")
                .replace("'", "\\'");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("parseMarkdown(\"" + content + "\");", null);
        } else {
            webView.loadUrl("javascript:parseMarkdown(\"" + content + "\");");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void setWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultFontSize(16);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(this, "handler");
        webView.loadUrl("file:///android_asset/markdown.html");
    }
}
