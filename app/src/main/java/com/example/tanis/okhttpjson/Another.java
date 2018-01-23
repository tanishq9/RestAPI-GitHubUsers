package com.example.tanis.okhttpjson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tanis on 1/17/2018.
 */

public class Another extends AppCompatActivity implements Callback{
    TextView name;
    ImageView imageView;
    TextView fr;
    ProgressBar progressDialog;
    FloatingActionButton floatingActionButton;
    TextView fl;
    int followers=0,following=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);
        final Intent intent = getIntent();
        name = findViewById(R.id.name);
        imageView = findViewById(R.id.iv);
        fl=findViewById(R.id.following);
        floatingActionButton=findViewById(R.id.fab);
        fr=findViewById(R.id.followers);
        progressDialog=findViewById(R.id.pb);
        String str = intent.getStringExtra("photo");
        name.setText(intent.getStringExtra("name"));
        Picasso.with(this)
                .load(str)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder()
                .url(intent.getStringExtra("url"))
                .build();
        okHttpClient.newCall(request).enqueue(this);
        //progressDialog.setVisibility(View.VISIBLE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(intent.getStringExtra("url")));
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    //OnResponse() is a background or nonUI thread i.e it runs in the background
    //I had a separate thread that was trying to modify the UI
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        progressDialog.setVisibility(View.VISIBLE);
        final String s=response.body().string();
        try {
            JSONObject jsonObject=new JSONObject(s);
            followers=jsonObject.getInt("followers");
            following=jsonObject.getInt("following");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fl.setText(following+"");
                    fr.setText(followers+"");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setVisibility(View.INVISIBLE);
            }
        });
    }


}