package com.bynotech.offerian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bynotech.offerian.R;
import com.bynotech.offerian.model.BusinessInfo;
import com.bynotech.offerian.model.OfferInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sadi on 12/1/2017.
 */

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MovieViewHolder> {
    int lastPosition = -1;
    private List<BusinessInfo> listBusinessInfo;
    //private List<OrderData> ordersPdf;
    private int rowLayout;
    private Context context;
    TextView tvGrandTotal;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout allOrderLayout;
        TextView tvCompanyName,tvBusinessCategory;
        ImageView imgCompanyLogo,imgBusinessVerify;
        public MovieViewHolder(View v) {
            super(v);
            imgCompanyLogo = (ImageView) v.findViewById(R.id.imgCompanyLogo);
            imgBusinessVerify = (ImageView) v.findViewById(R.id.imgBusinessVerify);
            tvCompanyName = (TextView) v.findViewById(R.id.tvCompanyName);
            tvBusinessCategory = (TextView) v.findViewById(R.id.tvBusinessCategory);

        }
    }



    public BusinessAdapter(List<BusinessInfo> listBusinessInfo, int rowLayout, Context context) {
        this.listBusinessInfo = listBusinessInfo;
        //this.ordersPdf = ordersPdf;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {



        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        final BusinessInfo businessData = listBusinessInfo.get(position);

        holder.tvCompanyName.setText(businessData.getName());
        holder.tvBusinessCategory.setText(businessData.getBusiness_category());


        if(listBusinessInfo.size()>0){

            if(businessData.getLogo_url()!=null){
                Picasso.with(context).load(businessData.getLogo_url()).into(holder.imgCompanyLogo);
            }

//            if(offerData.getCompany_logo()!=null){
//                Picasso.with(context).load(offerData.getCompany_logo()).into(holder.imgLogo);
//            }
        }


//        holder.allOrderLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPdf(orderHistoryData.getCustomerName()+".pdf", "Sale Report");
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listBusinessInfo.size();
    }


    @Override
    public void onViewDetachedFromWindow(MovieViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.itemView.clearAnimation();
    }
}
