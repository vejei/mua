package io.github.zeleven.mua;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {
    private List<Object> dataSet;
    private AppCompatActivity context;

    public FilesAdapter(List<Object> filesList) {
        dataSet = filesList;
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
//        File file = dataSet.get(position);
//        holder.fileName.setText(file.getName());
//        holder.fileContent.setText(getFileContent(file));
//        holder.fileDate.setText(getCreationTime(file.getAbsolutePath()).toString());
//        holder.fileTypeName.setText("Markdown");
        MarkdownFile md = (MarkdownFile) dataSet.get(position);
        holder.fileName.setText(md.getName());
        holder.fileContent.setText(md.getContent());
        holder.fileDate.setText(md.getDate());
        holder.fileTypeName.setText("Markdown");
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
        TextView fileName, fileContent, fileDate, fileTypeName;

        public ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
            fileContent = itemView.findViewById(R.id.file_content);
            fileDate = itemView.findViewById(R.id.file_date);
            fileTypeName = itemView.findViewById(R.id.file_type_name);
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

    /**
     * Get creation time of file from specify file path.
     * @param filePath absolute path of file.
     * @return creation time fo file.
     */
    private FileTime getCreationTime(String filePath) {
        Path path = Paths.get(filePath);
        BasicFileAttributes attributes = null;
        try {
            attributes = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attributes.creationTime();
    }
}
