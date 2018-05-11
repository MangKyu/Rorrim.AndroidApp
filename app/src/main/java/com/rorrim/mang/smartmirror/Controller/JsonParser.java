package com.rorrim.mang.smartmirror.Controller;

import com.rorrim.mang.smartmirror.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class JsonParser {

    private static JsonParser instance;

    public static JsonParser getInstance(){
        if (instance == null) {
            synchronized (JsonParser.class) {
                if (instance == null) {
                    instance = new JsonParser();
                }
            }
        }
        return instance;
    }

    /* 나중에 JsonParser Class 만들어야 할 듯 */
    public LinkedList<Data> parseJson(String jsonStr){

        LinkedList<Data> dataList = new LinkedList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dataList.add(new Data(jsonObject.getString("name"), jsonObject.getString("models")));
            }

        }catch(JSONException e){
            e.getStackTrace();
        }
        return dataList;
    }

}
