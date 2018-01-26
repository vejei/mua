package io.github.zeleven.mua;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseEditorFragment extends Fragment {
    protected AppCompatActivity context;

    protected static boolean saved = false;
    protected static boolean fromFile = false;
    protected static String fileName;
    protected static String filePath;
    protected static String fileContent;
    protected static String fileExtension;
    protected static SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        context = (AppCompatActivity) getContext();
        initView();
        return view;
    }

    public void initView() {
        if (!fromFile) {
            saved = false;
            fileName = null;
            filePath = null;
            fileContent = null;
        }
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        fileExtension = getFileExt(sharedPref.getString("file_extension", ""));
    }

    public String getFileExt(String value) {
        Resources res = context.getResources();
        String[] items = res.getStringArray(R.array.extension_list);
        String fileExt;
        switch (value) {
            case "0":
                fileExt = items[0];
                break;
            case "1":
                fileExt = items[1];
                break;
            case "2":
                fileExt = items[2];
                break;
            default:
                fileExt = items[0];
        }
        return fileExt;
    }

    public abstract int getLayoutId();
}
