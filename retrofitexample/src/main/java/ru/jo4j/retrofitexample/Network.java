package ru.jo4j.retrofitexample;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static final String TAG = "Network";
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private AppCompatActivity activity;

    public Network(AppCompatActivity activity) {
        this.activity = activity;
    }

    public static JsonPlaceHolderApi getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        return jsonPlaceHolderApi;
    }

    public void checkNetwork() {
        try {
            ProviderInstaller.installIfNeeded(activity.getApplicationContext());
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException ex) {
            Log.e(TAG, "GooglePlay not available", ex);
        }
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
        } catch (NoSuchAlgorithmException ex) {
            Log.e(TAG, "No such algorithm", ex);
        }
        try {
            sslContext.init(null, null, null);
        } catch (KeyManagementException ex) {
            Log.e(TAG, "key managment error", ex);
        }
        SSLEngine engine = sslContext.createSSLEngine();
    }
}
