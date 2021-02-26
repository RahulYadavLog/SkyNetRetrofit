package com.example.retrofit2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.retrofit2.R;
import com.example.retrofit2.model.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserView> {
    List<UserModel> userModels;
    Context context;
    ViewPager2 viewPager2;

    ShareData shareData;

    public UserAdapter(List<UserModel> userModels, Context context, ViewPager2 viewPager2, ShareData shareData) {
        this.userModels = userModels;
        this.context = context;
        this.viewPager2 = viewPager2;
        this.shareData = shareData;
    }

  public   interface ShareData{
        public void share(String userid,String id,String title,String body);
    }
    @NonNull
    @Override
    public UserView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.user_list, parent, false);
        return new UserAdapter.UserView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserView holder, int position) {

        holder.userId.setText(userModels.get(position).getUserId());
        holder.id.setText(userModels.get(position).getId());
        holder.title.setText(userModels.get(position).getTitle());
        holder.body.setText(userModels.get(position).getBody());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData.share(userModels.get(position).getUserId(),userModels.get(position).getId(),userModels.get(position).getTitle(),
                        userModels.get(position).getBody());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class UserView extends RecyclerView.ViewHolder {
        TextView userId,id,title,body;
        CardView cardView;
        public UserView(@NonNull View itemView) {
            super(itemView);
            userId=itemView.findViewById(R.id.user_id);
            id=itemView.findViewById(R.id.id);
            title=itemView.findViewById(R.id.title);
            body=itemView.findViewById(R.id.body);
            cardView=itemView.findViewById(R.id.card);
        }
    }

}
