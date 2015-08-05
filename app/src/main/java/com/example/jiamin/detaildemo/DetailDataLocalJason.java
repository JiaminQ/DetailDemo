package com.example.jiamin.detaildemo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiamin on 8/5/15.
 */
public class DetailDataLocalJason {
    public static final int TYPE0 = 0;   //
    public static final int TYPE1 = 1;   //
    public static final int TYPE2 = 2;   //logo
    public static final int TYPE3 = 3;   //brief
    public static final int TYPE4 = 4;   //attribute pair
    public static final int TYPE5 = 5;   //end logo
    List<Map<String,?>> detailList;

    public List<Map<String, ?>> getDataList() {
        return detailList;
    }
    public int getSize(){
        return detailList.size();
    }
    public HashMap getItem(int i){
        if (i >=0 && i < detailList.size()){
            return (HashMap) detailList.get(i);
        } else
            return null;
    }

    public DetailDataLocalJason() {
        detailList = new ArrayList<Map<String,?>>();
    }

    public void loadLocaletailDataJson(Context context) {
        detailList.clear(); // clear the list

        // movie.json contains an array of movies
        String moviesArray = loadFileFromAsset(context, "detail.json");
        if (moviesArray == null){
            Log.d("MyDebugMsg", "Having trouble load movie.json");
            return;
        }

        try {
            // Parse the string to construct JSON array
            JSONArray moviesJsonArray = new JSONArray(moviesArray);
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movieJsonObj = (JSONObject) moviesJsonArray.get(i);
                if (movieJsonObj != null) {
              //      String pid = (String) movieJsonObj.get("pid");
              //      Integer plike = Integer.parseInt(movieJsonObj.get("plike").toString());
                    String description = (String) movieJsonObj.get("description");
                    String model = (String) movieJsonObj.get("model");
                    String image = (String) movieJsonObj.get("pimage");
                    String uicon = (String) movieJsonObj.get("uicon");
                    String uname = (String) movieJsonObj.get("uname");
                    String title = (String) movieJsonObj.get("pname");
                    String location = (String) movieJsonObj.get("location");

                    int bgID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                    int uiconID = context.getResources().getIdentifier(uicon, "drawable", context.getPackageName());
                    detailList.add(createItem(TYPE2,bgID,title));
                    detailList.add(createItem(TYPE1,uiconID,uname));
                    //
                    detailList.add(createItem(TYPE4,"Location",location));
                    detailList.add(createItem(TYPE4,"Camera",model));
                    detailList.add(createItem(TYPE3,description));
                    detailList.add(createItem(TYPE5));
                    Log.d("DetailDataLocalJson","createlist");
                }
            }
        } catch (JSONException ex){
            Log.d("MyDebugMsg", "JSONException in loadLocalMovieDataJson()");
            ex.printStackTrace();
        }
    }

    public String loadFileFromAsset(Context context, String fileName) {
        String contents = null, line;
        try {
            InputStream stream = context.getAssets().open(fileName);
            if (stream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder out = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                contents = out.toString();
            }
        } catch (IOException ex) {
            Log.d("MyDebugMsg", "IOException in loadFileFromAsset()");
            ex.printStackTrace();
        }
        return contents;
    }

    private static HashMap createMovie(String name, int image, String description, String year,
                                       String length, double rating, String director, String stars, String url, String id) {
        HashMap movie = new HashMap();
        movie.put("image",image);
        movie.put("name", name);
        movie.put("description", description);
        movie.put("year", year);
        movie.put("length",length);
        movie.put("rating",rating);
        movie.put("director",director);
        movie.put("stars",stars);
        movie.put("url",url);
        movie.put("id",id);
        movie.put("selection",false);
        return movie;
    }
    private static HashMap createDetial(String title,int bgID,int uiconID,String description, String model, String uname, String location) {
        HashMap movie = new HashMap();
        movie.put("title",title);
        movie.put("image",bgID);
        movie.put("uicon",uiconID);
        movie.put("description", description);
        movie.put("model", model);
        movie.put("uname",uname);
        movie.put("location",location);
        movie.put("selection",false);
        return movie;
    }
    //TYPE1
    private HashMap createItem(int type, int iconid,String title) {
        HashMap movie = new HashMap();
        movie.put("type",type);
        movie.put("icon", iconid);
        movie.put("title", title);

        return movie;
    }
    //TYPE2
    private HashMap createItem(int type, int iconid) {
        HashMap movie = new HashMap();
        movie.put("type",type);
        movie.put("icon", iconid);

        return movie;
    }
    //TYPE3
    private HashMap createItem(int type,String title) {
        HashMap movie = new HashMap();
        movie.put("type",type);
        movie.put("title", title);

        return movie;
    }
    //TYPE4
    private HashMap createItem(int type,String title,String value) {
        HashMap movie = new HashMap();
        movie.put("type",type);
        movie.put("title", title);
        movie.put("value", value);

        return movie;
    }
    //TYPE5
    private HashMap createItem(int type) {
        HashMap movie = new HashMap();
        movie.put("type",type);

        return movie;
    }

  /*  private static HashMap createMovie_brief(String name, String description,
                                             double rating, String url, String id) {
        HashMap movie = new HashMap();

        movie.put("name", name);
        movie.put("description", description);
        movie.put("rating",rating);
        movie.put("url",url);
        movie.put("id",id);


        return movie;
    }*/
}
