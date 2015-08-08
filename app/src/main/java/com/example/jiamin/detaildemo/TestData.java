package com.example.jiamin.detaildemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiamin on 8/7/15.
 */
public class TestData {

    List<Map<String,?>> dataList;

    public List<Map<String, ?>> getMoviesList() {
        return dataList;
    }

    public int getSize(){
        return dataList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < dataList.size()){
            return (HashMap) dataList.get(i);
        } else return null;
    }

    public TestData(){
        String title;
        dataList = new ArrayList<Map<String,?>>();

        dataList.add(createMovie("Seaside",R.drawable.bg1,1));
        dataList.add(createMovie("SU",R.drawable.bg2,2));
    }


    private HashMap createMovie(String title, int image,int id) {
        HashMap movie = new HashMap();
        movie.put("image",image);
        movie.put("icon", title);
        movie.put("id",id);
        movie.put("selection",false);

        return movie;
    }
}