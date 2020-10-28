package ru.jo4j.network;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    private Service service;
    TextView result;
    String url = "https://api.github.com/users/PaulVoit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.text_view_result);
        Button button = findViewById(R.id.button);
        service = new Service(this);
        service.checkNetwork();
        button.setOnClickListener(view -> {
            try {
                service.startRequest(url, new Service.ResultHandler() {
                    @Override
                    public void onSuccess(String response) {
                        result.setText(response);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}




