package com.free.dennisg.bittrackr.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by Dennis Gimbergsson on 2016-11-07.
 **/

public interface RetrofitAPI {
    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
    */
    @GET("address/{address}/?format=json&limit=51")
    Call<Address> getAddressDetails(@Path("address") String address);
}
