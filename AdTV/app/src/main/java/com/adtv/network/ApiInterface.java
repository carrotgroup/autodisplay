package com.adtv.network;

import com.adtv.splashscreen.model.GetSharedLinkApiResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * interface which hold the definition of api.
 */

public interface ApiInterface {

    /**
     * api to fetch user content urls
     */
//    @Headers("Authorization: Bearer qPWe4rHzi4AAAAAAAAAAB-GU8ty1sQZtY1jsZNBp2OoW2c31HohRJUmRN6OTXymg")
    @POST("sharing/list_shared_links")
    Call<GetSharedLinkApiResponse> fetchDropBoxContents(@Header("Authorization") String token);
}
