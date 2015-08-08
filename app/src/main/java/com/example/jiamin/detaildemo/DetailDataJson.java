package com.example.jiamin.detaildemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiamin on 8/5/15.
 */
public class DetailDataJson {


    public static String FILE_SERVER="http://www.cis.syr.edu/~wedu/Teaching/android/Labs/json/";
    public static String PHP_SERVER="http://www.example.com/index.php/";

    public static final int TYPE0 = 0;   //
    public static final int TYPE1 = 1;   //
    public static final int TYPE2 = 2;   //logo
    public static final int TYPE3 = 3;   //brief
    public static final int TYPE4 = 4;   //attribute pair
    public static final int TYPE5 = 5;   //end logo

    List<Map<String,?>> picsList;
    List<Map<String,?>> equipmentList;
    List<Map<String,?>> detailList;

    public List<Map<String, ?>> getPicsList() {
        return picsList;
    }
    public int getPicsSize(){
        return picsList.size();
    }
    public HashMap getPicsItem(int i){
        if (i >=0 && i < picsList.size()){
            return (HashMap) picsList.get(i);
        } else
            return null;
    }
    public List<Map<String, ?>> getEquipmentsList() {
        return equipmentList;
    }
    public int getEquipmentSize(){
        return equipmentList.size();
    }
    public HashMap getEquipmentItem(int i){
        if (i >=0 && i < equipmentList.size()){
            return (HashMap) equipmentList.get(i);
        } else
            return null;
    }
    public DetailDataJson() {
        equipmentList= new ArrayList<Map<String,?>>();
        picsList = new ArrayList<Map<String,?>>();
        detailList = new ArrayList<Map<String,?>>();
    }

    public void loadLocalMovieDataJson(Context context) {
        picsList.clear(); // clear the list

        // movie.json contains an array of movies
        String moviesArray = MyUtility.loadJSONFromAsset(context, "movie.json");
        if (moviesArray == null){
            Log.d("MyDebugMsg", "Having trouble load movie.json");
            return;
        }

        try {
            JSONArray moviesJsonArray = new JSONArray(moviesArray);
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movieJsonObj = (JSONObject) moviesJsonArray.get(i);
                if (movieJsonObj != null) {
                    String name = (String) movieJsonObj.get("name");
                    double rating = Double.parseDouble(movieJsonObj.get("rating").toString());
                    String url = (String) movieJsonObj.get("url");
                    String description = (String) movieJsonObj.get("description");
                    String id = (String) movieJsonObj.get("id");
                    String image = (String) movieJsonObj.get("image");
                    int resID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                    picsList.add(createMovie(name, resID, description, null, null, rating, null, null, url, id));
                }
            }
        } catch (JSONException ex){
            Log.d("MyDebugMsg", "JSONException in loadLocalMovieDataJson()");
            ex.printStackTrace();
        }
    }

    public void downloadEquipmentsDataJson(String json_url,Context context) {
        equipmentList.clear(); // clear the list

        String moviesArray = MyUtility.downloadJSON(json_url);
        if (moviesArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
            return;
        }

        try {
            JSONArray moviesJsonArray = new JSONArray(moviesArray);
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movieJsonObj = (JSONObject) moviesJsonArray.get(i);
                if (movieJsonObj != null) {
                    String image = (String) movieJsonObj.get("eimage");
                    String title = (String) movieJsonObj.get("model");
                    String seid = (String) movieJsonObj.get("eid");
                    String manufature = (String) movieJsonObj.get("manufature");
                    int eid = Integer.valueOf(seid).intValue();

                    int bgID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                    equipmentList.add(createEquipment_brief(title,eid,manufature,bgID));
                }
            }
        } catch (JSONException ex) {
            Log.d("MyDebugMsg", "JSONException in downloadMovieDataJson");
            ex.printStackTrace();
        }
    }

    public void downloadPicsDataJson(String json_url,Context context) {
        picsList.clear(); // clear the list

        String moviesArray = MyUtility.downloadJSON(json_url);
        if (moviesArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
            return;
        }

        try {
            JSONArray moviesJsonArray = new JSONArray(moviesArray);
            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject movieJsonObj = (JSONObject) moviesJsonArray.get(i);
                if (movieJsonObj != null) {
             //       String description = (String) movieJsonObj.get("description");
             //       String model = (String) movieJsonObj.get("model");
                    String image = (String) movieJsonObj.get("pimage");
               //     String uicon = (String) movieJsonObj.get("uicon");
                //    String uname = (String) movieJsonObj.get("uname");
                    String uname = "uname";
                    String title = (String) movieJsonObj.get("pname");
                    String spid = (String) movieJsonObj.get("pid");
                    int pid = Integer.valueOf(spid).intValue();
                    String location = (String) movieJsonObj.get("location");

                    int bgID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                  //  int uiconID = context.getResources().getIdentifier(uicon, "drawable", context.getPackageName());
                    picsList.add(createPic_brief(title,pid,uname,bgID));
                }
            }
        } catch (JSONException ex) {
            Log.d("MyDebugMsg", "JSONException in downloadMovieDataJson");
            ex.printStackTrace();
        }
    }

    public List<Map<String,?>> downloadSingleEquipmentJson(String json_url,Context context){
        //  String movieJson = MyUtility.downloadJSON(json_url);
        detailList.clear();
        String moviesArray = MyUtility.downloadJSON(json_url);
        if (moviesArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
            return null;
        }

        try {
            //   JSONObject movieJsonObj = new JSONObject(movieJson);
            JSONArray moviesJsonArray = new JSONArray(moviesArray);
            JSONObject movieJsonObj = (JSONObject)moviesJsonArray.get(0);
            if(movieJsonObj==null){
                Log.d("JsonMSG","can't get json obj "+moviesArray);
            }
            if (movieJsonObj != null) {

                String description ="description";
                String model = (String) movieJsonObj.get("model")+(String) movieJsonObj.get("manufature");
                String image = (String) movieJsonObj.get("eimage");
                //    String uicon = (String) movieJsonObj.get("uicon");
            //    String uicon = "uicon1";
            //    String uname = (String) movieJsonObj.get("uname");
                String title = (String) movieJsonObj.get("model");
         //       String location = (String) movieJsonObj.get("location");

                int bgID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
               // int uiconID = context.getResources().getIdentifier(uicon, "drawable", context.getPackageName());
                //    detailList.add(createItem(TYPE0,));
                detailList.add(createItem(TYPE2,bgID,title));
                Log.d("bgID",image);
      //          detailList.add(createItem(TYPE1,uiconID,uname));
                //
        //        detailList.add(createItem(TYPE4,"Location",location));
          //      detailList.add(createItem(TYPE4,"Camera",model));
       //         detailList.add(createItem(TYPE4,"Time","May"));
                detailList.add(createItem(TYPE3,description));
                detailList.add(createItem(TYPE5));
                Log.d("DetailDataJson","createlist");
                return detailList;



            }
        } catch (JSONException ex){
            Log.d("MyDebugMsg", "JSONException in downloadSingleMovieJson");
            ex.printStackTrace();
        }
        return null;

    }

    public List<Map<String,?>> downloadSinglePicJson(String json_url,Context context){
        //  String movieJson = MyUtility.downloadJSON(json_url);
        detailList.clear();
        String moviesArray = MyUtility.downloadJSON(json_url);
        if (moviesArray == null){
            Log.d("MyDebugMsg", "Having trouble loading URL: "+json_url);
            return null;
        }

        try {
            //   JSONObject movieJsonObj = new JSONObject(movieJson);
            JSONArray moviesJsonArray = new JSONArray(moviesArray);
            JSONObject movieJsonObj = (JSONObject)moviesJsonArray.get(0);
            if(movieJsonObj==null){
                Log.d("JsonMSG","can't get json obj "+moviesArray);
            }
            if (movieJsonObj != null) {
         //       String description = (String) movieJsonObj.get("description");
                String description ="description";
          //      String model = (String) movieJsonObj.get("model");
                String model = (String) movieJsonObj.get("manufature")+" "+(String) movieJsonObj.get("model");
                String image = (String) movieJsonObj.get("pimage");
            //    String uicon = (String) movieJsonObj.get("uicon");
                String uicon = "uicon1";
                String uname = (String) movieJsonObj.get("uname");
                String title = (String) movieJsonObj.get("pname");
                String location = (String) movieJsonObj.get("location");

                int bgID = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                int uiconID = context.getResources().getIdentifier(uicon, "drawable", context.getPackageName());
            //    detailList.add(createItem(TYPE0,));
                detailList.add(createItem(TYPE2,bgID,title));
                Log.d("bgID",image);
                detailList.add(createItem(TYPE1,uiconID,uname));
                //
                detailList.add(createItem(TYPE4,"Location",location));
                detailList.add(createItem(TYPE4,"Camera",model));
         //       detailList.add(createItem(TYPE4,"Time","May"));
                detailList.add(createItem(TYPE3,description));
                detailList.add(createItem(TYPE5));
                Log.d("DetailDataJson","createlist");
                return detailList;



            }
        } catch (JSONException ex){
            Log.d("MyDebugMsg", "JSONException in downloadSingleMovieJson");
            ex.printStackTrace();
        }
        return null;

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
        return movie;
    }


    private static HashMap createPic_brief(String pname, int pid,
                                             String uname,int image) {
        HashMap movie = new HashMap();

        movie.put("pname", pname);
        movie.put("pid", pid);
        movie.put("uname",uname);
        movie.put("image",image);
        return movie;
    }
    private static HashMap createEquipment_brief(String model, int eid,
                                           String manufature,int image) {
        HashMap movie = new HashMap();

        movie.put("model", model);
        movie.put("eid", eid);
        movie.put("manufature",manufature);
        movie.put("image",image);
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

}
