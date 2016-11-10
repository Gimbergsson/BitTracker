package com.free.dennisg.bittrackr.api;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TxsAdapter extends RecyclerView.Adapter<TxsAdapter.MyViewHolder> {
    private String[] mDate;
    private String[] mTitle;
    private String[] mUserImageUrl;
    private String[] mSavedImages;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleText;
        RoundedImageView userImage;
        RelativeTimeTextView timeText;
        Button followBackButton;
        ImageView image1, image2, image3, image4, imageOverflow;

        List<Txs> txsList;

        MyViewHolder(View view) {
            super(view);

            timeText = (RelativeTimeTextView) view.findViewById(R.id.time_txt);
            titleText = (TextView) view.findViewById(R.id.title_txt);
            userImage = (RoundedImageView) view.findViewById(R.id.userImage);
            followBackButton = (Button) view.findViewById(R.id.follow_back_btn);

            image1 = (ImageView) view.findViewById(R.id.image1);
            image2 = (ImageView) view.findViewById(R.id.image2);
            image3 = (ImageView) view.findViewById(R.id.image3);
            image4 = (ImageView) view.findViewById(R.id.image4);
            imageOverflow = (ImageView) view.findViewById(R.id.imageOverflow);
        }
    }

    public TxsAdapter(List<Txs> txsList) {
        this.txsList = txsList;
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        SimpleDateFormat format;
        Date date = new Date();
        try {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = format.parse(mDate[position]);
        }catch (ParseException e){
            Log.e("**Error**", e.getMessage());
        }
        holder.timeText.setReferenceTime(date.getTime());
        holder.titleText.setText(mTitle[position]);

        final Context context = holder.userImage.getContext();
        Picasso.with(context).load(mUserImageUrl[position]).into(holder.userImage);

        if (mTitle[position].contains("följa")){
            holder.followBackButton.setVisibility(View.VISIBLE);
            holder.followBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.followBackButton.getText().equals("Följ tillbaka")){
                        holder.followBackButton.setText("Följde tillbaka");
                    }else{
                        holder.followBackButton.setText("Följ tillbaka");
                    }

                }
            });
        }
        if(mTitle[position].contains("sparade")){
            for (int i = 0; i <  mSavedImages.length; i++){
                if(i == 0) {
                    holder.image1.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(mSavedImages[i]).centerCrop().resize(200, 200)
                            .centerCrop().into(holder.image1);
                }else if(i == 1){
                    holder.image2.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(mSavedImages[i]).centerCrop().resize(200, 200)
                            .centerCrop().into(holder.image2);
                }else if(i == 2){
                    holder.image3.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(mSavedImages[i]).centerCrop().resize(200, 200)
                            .centerCrop().into(holder.image3);
                }else if(i == 3){
                    holder.image4.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(mSavedImages[i]).centerCrop().resize(200, 200)
                            .centerCrop().into(holder.image4);
                }else if(i == 4){
                    holder.imageOverflow.setVisibility(View.VISIBLE);
                    holder.imageOverflow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int savedImagesLength = mSavedImages.length;

                            AlertDialog dialog = new AlertDialog.Builder(view.getContext()).create();
                            LayoutInflater inflater = LayoutInflater.from(view.getContext());

                            View viewLayout = inflater.inflate(R.layout.overflow_images_dialog, null);
                            ImageView imageView1 = (ImageView) viewLayout.findViewById(R.id.image1);
                            ImageView imageView2 = (ImageView) viewLayout.findViewById(R.id.image2);
                            ImageView imageView3 = (ImageView) viewLayout.findViewById(R.id.image3);
                            ImageView imageView4 = (ImageView) viewLayout.findViewById(R.id.image4);
                            ImageView imageView5 = (ImageView) viewLayout.findViewById(R.id.image5);
                            ImageView imageView6 = (ImageView) viewLayout.findViewById(R.id.image6);
                            ImageView[] imageViewArray = {imageView1, imageView2, imageView3, imageView4, imageView5, imageView6};

                            if(savedImagesLength >= 5){
                                for (int i = 0; i < savedImagesLength; i++) {
                                    Picasso.with(context).load(mSavedImages[i]).into(imageViewArray[i]);
                                }
                            }

                            dialog.setView(viewLayout);
                            dialog.show();
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDate.length;
    }
}
