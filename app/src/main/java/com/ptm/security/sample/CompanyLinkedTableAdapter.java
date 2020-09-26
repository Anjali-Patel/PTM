package com.ptm.security.sample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter;
import com.cleveroad.adaptivetablelayout.ViewHolderImpl;
import com.ptm.security.Model.Company;
import com.ptm.security.R;

import static com.ptm.security.activities.Details2.CompaniesHeadings;
import static com.ptm.security.activities.Details2.CompaniesList;

public class CompanyLinkedTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

    private final LayoutInflater mLayoutInflater;
    private final int mColumnWidth;
    private final int mRowHeight;
    private final int mHeaderHeight;
    private final int mHeaderWidth;
    private final int mRowCount;
    private final int mColCount;
    public CompanyLinkedTableAdapter(Context context, int rowCount, int colCount) {
        mLayoutInflater = LayoutInflater.from(context);
        Resources res = context.getResources();
        mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width);
        mRowHeight = res.getDimensionPixelSize(R.dimen.row_height);
        mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height);
        mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width);
        mRowCount = rowCount;
        mColCount = colCount;
    }

    @Override
    public int getRowCount() {
        return mRowCount;
    }

    @Override
    public int getColumnCount() {
        return mColCount;
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
        return new TestViewHolder(mLayoutInflater.inflate(R.layout.item_card, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.item_header_row, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
        final TestViewHolder vh = (TestViewHolder) viewHolder;
        String itemData = "-"; // skip headers


        Company company=CompaniesList.get(row);

        switch (column) {

            case 1:
                itemData = company.getCompany_name();
                break;
            case 2:
                itemData = company.getCountry();
                break;
            case 3:
                itemData = company.getDescription();
                break;
            case 4:
                itemData = company.getHomepage();
                break;
            case 5:
                itemData = company.getContact();
                break;
            case 6:
                itemData = company.getFiles();
                break;
            case 7:
                itemData = company.getShort_name();
                break;
          /*  case 8:
                itemData = company.getFiles();
                break;
            case 9:
                itemData = company.getShort_name();
                break;*/
            default:
                break;

        }

        if (TextUtils.isEmpty(itemData))
            itemData = "";
        itemData = itemData.trim();
        vh.tvText.setVisibility(View.VISIBLE);
        vh.ivImage.setVisibility(View.VISIBLE);
        vh.tvText.setText(itemData);

        Glide.with(vh.ivImage.getContext())
                .load(itemData)
                .apply(new RequestOptions().transform(new FitCenter()))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        vh.ivImage.setVisibility(View.GONE);
                        vh.tvText.setVisibility(View.VISIBLE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        vh.ivImage.setVisibility(View.VISIBLE);
                        vh.tvText.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(vh.ivImage);
        if(row%2==0){
            vh.relative_layout1.setBackgroundColor(  Color.parseColor("#DDF5FE"));
        }else{

            vh.relative_layout1.setBackgroundColor(Color.parseColor("#fffcbb"));
        }

    }

    @Override
    public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, int column) {
        TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;
if(CompaniesHeadings.size()>column-1){
    vh.tvText.setText(CompaniesHeadings.get(column - 1));  // skip left top header

}


    }

    @Override
    public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
        TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;
        vh.tvText.setText(row + "");
//        vh.tvText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        vh.tvText.setSingleLine(true);
//        vh.tvText.setBackgroundColor( Color.parseColor("##DDF5FE"));

        if(row%2==0){
            vh.relative_layout.setBackgroundColor(  Color.parseColor("#DDF5FE"));
        }else{
            vh.relative_layout.setBackgroundColor(Color.parseColor("#fffcbb"));
        }





//        vh.tvText.setMarqueeRepeatLimit(-1);
//        vh.tvText.setSelected(true);
    }

    @Override
    public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
        TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;
        vh.tvText.setText("S.No");
    }

    @Override
    public int getColumnWidth(int column) {
        return mColumnWidth;
    }

    @Override
    public int getHeaderColumnHeight() {
        return mHeaderHeight;
    }

    @Override
    public int getRowHeight(int row) {
        return mRowHeight;
    }

    @Override
    public int getHeaderRowWidth() {
        return mHeaderWidth;
    }

    //------------------------------------- view holders ------------------------------------------

    private static class TestViewHolder extends ViewHolderImpl {
        TextView tvText;
        ImageView ivImage;
        RelativeLayout relative_layout,relative_layout1;

        private TestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            ivImage = itemView.findViewById(R.id.ivImage);
            relative_layout1=itemView.findViewById(R.id.relative_layout1);
            relative_layout=itemView.findViewById(R.id.relative_layout);
        }
    }

    private static class TestHeaderColumnViewHolder extends ViewHolderImpl {
        TextView tvText;
        RelativeLayout relative_layout,relative_layout1;


        private TestHeaderColumnViewHolder(@NonNull View itemView) {
            super(itemView);
            relative_layout1=itemView.findViewById(R.id.relative_layout1);
            tvText = itemView.findViewById(R.id.tvText);
            relative_layout=itemView.findViewById(R.id.relative_layout);
        }
    }

    private static class TestHeaderRowViewHolder extends ViewHolderImpl {
        TextView tvText;
        RelativeLayout relative_layout,relative_layout1;

        TestHeaderRowViewHolder(@NonNull View itemView) {
            super(itemView);
            relative_layout1=itemView.findViewById(R.id.relative_layout1);
            tvText = itemView.findViewById(R.id.tvText);
            relative_layout=itemView.findViewById(R.id.relative_layout);
        }
    }

    private static class TestHeaderLeftTopViewHolder extends ViewHolderImpl {
        TextView tvText;
        RelativeLayout relative_layout,relative_layout1;

        private TestHeaderLeftTopViewHolder(@NonNull View itemView) {
            super(itemView);
            relative_layout1=itemView.findViewById(R.id.relative_layout1);
            tvText = itemView.findViewById(R.id.tvText);
            relative_layout=itemView.findViewById(R.id.relative_layout);
        }
    }
}
