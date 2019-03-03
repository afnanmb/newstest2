package com.example.afnan.newstest2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class news {
    //https://io.hsoub.com/programming/33100-%D9%87%D9%84-%D8%B9%D9%84%D9%8A-%D8%AA%D8%B9%D9%84%D9%85-json-%D9%85%D8%A7-%D9%87%D9%8A-%D8%A8%D8%A7%D9%84%D8%B6%D8%A8%D8%B7
    //@SerializedName this json that used to serialized and give it a name which we choose status
    //@Expose:An annotation that indicates this member should be exposed يعرض for JSON serialization or deserialization.
    //we will go to https://newsapi.org and see the code and define the below
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResult")
    @Expose
    private int totalResult;

    @SerializedName("articles")
    @Expose
    //we make it in list because it contain another things inside it
    //then the Article between <> we will make it a new class
    private List<Artical> articale = null;
    //then make setters and getters ...right click then choose setter and getters and select all
    //getter هي دالة ترجع قيمة المخزنة داخلها
    //setter its fun. that we gave it a name that put it to the variable that we defined
    //then we go to article class and add the other things that inside articles


    public news(String status, int totalResult, List<Artical> articale) {
        this.status = status;
        this.totalResult = totalResult;
        this.articale = articale;
    }

    @SuppressWarnings("unused")
    public String getStatus() {
        return status;
    }

    @SuppressWarnings("unused")
    public void setStatus(String status) {
        this.status = status;
    }

    @SuppressWarnings("unused")
    public int getTotalResult() {
        return totalResult;
    }

    @SuppressWarnings("unused")
    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    @SuppressWarnings("unused")
    public List<Artical> getArticale() {
        return articale;
    }

    @SuppressWarnings("unused")
    public void setArticale(List<Artical> articale) {
        this.articale = articale;
    }
}

