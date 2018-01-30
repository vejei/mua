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
    private boolean pageFinish = false;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_preview;
    }

    @Override
    public void initView() {
        super.initView();
        setHasOptionsMenu(true);
        configWebView();
    }

    @Subscribe
    public void onContentChangedEvent(ContentChangedEvent event) {
        if (event.content != null && !event.content.equals("")) {
            loadMarkdown(event.content);
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
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pageFinish = true;
                }
            }
        });
        webView.loadUrl("file:///android_asset/markdown.html");
    }

    public void loadMarkdown(String markdown) {
        if (pageFinish) {
            String content = markdown.replace("\n", "\\n").replace("\"", "\\\"")
                    .replace("'", "\\'");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript("javascript:parseMarkdown(\"" + content + "\");", null);
            } else {
                webView.loadUrl("javascript:parseMarkdown(\"" + content + "\");");
            }
        }
    }
}
