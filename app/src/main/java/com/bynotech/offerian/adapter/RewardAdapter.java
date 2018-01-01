package com.bynotech.offerian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.bynotech.offerian.model.ReviewInfo;
import com.bynotech.offerian.model.RewardInfo;
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
        LinearLayout allOrderLayout;
        TextView tvReviewName,tvCompany,tvComment;
        RatingBar ratingBarReview;
        ImageView imgReviewUser;
        public MovieViewHolder(View v) {
            super(v);
//            imgReviewUser = (ImageView) v.findViewById(R.id.imgReviewUser);
//            tvReviewName = (TextView) v.findViewById(R.id.tvReviewName);
//            tvCompany = (TextView) v.findViewById(R.id.tvCompany);
//            tvComment = (TextView) v.findViewById(R.id.tvComment);
//            ratingBarReview = (RatingBar) v.findViewById(R.id.ratingBarReview);

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

        final RewardInfo offerData = listReviewInfo.get(position);




//        if(listReviewInfo.size()>0){
//            holder.tvReviewName.setText(offerData.getUser_name());
//            holder.tvCompany.setText(offerData.getCompany_name());
//            holder.tvComment.setText(offerData.getComment());
//            if(offerData.getUser_image()!=null){
//                Picasso.with(context).load(offerData.getUser_image()).into(holder.imgReviewUser);
//            }
//
//        }


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
