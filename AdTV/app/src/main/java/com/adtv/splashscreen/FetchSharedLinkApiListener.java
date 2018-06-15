package com.adtv.splashscreen;

import android.util.Log;

import com.adtv.network.ApiInterface;
import com.adtv.network.CallbackManager;
import com.adtv.splashscreen.model.GetSharedLinkApiResponse;

import retrofit2.Call;

public class FetchSharedLinkApiListener extends CallbackManager {

    private ApiInterface mApiInterface;
    private SplashScreenApiResponseListener mHomeApiResponseListener;

    public FetchSharedLinkApiListener(SplashScreenApiResponseListener iHomeApiResponseListener) {
        this.mApiInterface = (ApiInterface) getServiceClient(ApiInterface.class);
        this.mHomeApiResponseListener = iHomeApiResponseListener;
    }

    public void callFetchSharedLinksApi(String authToken) {
        Call<GetSharedLinkApiResponse> call = mApiInterface.fetchDropBoxContents(authToken);
        call.enqueue(this);
    }

    @Override
    protected void onSuccess(Object o) {
        Log.e("TAG", "Object::" + o);
        if (o instanceof GetSharedLinkApiResponse) {
            mHomeApiResponseListener.onResponse((GetSharedLinkApiResponse) o);
        } else {
            mHomeApiResponseListener.onError("something went wrong, please try again");
        }
    }

    @Override
    protected void onError(String message) {
        mHomeApiResponseListener.onError(message);
    }
}
