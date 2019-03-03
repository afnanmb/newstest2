package com.example.afnan.newstest2.api;

import com.example.afnan.newstest2.models.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //we get top-headlines from https://newsapi.org/v2/top-headlines
    //@GET request
    @GET("top-headlines")
    //هنعمل استدعاء لكلاس news
    Call<news> getNews(

            //@Query سؤال
            //https://newsapi.org/v2/top-headlines?country=
            @Query("country") String country,
            //https://newsapi.org/v2/top-headlines?country=sa&category=science&apiKey=
            @Query("apiKey") String apiKey
    );
    //then go and create new layout
}
