package com.example.afnan.newstest2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.afnan.newstest2.api.ApiInterface;
import com.example.afnan.newstest2.api.apiclient;
import com.example.afnan.newstest2.models.Artical;
import com.example.afnan.newstest2.models.news;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity {

    //finally we righr this code
    //here i put my own api key
    public static final String API_KEY = "17289d3183374bf0a5b71729c5092679";
    private RecyclerView recyclerView;
    //https://developer.android.com/reference/android/support/v7/widget/RecyclerView.LayoutManager
    private RecyclerView.LayoutManager layoutManager;
    private List<Artical> articals = new ArrayList<>();
    private Adapter adapter;
    //.class.getSimpleName():Returns the simple name of the underlying class as given in the source code.
    //https://developer.android.com/reference/java/lang/Class
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //we do the following to recyclerview because :look to this website
        //https://developer.android.com/reference/android/support/v7/widget/RecyclerView
        //in layout main_activity we put to recyclerview id RecyclerView
        recyclerView = findViewById(R.id.RecyclerView);
        //LinearLayoutManager(Context context) :Creates a vertical LinearLayoutManager
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        // RecyclerView.ItemAnimator that will handle animations involving changes
        // to the items in this RecyclerView.
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //to enjoy smooth scrolling with the android RecyclerView put inside
        // the NestedScrollView, we are going to set the method setNestedScrollingEnabled(false) to false
        //https://inducesmile.com/android-tips/android-recyclerview-inside-nestedscrollview-example/
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();


    }

    public void LoadJson() {


        ApiInterface apiInterface = apiclient.getApiClient().create(ApiInterface.class);
        String Country = Utils.getCountry();
        Call<news> call = apiInterface.getNews(Country, API_KEY);

        call.enqueue(new Callback<news>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful() && response.body().getArticale() != null) {

                    if (!articals.isEmpty()) {
                        articals.clear();
                    }

                    articals = response.body().getArticale();
                    adapter = new Adapter(articals, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
            });

    }
}
