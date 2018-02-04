package io.github.zeleven.mua;

import android.os.AsyncTask;

import java.util.List;

public class QueryTask extends AsyncTask<Void, Void, List<FileEntity>> {
    private String filePath;
    private String query;
    private Response response;

    public QueryTask(String filePath, String query, Response response) {
        this.filePath = filePath;
        this.query = query;
        this.response = response;
    }

    @Override
    protected List<FileEntity> doInBackground(Void... params) {
        if (query == null) {
            return FileUtils.listFiles(filePath);
        } else {
            return FileUtils.searchFiles(filePath, query);
        }
    }

    @Override
    protected void onPostExecute(List<FileEntity> entityList) {
        response.onTaskFinish(entityList);
    }

    public interface Response {
        void onTaskFinish(List<FileEntity> entityList);
    }
}
