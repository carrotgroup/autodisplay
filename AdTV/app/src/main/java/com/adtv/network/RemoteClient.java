package com.adtv.network;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * class which create the apiclient to hit api.
 */

public class RemoteClient {

    private static final String BASE_URL = "https://api.dropboxapi.com/2/";

    /**
     * method to return to create retrofit client
     *
     * @return
     */
    private static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); // set your desired log level
        //NONE, BASIC, HEADERS, BODY

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);// add logging as last interceptor
        httpClient.connectTimeout(2, TimeUnit.MINUTES);
        httpClient.writeTimeout(2, TimeUnit.MINUTES);
        httpClient.readTimeout(2, TimeUnit.MINUTES);

        httpClient.addInterceptor(new ErrorInterceptor());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Going to build the Retrofit builder with converter
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build());
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        return builder.build();
    }


    private static class ErrorInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // before request
            Request originalRequest = chain.request();
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);

            // inspect status codes of unsuccessful responses
            if (response != null && response.code() != 200) {
                Log.e("RemoteCLient", String.valueOf(response.code()));
            }
            return response;
        }
    }

    /**
     * method to get client corresponding to api interface.
     *
     * @param serviceClass
     * @param <S>          api interface which hold all the definition of api.
     * @return
     */
    public static <S> S getClient(Class<S> serviceClass) {
        return getClient().create(serviceClass);
    }
}
