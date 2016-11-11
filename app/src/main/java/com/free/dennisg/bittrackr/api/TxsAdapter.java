package com.free.dennisg.bittrackr.api;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.free.dennisg.bittrackr.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TxsAdapter extends RecyclerView.Adapter<TxsAdapter.TxsViewHolder> {

    private List<Txs> txsList;

    public TxsAdapter(List<Txs> txsList) {
        this.txsList = txsList;
    }

    @Override
    public TxsAdapter.TxsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.txs_list_row, parent, false);
        return new TxsViewHolder(itemView);
    }

    public static class TxsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txs_hash)        TextView mTxsHashText;
        @BindView(R.id.txs_relayed_by)  TextView mTxsRelayedByText;
        @BindView(R.id.txs_size)        TextView mTxsSize;

        TxsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(TxsViewHolder holder, int position) {
            Txs txs = txsList.get(position);
            holder.mTxsHashText.setText(txs.getHash());
            holder.mTxsRelayedByText.setText(txs.getRelayed_by());
            holder.mTxsSize.setText(String.valueOf(txs.getBlock_height()));
    }

    @Override
    public int getItemCount() {
        return txsList.size();
    }
}
