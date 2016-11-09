package com.free.dennisg.bittrackr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.free.dennisg.bittrackr.R;
import com.free.dennisg.bittrackr.api.Address;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.free.dennisg.bittrackr.api.*;

public class LookupAddressFragment extends Fragment {

    @BindView(R.id.address) TextView address_txt;
    @BindView(R.id.hash160) TextView hash160_txt;
    @BindView(R.id.transactions_done) TextView transactions_done_txt;
    @BindView(R.id.total_received) TextView total_received_txt;
    @BindView(R.id.total_sent) TextView total_sent_txt;
    @BindView(R.id.final_balance) TextView final_balance_txt;
    @BindView(R.id.transactions) TextView transactions_txt;

    String apiBaseURL = "https://blockchain.info/";

    public static LookupAddressFragment newInstance(int index) {
        LookupAddressFragment fragment = new LookupAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("empty", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("**Info**", "onCreateView");
        View view = inflater.inflate(R.layout.lookup_address_fragment, container, false);
        getAddressDetails();
        return view;
    }

    public void getAddressDetails() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitAPI service = retrofit.create(RetrofitAPI.class);
        Call<Address> call = service.getAddressDetails("1Gimbergzr2c3C5KcGjLZSM1cCUMW4PfyY");
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                Address AddressData = response.body();

                TextView address_text = (TextView) getView().findViewById(R.id.address);
                address_text.setText(AddressData.getAddress());

                TextView hash160_text = (TextView) getView().findViewById(R.id.hash160);
                hash160_text.setText(AddressData.getHash160());

                TextView transactions_done_text = (TextView) getView().findViewById(R.id.transactions_done);
                transactions_done_text.setText(String.valueOf(AddressData.getN_tx()));

                //address_txt.setText(AddressData.getAddress());
                //hash160_txt.setText(AddressData.getHash160());
                //transactions_done_txt.setText(String.valueOf(AddressData.getN_tx()));
                //total_received_txt.setText(String.valueOf(AddressData.getTotal_received()));
                //total_sent_txt.setText(String.valueOf(AddressData.getTotal_sent()));
                //final_balance_txt.setText(String.valueOf(AddressData.getFinal_balance()));

            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

            /*
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                try {

                    List<Student> StudentData = response.body();

                    for (int i = 0; i < StudentData.size(); i++) {
                        if (i == 0) {
                            Log.d("TAG", "Msg:  " + StudentData.get(i).getStudentName() +" : "+String.valueOf(i));
                            questionTextView.setText("Question:  " + StudentData.get(i).getStudentName());
                        }
                    }


                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.d("onFailure", t.toString());
                mProgressBar.setVisibility(View.GONE);
            }*/
        });
    }
}
