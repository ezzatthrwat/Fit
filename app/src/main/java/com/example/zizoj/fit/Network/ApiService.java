package com.example.zizoj.fit.Network;

import com.example.zizoj.fit.Network.Model.Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Call<Responses> getNearbyPlaces(@Url String url);


}
