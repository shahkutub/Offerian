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
import android.widget.RatingBar;
import android.widget.TextView;

import com.bynotech.offerian.R;
import com.bynotech.offerian.model.OfferInfo;
import com.bynotech.offerian.model.ReviewInfo;
import com.bynotech.offerian.model.RewardInfo;
import com.bynotech.offerian.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sadi on 12/1/2017.
 */

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MovieViewHolder> {
    int lastPosition = -1;
    private List<RewardInfo> listReviewInfo;
    //private List<OrderData> ordersPdf;
    private int rowLayout;
    private Context context;
    TextView tvGrandTotal;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemType,tvOfferLft,tvDescription,tvPoint,tvMore,tvCamp_ribon;
        ImageView img_product_photo,imgLogo,imgLocaOrDalyver;
        public MovieViewHolder(View v) {
            super(v);
            img_product_photo = (ImageView) v.findViewById(R.id.img_product_photo);
            imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
            imgLocaOrDalyver = (ImageView) v.findViewById(R.id.imgLocaOrDalyver);
            tvItemType = (TextView) v.findViewById(R.id.tvItemType);
            tvOfferLft = (TextView) v.findViewById(R.id.tvOfferLft);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvPoint = (TextView) v.findViewById(R.id.tvPoint);
            tvMore = (TextView) v.findViewById(R.id.tvMore);
            tvCamp_ribon = (TextView) v.findViewById(R.id.tvCamp_ribon);
        }
    }



    public RewardAdapter(List<RewardInfo> listReviewInfo, int rowLayout, Context context) {
        this.listReviewInfo = listReviewInfo;
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

        final RewardInfo data = listReviewInfo.get(position);

        holder.tvItemType.setText(data.getItem_type());
        holder.tvOfferLft.setText(data.getOffer_left()+" left only");
        holder.tvDescription.setText(data.getDescription());
        holder.tvPoint.setText(data.getReward_point()+" points");
        holder.tvDescription.setText(data.getDescription());

//        if(!TextUtils.isEmpty(data.getCampaign_ribon())){
//            holder.tvCamp_ribon.setVisibility(View.VISIBLE);
//            holder.tvCamp_ribon.setText(offerData.getCampaign_ribon());
//        }else {
//            holder.tvCamp_ribon.setVisibility(View.GONE);
//        }
        if(data.getDelivery_type().equalsIgnoreCase("1")){
           holder.imgLocaOrDalyver.setImageResource(R.drawable.ic_autorenew_white_24dp);
        }else if(data.getDelivery_type().equalsIgnoreCase("2")){
            holder.imgLocaOrDalyver.setImageResource(R.drawable.ic_directions_bike_white_48dp);
        }

        if(listReviewInfo.size()>0){

            if(data.getProduct_photo()!=null){
                Picasso.with(context).load(data.getProduct_photo()).into(holder.img_product_photo);
            }

            if(data.getCompany_logo()!=null){
                Picasso.with(context).load(data.getCompany_logo()).into(holder.imgLogo);
            }
        }

        holder.img_product_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.offerId = data.getOffer_id();
                //getOfferById(offerData.getOffer_id());
//                Intent intent = new Intent(context, SignInActivity.class);
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listReviewInfo.size();
    }


    @Override
    public void onViewDetachedFromWindow(MovieViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.itemView.clearAnimation();
    }
}
