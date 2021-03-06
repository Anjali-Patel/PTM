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
import com.ptm.security.Model.EquipmentImageListModel;
import com.ptm.security.R;

import java.util.List;

public class EquipmentImageAdapter   extends RecyclerView.Adapter<EquipmentImageAdapter.MyViewHolder> {
    Context context;
    private List<EquipmentImageListModel> equipmentImageListModelList;

    public EquipmentImageAdapter(Context context, List<EquipmentImageListModel> equipmentImageListModelList) {
        this.context = context;
        this.equipmentImageListModelList = equipmentImageListModelList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EquipmentImageListModel Items = equipmentImageListModelList.get(position);
        Glide.with(context).load(Items.getEquipmentImage()).error(R.drawable.no_data_found).into(holder.image);
        holder.name_image.setText(Items.getEquipmentImage());
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

        return equipmentImageListModelList.size();
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