package com.free.dennisg.bittrackr.api;

import android.util.Log;

import com.free.dennisg.bittrackr.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dennis Gimbergsson on 2017-01-08.
 **/

public class RetrofitCalls {

    Response<Ticker> response;

    public Response<Ticker> getCurrenCiesCall() {


        return response;
    }
}
