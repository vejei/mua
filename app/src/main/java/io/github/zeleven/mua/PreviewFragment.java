package io.github.zeleven.mua;

import android.view.Menu;
import android.view.MenuInflater;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

public class PreviewFragment extends BaseEditorFragment {
    @BindView(R.id.markdown_content) WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_markdown_preview;
    }

    @Override
    public void initView() {
        super.initView();
        setHasOptionsMenu(true);

        configWebView();
        if (fileContent != null) {
            String content = fileContent.replace("\n", "\\n").replace("\"", "\\\"")
                    .replace("'", "\\'");
            webView.loadUrl("javascript:parseMarkdown(\"" + content + "\");");
        }
    }

    @Subscribe
    public void onContentChangedEvent(ContentChangedEvent event) {
        if (event.content != null && !event.content.equals("")) {
            String content = event.content.replace("\n", "\\n").replace("\"", "\\\"")
                    .replace("'", "\\'");
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.preview_fragment_menu, menu);
    }

    public void configWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/markdown.html");
    }
}
