package io.github.zeleven.mua;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {
    private List<File> dataSet;
    private AppCompatActivity context;

    public FilesAdapter(List<File> filesList) {
        dataSet = (filesList == null) ? new ArrayList<File>() : filesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = (MainActivity) parent.getContext();
        }
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.file_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final File file = dataSet.get(position);
        final String fileName = file.getName();
        holder.fileName.setText(fileName);

        final StringBuilder content = getFileContent(file);
        if (content.length() == 0) {
            holder.fileContent.setVisibility(View.GONE);
        } else {
            holder.fileContent.setText(content);
        }

        holder.fileDate.setText(DateUtils.getRelativeTimeSpanString(file.lastModified(),
                System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open detail fragment

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName, fileContent, fileDate;

        public ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
            fileContent = itemView.findViewById(R.id.file_content);
            fileDate = itemView.findViewById(R.id.file_date);
        }
    }

    /**
     * Get text content from specify file.
     * @param file file object
     * @return text content from file.
     */
    private StringBuilder getFileContent(File file) {
        StringBuilder textContent = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                textContent.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(getClass().getName(), e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(getClass().getName(), e.getMessage());
        }
        return textContent;
    }
}
