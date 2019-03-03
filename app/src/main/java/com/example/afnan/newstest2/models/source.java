package com.example.afnan.newstest2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class source {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    public source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //then make setter and getter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
