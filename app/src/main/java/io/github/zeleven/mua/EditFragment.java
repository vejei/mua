package io.github.zeleven.mua;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class EditFragment extends BaseFragment {
    @BindView(R.id.title_input) EditText titleInput;
    @BindView(R.id.content_input) EditText contentInput;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit;
    }

    @Override
    public void initView() {
        toolbarTitle = "";
        super.initView();
        addListenerForInput();
    }

    public void addListenerForInput() {
        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EventBus.getDefault().post(new TitleChangedEvent(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        contentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EventBus.getDefault().post(new ContentChangedEvent(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
