package io.github.zeleven.mua;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class SaveFileTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private String filePath;
    private String fileName;
    private String content;
    private Response response;

    public SaveFileTask(Context context, String filePath, String fileName, String content,
                        Response response) {
        this.context = context;
        this.filePath = filePath;
        this.fileName = fileName;
        this.content = content;
        this.response = response;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean result;
        if (TextUtils.isEmpty(fileName)) {
            toastMessage(R.string.toast_file_name_can_not_empty);
            result = false;
        } else {
            result = FileUtils.saveFile(filePath, content);
            if (result) {
                toastMessage(R.string.toast_saved);
            } else {
                toastMessage(R.string.toast_file_name_exists);
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        response.taskFinish(aBoolean);
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

    public interface Response {
        void taskFinish(Boolean result);
    }
}
