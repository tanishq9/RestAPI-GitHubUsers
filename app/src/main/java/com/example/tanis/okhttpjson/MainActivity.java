package com.example.tanis.okhttpjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callback{
    TextView textView;
    EditText editText;
    Button button;
    RecyclerView recyclerView;
    public static String BASE_URL ="https://api.github.com/search/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView=findViewById(R.id.tv);
        editText=findViewById(R.id.et);
        button=findViewById(R.id.btn);
        recyclerView=findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final OkHttpClient okHttpClient=new OkHttpClient();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString=editText.getText().toString();
                Request request=new Request.Builder()
                        .url(BASE_URL+"users?q="+inputString)
                        .build();
                okHttpClient.newCall(request).enqueue(MainActivity.this);
            }
        });
    }


    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String string=response.body().string();

        Gson gson=new Gson();
        com.example.tanis.okhttpjson.ItemList myResponse=gson.fromJson(string,com.example.tanis.okhttpjson.ItemList.class);

        final UserAdapter userAdapter = new UserAdapter(MainActivity.this,myResponse.getItems());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(userAdapter);
            }
        });

    }

    private ArrayList<User> parseJson(String s) throws JSONException{
        ArrayList<User> list=new ArrayList<>();
        JSONObject jsonObject=new JSONObject(s);
        JSONArray jsonArray=jsonObject.getJSONArray("items");
        for(int i=0;i<jsonArray.length() && i!=1;i++){
            JSONObject currentObject=jsonArray.getJSONObject(i);
            String login=currentObject.getString("login");
            String url=currentObject.getString("url");
            int id=currentObject.getInt("id");
            Double score=currentObject.getDouble("score");
            String photo=currentObject.getString("avatar_url");
            User user=new User(login,id,photo,score,url);
            list.add(user);
            //String,Int,Double are the data type of variable in JsonData
        }
        return list;
    }

}

   /*recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(userAdapter);
            }
        });*/