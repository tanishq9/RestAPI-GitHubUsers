package com.example.tanis.okhttpjson;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by tanis on 1/16/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    //ArrayList<User> arrayList;
    ArrayList<Item> arrayList;
    Context context;

    UserAdapter(Context c,ArrayList<Item> list){
        this.context=c;
        this.arrayList=list;
    }
    /*UserAdapter(Context c,ArrayList<User> list){
        this.context=c;
        this.arrayList=list;
    }*/
    public class UserHolder extends RecyclerView.ViewHolder {
        TextView login,id,score,url;
        ImageView image;
        public UserHolder(View itemView) {
            super(itemView);
            login=itemView.findViewById(R.id.login);
            id=itemView.findViewById(R.id.id);
            score=itemView.findViewById(R.id.score);
            url=itemView.findViewById(R.id.url);
            image=itemView.findViewById(R.id.image);
        }
    }

    @Override
    public UserAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false));
    }//if it was null inplace of parent then the view was unapt pls check this

    @Override
    public void onBindViewHolder(final UserAdapter.UserHolder holder, int position) {
       // final User user=arrayList.get(position);
        final Item user=arrayList.get(position);
        Picasso.with(context)
                .load(user.getAvatar_url())
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.image);
        holder.login.setText(user.getLogin());
        holder.score.setText(user.getScore()+"");
        holder.id.setText(user.getId()+"");
        holder.url.setText(user.getUrl());
        holder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request=new Request.Builder()
                        .url(user.getUrl())
                        .build();
                String str=holder.url.getText().toString();
                Intent intent=new Intent(context, Another.class);
                intent.putExtra("photo",user.getAvatar_url());
                intent.putExtra("name",holder.login.getText().toString());
                intent.putExtra("url",str);
                //intent.setData(Uri.parse(str));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
