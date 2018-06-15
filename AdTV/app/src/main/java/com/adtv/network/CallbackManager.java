package com.adtv.network;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackManager implements Callback {

    private HashMap<String, String> mRequestHeaderMap = new HashMap<>();

    public CallbackManager() {
        //initHeaders();
    }

    public <S> S getServiceClient(Class<S> serviceClass) {

        return RemoteClient.getClient(serviceClass);
    }

    @Override
    public void onResponse(Call call, Response response) {

        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onError(response.raw().message());
        }
    }

    @Override
    public void onFailure(Call call, Throwable throwable) {
        throwable.printStackTrace();
        try {
            if (throwable instanceof UnknownHostException) {
                onError("no network");

            } else if (throwable instanceof SocketTimeoutException) {
                //https://github.com/square/retrofit/issues/1260   handling network issue
                onError("Connection to server timed out");

            } else if (throwable instanceof ApiException) {
                int statusCode = ((ApiException) throwable).getStatusCode();
                String message = ((ApiException) throwable).getLocalizedMessage();

            } else {
                onError("something went wrong::" + throwable);
            }
        } catch (Exception e) {
            onError("something went wrong::" + e);
        }
    }


    protected abstract void onSuccess(Object o);

    protected abstract void onError(String message);

}
