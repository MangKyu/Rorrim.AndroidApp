package com.rorrim.mang.smartmirror.Unused;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rorrim.mang.smartmirror.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public LinkedList<Data> parseJson(String jsonStr){

        LinkedList<Data> dataList = new LinkedList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
    /*
                dataList.add(new Data(jsonObject.getString("name"),
                        gson.fromJson(jsonObject.getString("models"), type)));
*/
            }

        }catch(JSONException e){
            e.getStackTrace();
        }
        return dataList;
    }

}
