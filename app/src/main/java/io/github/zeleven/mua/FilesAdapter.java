package io.github.zeleven.mua;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
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

        final String content = FileUtils.readContentFromFile(file);
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
                Fragment fragment = new EditorFragment();
                Bundle args = new Bundle();
                args.putBoolean(Constants.BUNDLE_KEY_SAVED, true);
                args.putBoolean(Constants.BUNDLE_KEY_FROM_FILE, true);
                args.putString(Constants.BUNDLE_KEY_FILE_NAME,
                        FileUtils.stripExtension(file.getName()));
                args.putString(Constants.BUNDLE_KEY_FILE_PATH, file.getAbsolutePath());
                fragment.setArguments(args);
                context.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
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
}
