package com.free.dennisg.bittrackr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.free.dennisg.bittrackr.R;
import com.free.dennisg.bittrackr.api.Address;
import com.free.dennisg.bittrackr.api.MyDeserializer;
import com.free.dennisg.bittrackr.api.Prev_out;
import com.free.dennisg.bittrackr.api.RetrofitAPI;
import com.free.dennisg.bittrackr.api.Txs;
import com.free.dennisg.bittrackr.api.TxsAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LookupAddressFragment extends Fragment {

    @BindView(R.id.address)             TextView address_txt;
    @BindView(R.id.hash160)             TextView hash160_txt;
    @BindView(R.id.transactions_done)   TextView transactions_done_txt;
    @BindView(R.id.total_received)      TextView total_received_txt;
    @BindView(R.id.total_sent)          TextView total_sent_txt;
    @BindView(R.id.final_balance)       TextView final_balance_txt;

    @BindView(R.id.address_input)       EditText address_input_edittext;
    @BindView(R.id.get_address_info)    Button get_address_info_button;

    @BindView(R.id.txs_recycler_view)   RecyclerView recyclerView;

    String apiBaseURL = "https://blockchain.info/";

    TxsAdapter txsAdapter;
    LinearLayoutManager linearLayoutManager;

    public static LookupAddressFragment newInstance(int index) {
        LookupAddressFragment fragment = new LookupAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("0", 1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("**Info**", "onCreateView");
        View view = inflater.inflate(R.layout.lookup_address_fragment, container, false);
        ButterKnife.bind(this, view);

        get_address_info_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address_string = address_input_edittext.getText().toString();
                int offset_string = 0;
                if(address_string.startsWith("1")) {
                    getAddressDetails(address_string, offset_string);
                }else{
                    Toast.makeText(getContext(), "Address needs to start with a 1", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void getAddressDetails(String address, int offset){
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Prev_out.class, new MyDeserializer<Prev_out>())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI service = retrofit.create(RetrofitAPI.class);
        Call<Address> call = service.getAddressDetails(address, "json", offset);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Address AddressData = response.body();
                Log.i("**TAG**", AddressData.toString());

                address_txt.setText(AddressData.getAddress());
                hash160_txt.setText(AddressData.getHash160());
                transactions_done_txt.setText(String.valueOf(AddressData.getN_tx()));
                total_received_txt.setText(String.valueOf((double)AddressData.getTotal_received() / 100000000) + " BTC");
                total_sent_txt.setText(String.valueOf((double)AddressData.getTotal_sent() / 100000000) + " BTC");
                final_balance_txt.setText(String.valueOf((double)AddressData.getFinal_balance() / 100000000) + " BTC");

                List<Txs> txsList = AddressData.getTxs();

                txsAdapter = new TxsAdapter(txsList);
                linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(txsAdapter);

                txsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
