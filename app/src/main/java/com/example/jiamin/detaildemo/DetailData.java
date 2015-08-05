package com.example.jiamin.detaildemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiamin on 8/5/15.
 */
public class DetailData {
    public static final int TYPE0 = 0;   //
    public static final int TYPE1 = 1;   //
    public static final int TYPE2 = 2;   //logo
    public static final int TYPE3 = 3;   //brief
    public static final int TYPE4 = 4;   //attribute pair
    public static final int TYPE5 = 5;   //end logo



    List<Map<String,?>> drawerList;
    Integer bgImageID;

    public List<Map<String,?>> getDataList(){return drawerList;}

    public Map<String,?> getItem(int position){return drawerList.get(position);}
    public Integer getBGID(){return bgImageID;}

    public int getSize(){return drawerList.size();}

    public DetailData(){

        drawerList = new ArrayList<Map<String,?>>();

        //item 1-3  type 1

      //  bgImageID = R.drawable.bg1;
        drawerList.add(createItem(TYPE2,R.drawable.home,"Seaside"));
        drawerList.add(createItem(TYPE1,R.drawable.uicon1,"User1"));
     //
        drawerList.add(createItem(TYPE4,"Location","San Diego"));
        drawerList.add(createItem(TYPE3,"From La Jolla beaches to Anza Borrego State Park, " +
                "these are the best spots to photograph in San Diego County. If you are planning" +
                "a vacation in San Diego."));
        drawerList.add(createItem(TYPE5));
    /*    drawerList.add(createItem(TYPE3,"Movie ListRecyclerView"));
        drawerList.add(createItem(TYPE3,"Drag and Drop"));
        //   drawerList.add(createItem(TYPE1,R.drawable.grid,"Grid style Movie"));

        //item 4 type 2
      //  drawerList.add(createItem(TYPE2,R.drawable.bar));

        //item 5-8 type 3
        drawerList.add(createItem(TYPE3,"About Me"));
        drawerList.add(createItem(TYPE3,"Home"));
        drawerList.add(createItem(TYPE3,"Fragment Animation1"));
        drawerList.add(createItem(TYPE3,"Fragment Animation2"));*/


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