package io.github.zeleven.mua;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {
    private List<Object> dataSet;
    private AppCompatActivity appCompatActivity;

    public FilesAdapter(List<Object> filesList) {
        dataSet = filesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (appCompatActivity != null) {
            appCompatActivity = (MainActivity) parent.getContext();
        }
        View itemView = LayoutInflater.from(appCompatActivity)
                .inflate(R.layout.file_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Object file = dataSet.get(position);
        holder.fileName.setText("");
        holder.fileContent.setText("");
        holder.fileDate.setText("");
        holder.fileTypeName.setText("");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCompatActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FileDetailFragment()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName, fileContent, fileDate, fileTypeName;
        ImageView fileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
            fileContent = itemView.findViewById(R.id.file_content);
            fileImage = itemView.findViewById(R.id.file_image);
            fileDate = itemView.findViewById(R.id.file_date);
            fileTypeName = itemView.findViewById(R.id.file_type_name);
        }
    }
}
