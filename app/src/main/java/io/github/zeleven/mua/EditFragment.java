package io.github.zeleven.mua;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.undo:
                break;
            case R.id.redo:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
