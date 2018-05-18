package com.rorrim.mang.smartmirror.Unused;

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
 /*
    public LinkedList<Data> parseJson(String jsonStr){

        LinkedList<Data> dataList = new LinkedList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();

                dataList.add(new Data(jsonObject.getString("name"),
                        gson.fromJson(jsonObject.getString("models"), type)));

            }

        }catch(JSONException e){
            e.getStackTrace();
        }
        return dataList;
    }
*/
}
