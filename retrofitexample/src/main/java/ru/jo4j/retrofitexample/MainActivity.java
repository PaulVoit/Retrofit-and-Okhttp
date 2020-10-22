package ru.jo4j.retrofitexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recycler;
    private Network network;
    private TextView result;
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        network = new Network(this);
        network.checkNetwork();
        jsonPlaceHolderApi = Network.getApi();
        updateUI();
    }

    public void updateUI() {
        posts = new ArrayList<>();
        recycler = findViewById(R.id.posts_recycle_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new PostAdapter(posts));
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    result.setText(String.format("Code: %s", response.code()));
                    return;
                }
                posts.addAll(response.body());
                recycler.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }
}