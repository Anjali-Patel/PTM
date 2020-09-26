package com.ptm.security.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ptm.security.Model.CompanyImageListModel;
import com.ptm.security.Model.ProjectImageListModel;
import com.ptm.security.R;

import java.util.List;

public class CompanyImageAdapter extends RecyclerView.Adapter<CompanyImageAdapter.MyViewHolder> {
    Context context;
    private List<CompanyImageListModel> companyimagelist;

    public CompanyImageAdapter(Context context, List<CompanyImageListModel> companyimagelist) {
        this.context = context;
        this.companyimagelist = companyimagelist;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CompanyImageListModel Items = companyimagelist.get(position);
        Glide.with(context).load(Items.getImage_name()).error(R.drawable.no_data_found).into(holder.image);
        holder.name_image.setText(Items.getImage_name());
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

        return companyimagelist.size();
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