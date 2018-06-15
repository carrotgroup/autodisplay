package com.adtv.splashscreen;

import com.adtv.splashscreen.model.GetSharedLinkApiResponse;

interface SplashScreenApiResponseListener {

    void onResponse(GetSharedLinkApiResponse iGetSharedLinkApiResponse);

    void onError(String message);
}
