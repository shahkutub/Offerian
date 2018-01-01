package com.bynotech.offerian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import com.bynotech.offerian.model.OfferDetails;
import com.bynotech.offerian.model.OfferInfo;
import com.bynotech.offerian.retrofit.Api;
import com.bynotech.offerian.utils.AlertMessage;
import com.bynotech.offerian.utils.AppConstant;
import com.bynotech.offerian.utils.NetInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Sadi on 12/1/2017.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MovieViewHolder> {
    int lastPosition = -1;
    private List<OfferInfo> listOfferInfo;
    //private List<OrderData> ordersPdf;
    private int rowLayout;
    private Context context;
    TextView tvGrandTotal;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout allOrderLayout;
        TextView tvItemType,tvOfferLft,tvDescription,tvPoint,tvMore,tvCamp_ribon;
        ImageView img_product_photo,imgLogo;
        public MovieViewHolder(View v) {
            super(v);
            img_product_photo = (ImageView) v.findViewById(R.id.img_product_photo);
            imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
            tvItemType = (TextView) v.findViewById(R.id.tvItemType);
            tvOfferLft = (TextView) v.findViewById(R.id.tvOfferLft);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvPoint = (TextView) v.findViewById(R.id.tvPoint);
            tvMore = (TextView) v.findViewById(R.id.tvMore);
            tvCamp_ribon = (TextView) v.findViewById(R.id.tvCamp_ribon);
        }
    }



    public OfferAdapter(List<OfferInfo> listOfferInfo, int rowLayout, Context context) {
        this.listOfferInfo = listOfferInfo;
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

        final OfferInfo offerData = listOfferInfo.get(position);

        holder.tvItemType.setText(offerData.getItem_type());
        holder.tvOfferLft.setText(offerData.getOffer_left()+" left only");
        holder.tvDescription.setText(offerData.getDescription());
        holder.tvPoint.setText(offerData.getReward_point()+" points");

        if(!TextUtils.isEmpty(offerData.getCampaign_ribon())){
            holder.tvCamp_ribon.setVisibility(View.VISIBLE);
            holder.tvCamp_ribon.setText(offerData.getCampaign_ribon());
        }else {
            holder.tvCamp_ribon.setVisibility(View.GONE);
        }

        if(listOfferInfo.size()>0){

            if(offerData.getProduct_photo()!=null){
                Picasso.with(context).load(offerData.getProduct_photo()).into(holder.img_product_photo);
            }

            if(offerData.getCompany_logo()!=null){
                Picasso.with(context).load(offerData.getCompany_logo()).into(holder.imgLogo);
            }
        }

        holder.img_product_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.offerId = offerData.getOffer_id();
                getOfferById(offerData.getOffer_id());
//                Intent intent = new Intent(context, SignInActivity.class);
//                context.startActivity(intent);
            }
        });
//        holder.allOrderLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPdf(orderHistoryData.getCustomerName()+".pdf", "Sale Report");
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listOfferInfo.size();
    }


    @Override
    public void onViewDetachedFromWindow(MovieViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.itemView.clearAnimation();
    }


    private void getOfferById(String offerId) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert","No internet connection!");
        }

//        final BusyDialog busyNow = new BusyDialog(con, true,false);
//        busyNow.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<OfferDetails> call = api.getOfferData(offerId);

        call.enqueue(new Callback<OfferDetails>() {
            @Override
            public void onResponse(Call<OfferDetails> call, Response<OfferDetails> response) {
                OfferDetails offerDetails = response.body();


                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(context, VERTICAL, false);
//                recyclerviewOffer.setLayoutManager(layoutManager);
//                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(con,
//                        layoutManager.getOrientation());
//                dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_line));
//
//                recyclerviewOffer.addItemDecoration(dividerItemDecoration);
//                adapter = new OfferAdapter(offerInfoList,R.layout.raw_offer,con);
//
//                recyclerviewOffer.setAdapter(adapter);;
//                for (int i = 0; i < offerInfoList.size(); i++) {
//                    //heroes[i] = heroList.get(i).getName_en();
//                    Log.e("Company name",""+offerInfoList.get(i).getCompany_name());
//                }


                // busyNow.dismis();
                //displaying the string array into listview

            }

            @Override
            public void onFailure(Call<OfferDetails> call, Throwable t) {
                //busyNow.dismis();
            }

        });
    }


}
