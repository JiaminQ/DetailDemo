package com.example.jiamin.detaildemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Detail extends Fragment {

    //  public static DetailData detailData;
  //  public static DetailDataLocalJason detailData;
//    public static DetailDataJson detailDataJson;
    private Context context;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Map<String,?>>pic;

    public Fragment_Detail() {
    }

    public static Fragment_Detail newInstance(List<Map<String,?>>piclist) {
        Fragment_Detail fragment = new Fragment_Detail();
        Bundle args = new Bundle();
       // args.p(ARG_SECTION_NUMBER, pic);
        //    movieData = new MovieJsonLocal();
        pic = piclist;
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
  //      detailData = new DetailDataLocalJason();
  //      detailData.loadLocaletailDataJson(getActivity());

     //   context = getActivity();
    //    detailDataJson = new DetailDataJson();

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        drawable.setDither(true);
        rootView.findViewById(R.id.detailview).setBackground(drawable);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        final DetailRecyclerViewAdapter mRecyclerViewAdapter = new DetailRecyclerViewAdapter(getActivity(), pic,rootView);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        return rootView;
    }

    /*   private class MyDownloadMovieDetailAsyncTask extends AsyncTask<String,Void,List> {

 /*    private final WeakReference<OnListItemSelectedListener> weakListener;
         public MyDownloadMovieDetailAsyncTask(OnListItemSelectedListener listener){
             weakListener = new WeakReference<OnListItemSelectedListener>(listener);
         }
        public MyDownloadMovieDetailAsyncTask(){

        }
        @Override
        protected  List doInBackground(String... urls){
            //HashMap movie = new HashMap();
            List<Map<String,?>> pic=new ArrayList<Map<String,?>> ();
            for(String url:urls)
                pic = detailDataJson.downloadSinglePicJson(url,context);
            if(pic==null){
                Log.d("Debug message", "null");
            }
            return pic;
        }
      @Override
        protected  void onPostExecute(HashMap movie){
            final OnListItemSelectedListener listlistener = weakListener.get();
            if(listlistener!=null)
                listlistener.onListItemSelected(movie);

        }
    }*/
}