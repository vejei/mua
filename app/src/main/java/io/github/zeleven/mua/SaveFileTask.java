package io.github.zeleven.mua;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class SaveFileTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String fileName;
    private String content;

    public SaveFileTask(Context context, String fileName, String content) {
        this.context = context;
        this.fileName = fileName;
        this.content = content;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (TextUtils.isEmpty(fileName)) {
            toastMessage(R.string.message_title_empty);
        } else {
            String filePath = Environment.getExternalStorageDirectory().toString()
                    + "/" + "Mua" + "/" + fileName + ".md";
            int resId = FileUtils.saveFile(filePath, fileName, content);
            if (resId != 0) {
                toastMessage(resId);
            }
        }
        return null;
    }

    public void toastMessage(final int resId) {
        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
            }
        });
    }
}
