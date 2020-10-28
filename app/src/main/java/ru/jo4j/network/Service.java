package ru.jo4j.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Service {

    private AppCompatActivity activity;
    private static final String TAG = "Service";

    public Service(AppCompatActivity activity) {
        this.activity = activity;
    }

    public interface ResultHandler {
        public void onSuccess(String response);
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

    OkHttpClient client = new OkHttpClient();

    public void startRequest(String url, ResultHandler callback) throws IOException {

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            try {
                                String strResponse = response.body().string();
                                callback.onSuccess(strResponse);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
}
