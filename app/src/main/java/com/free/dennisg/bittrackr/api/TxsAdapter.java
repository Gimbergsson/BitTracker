package com.free.dennisg.bittrackr.api;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.free.dennisg.bittrackr.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TxsAdapter extends RecyclerView.Adapter<TxsAdapter.TxsViewHolder> {

    private Context mContext;
    private final List<Txs> txsList;

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

        TxsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TxsAdapter(Context mContext, List<Txs> txsList) {
        this.mContext = mContext;
        this.txsList = txsList;
    }

    @Override
    public TxsAdapter.TxsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.txs_list_row, parent, false);
        return new TxsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TxsViewHolder holder, int position) {
        Txs txs = txsList.get(position);
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
            BigDecimal bigDecimal = new BigDecimal(total_output_string);
            holder.mTxsOutValue.setText("Output: " + bigDecimal.toPlainString() + " BTC");
        }

        long total_input = 0;

        for(int i = 0; i < txs.getInputs().size(); i++){
            total_input += txs.getInputs().get(i).getPrev_out().getValue();
            String total_input_string = String.valueOf((double)total_input / 100000000);
            BigDecimal bigDecimal = new BigDecimal(total_input_string);
            holder.mTxsInValue.setText("Input: " + bigDecimal.toPlainString() + " BTC");
        }

        long txs_fee = total_input - total_output;
        String txs_fee_string = String.valueOf((double)txs_fee / 100000000);
        BigDecimal bigDecimal = new BigDecimal(txs_fee_string);
        holder.mTxsFee.setText("Fee: " + bigDecimal.toString() + " BTC");

        holder.mTxsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.mTxsDetails);
            }
        });

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
