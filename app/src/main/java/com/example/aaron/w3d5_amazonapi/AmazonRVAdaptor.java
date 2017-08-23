package com.example.aaron.w3d5_amazonapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aaron.w3d5_amazonapi.model.AmazonProfile;

import java.util.List;

/**
 * Created by aaron on 8/19/17.
 */

public class AmazonRVAdaptor extends RecyclerView.Adapter<AmazonRVAdaptor.ViewHolder> {
   List<AmazonProfile> passedAmazonList;
    Context context;

    public AmazonRVAdaptor(List<AmazonProfile> passedAmazonList, Context context) {
        this.passedAmazonList = passedAmazonList;
        this.context = context;
    }

    /**********************************
     *  CLASS View Holder            **
     **********************************/
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBookImage;
        TextView tvBookName;
        TextView tvAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            imgBookImage = (ImageView)itemView.findViewById(R.id.ivAmazonItem);
            tvBookName = (TextView)itemView.findViewById(R.id.tvItemName);
            tvAuthor = (TextView)itemView.findViewById(R.id.tvAuthor);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amazon_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AmazonProfile amazonProfile = passedAmazonList.get(position);
        Log.d("TAG", "onBindViewHolder: " + amazonProfile.getAuthor());
        holder.tvAuthor.setText(amazonProfile.getAuthor());
        holder.tvBookName.setText(amazonProfile.getTitle().toString());
        try {
            Glide.with(context.getApplicationContext())
                    .load(amazonProfile.getImageURL())
                    .asBitmap()
                    .into(holder.imgBookImage) ;
        } catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return passedAmazonList.size();
    }


}
