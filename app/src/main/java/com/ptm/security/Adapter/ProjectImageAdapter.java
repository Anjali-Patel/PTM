package com.ptm.security.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ptm.security.Model.Company;
import com.ptm.security.Model.ProjectImageListModel;
import com.ptm.security.R;
import com.ptm.security.util.CircleImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.ptm.security.util.Globals.IMAGES_COMPANIES;
import static com.ptm.security.util.Globals.SORTING_URL;
import static com.ptm.security.util.Globals.pstPrms;
import static com.ptm.security.util.Globals.showAlert;

public class ProjectImageAdapter extends RecyclerView.Adapter<ProjectImageAdapter.MyViewHolder> {
     Context context;
    private List<ProjectImageListModel> projectImageList;

    public ProjectImageAdapter(Context context, List<ProjectImageListModel> projectImageList) {
        this.context = context;
        this.projectImageList = projectImageList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProjectImageListModel Items = projectImageList.get(position);
        Glide.with(context).load(Items.getProjectImage()).into(holder.image);
        holder.name_image.setText(Items.getProjectImage());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewgroup, final int position) {
        View layout = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.image_list_layout, viewgroup, false);
        MyViewHolder holder = new MyViewHolder(layout);

        return holder;

    }

    @Override
    public int getItemCount() {

        return projectImageList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
      ImageView image,download;
      TextView name_image;
        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            name_image=view.findViewById(R.id.name_image);
            download=view.findViewById(R.id.download);

        }
    }

}