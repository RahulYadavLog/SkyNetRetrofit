package com.example.retrofit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofit2.adapter.UserAdapter;
import com.example.retrofit2.model.UserModel;
import com.example.retrofit2.request.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    UserAdapter userAdapter;
    List<UserModel> userModels;
    UserModel userModel;
    private Handler handler=new Handler();
    AlertDialog.Builder dialogBuilder;
    AlertDialog data_alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userModels = new ArrayList<>();
        viewPager2 = (ViewPager2) findViewById(R.id.view_pager);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api=retrofit.create(Api.class);
        Call<List<UserModel>> call=api.getHeroes();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                userModels =response.body();


                viewPager2.setAdapter(new UserAdapter(userModels, MainActivity.this, viewPager2, new UserAdapter.ShareData() {
                    @Override
                    public void share(String userid, String id, String title, String body) {
                        AddPage(userid,id,title,body);
                    }
                }));
                viewPager2.setClipToPadding(false);
                viewPager2.setClipChildren(false);
                viewPager2.setOffscreenPageLimit(3);
                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(100));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r=1-Math.abs(position);
                        page.setScaleY(0.85f+r*0.15f);
                    }
                });
                viewPager2.setPageTransformer(compositePageTransformer);

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void AddPage( String struserId,String strid,String strtitle,String strbody){
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
// ...Irrelevant code for customizing the buttons and title
        View userdialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.user_data_layout, null, false);
        final TextView userId,id,title,body;
        userId=userdialogView.findViewById(R.id.user_id);
        id=userdialogView.findViewById(R.id.id);
        title=userdialogView.findViewById(R.id.title);
        body=userdialogView.findViewById(R.id.body);

        userId.setText(struserId);
        id.setText(strid);
        title.setText(strtitle);
        body.setText(strbody);

        dialogBuilder.setView(userdialogView);
        data_alertDialog = dialogBuilder.create();
        data_alertDialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, 1550);
        data_alertDialog.show();




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        data_alertDialog.dismiss();
    }
}