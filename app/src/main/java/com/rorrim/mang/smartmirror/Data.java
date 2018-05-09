package com.rorrim.mang.smartmirror;

import java.util.ArrayList;

public class Data {
    private String name;
    private String models;

    public Data(String name, String models){
        this.name = name;
        this.models = models;
    }

    public String getModels() {
        return models;
    }

    public String getName() {
        return name;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Data [name=" + name + ", models =" + models;
    }

}
