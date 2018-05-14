package com.rorrim.mang.smartmirror.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("models")
    private List<String> models;
    @SerializedName("name")
    private String name;

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}