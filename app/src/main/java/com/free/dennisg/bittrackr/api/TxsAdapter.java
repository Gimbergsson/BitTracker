package com.free.dennisg.bittrackr.api;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.free.dennisg.bittrackr.R;
import com.free.dennisg.bittrackr.api.currencies.SEK;
import com.free.dennisg.bittrackr.fragments.LookupAddressFragment;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TxsAdapter extends RecyclerView.Adapter<TxsAdapter.TxsViewHolder> {

    private Context mContext;
    private String mAddress;
    private final List<Txs> txsList;

    String apiBaseURL = "https://blockchain.info/";

    double mSEKLast;

    public static class TxsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txs_hash)        TextView mTxsHashText;
        @BindView(R.id.txs_relayed_by)  TextView mTxsRelayedByText;
        @BindView(R.id.txs_block)       TextView mTxsBlock;
        @BindView(R.id.txs_size)        TextView mTxsSize;
        @BindView(R.id.txs_time)        RelativeTimeTextView mTxsTime;
        @BindView(R.id.txs_out_value)   TextView mTxsOutValue;
        @BindView(R.id.txs_in_value)    TextView mTxsInValue;
        @BindView(R.id.txs_fee)         TextView mTxsFee;
        @BindView(R.id.txs_details)     TextView mTxsDetails;
        @BindView(R.id.relativelayout)  RelativeLayout mRelativeLayout;

        TxsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TxsAdapter(Context mContext, String mAddress, List<Txs> txsList) {
        this.mContext = mContext;
        this.mAddress = mAddress;
        this.txsList = txsList;
    }

    @Override
    public TxsAdapter.TxsViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.txs_list_row, parent, false);

        return new TxsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TxsViewHolder holder, int position) {

        final Txs txs = txsList.get(position);
        holder.mTxsHashText.setText("Txs hash: " + txs.getHash());
        holder.mTxsRelayedByText.setText("Relayed by: " + txs.getRelayed_by());
        if (txs.getBlock_height() == 0){
            holder.mTxsBlock.setText("Unconfirmed!");
        }else{
            holder.mTxsBlock.setText("In block: " + String.valueOf(txs.getBlock_height()));
        }

        holder.mTxsSize.setText(String.valueOf(txs.getSize()) + " Bytes");

        long txsDate = txs.getTime() * 1000;
        holder.mTxsTime.setReferenceTime(txsDate);

        long total_output = 0;

        for(int i = 0; i < txs.getOut().size(); i++){
            total_output += txs.getOut().get(i).getValue();
            String total_output_string = String.valueOf((double)total_output / 100000000);
            BigDecimal outputBigDecimal = new BigDecimal(total_output_string);
            holder.mTxsOutValue.setText("Output: " + outputBigDecimal.toPlainString() + " BTC");
        }

        long total_input = 0;

        for(int i = 0; i < txs.getInputs().size(); i++){
            total_input += txs.getInputs().get(i).getPrev_out().getValue();
            String total_input_string = String.valueOf((double)total_input / 100000000);
            BigDecimal inputBigDecimal = new BigDecimal(total_input_string);
            holder.mTxsInValue.setText("Input: " + inputBigDecimal.toPlainString() + " BTC");
        }

        long txs_fee = total_input - total_output;
        String txs_fee_string = String.valueOf((double)txs_fee / 100000000);
        final BigDecimal feeBigDecimal = new BigDecimal(txs_fee_string);
        holder.mTxsFee.setText("Fee: " + feeBigDecimal.toString() + " BTC");

        /*Transaction detailed dialog*/
        /*final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =  inflater.inflate(R.layout.txs_dialog_details, null);

        TextView mSentToAddress = (TextView) layout.findViewById(R.id.textView2);
        mSentToAddress.setText(sentToAddressBigDecimal.toString());

        builder.setView(layout)
            .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(mContext, "Sign in", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            })
            .create();*/

        /*holder.mTxsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.mTxsDetails);

                for(int i = 0; i < txs.getOut().size(); i++){
                    Log.e("TAG", mAddress);
                    if(txs.getOut().get(i).getAddr().equals(mAddress)){

                        double sent_to_address = txs.getOut().get(i).getValue();

                        String total_sent_to_address = String.valueOf(sent_to_address / 100000000);

                        showDialog(total_sent_to_address);
                    }
                }
            }
        });*/

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI service = retrofit.create(RetrofitAPI.class);

        Call<Ticker> tickerCall = service.getTicker();
        tickerCall.enqueue(new Callback<Ticker>() {
            @Override
            public void onResponse(Call<Ticker> call, Response<Ticker> response) {
                if (response.isSuccessful()) {
                    //Apply response to POJO
                    Ticker tickerData = response.body();
                    mSEKLast = tickerData.getSEK().getLast();
                } else {
                    Log.e("Failure", "Something went wrong with getting ticker data");
                }
            }

            @Override
            public void onFailure(Call<Ticker> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> mAddresses = new ArrayList<>();
                List<Double> mValues = new ArrayList<>();

                for(int i = 0; i < txs.getOut().size(); i++){

                    /*Log.e("TAG", mAddress);
                    if(txs.getOut().get(i).getAddr().equals(mAddress)){

                        double sent_to_address = txs.getOut().get(i).getValue();

                        String total_sent_to_address = String.valueOf(sent_to_address / 100000000);

                        showDialog(String.valueOf(total_sent_to_address) + " " + String.valueOf((sent_to_address * mSEKLast) / 100000000));
                    }*/

                    double sent_to_address = txs.getOut().get(i).getValue();
                    String total_sent_to_address = String.valueOf(sent_to_address / 100000000);
                    mAddresses.add(i, txs.getOut().get(i).getAddr());
                    mValues.add(i, (double) txs.getOut().get(i).getValue());
                    //showDialog(String.valueOf(total_sent_to_address) + " " + String.valueOf((sent_to_address * mSEKLast) / 100000000));
                }

                showDialog(mAddresses, mValues);
            }
        });

    }

    private void showDialog(List<String> mAddressesList, List<Double> mValuesList) {
        /*Transaction detailed dialog*/
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout =  inflater.inflate(R.layout.txs_dialog_details, null);

        TextView mSentToAddress = (TextView) layout.findViewById(R.id.textView2);
        //mSentToAddress.setText(mSentToAddressString);

        String mOutput =  "";
        for(int i = 0; i < mAddressesList.size(); i++){
            double mValueBTC = mValuesList.get(i) / 100000000;
            mOutput += mAddressesList.get(i) + " -> " + (String.valueOf(mValueBTC) + "\n");
        }
        mSentToAddress.setText(mOutput);

        builder.setView(layout)
            .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            })
            .create().show();
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_txs_details, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Clicked more", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Clicked less", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return txsList.size();
    }
}
