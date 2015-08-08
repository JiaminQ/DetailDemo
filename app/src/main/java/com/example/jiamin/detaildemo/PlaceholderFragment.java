package com.example.jiamin.detaildemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by jiamin on 7/31/15.
 */
public class PlaceholderFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER="section_number";
    //    public static MovieJsonLocal movieData;
  //  public static MovieDataJson movieData;
  //  private  OnListItemSelectedListener mListner;
 //   public MyRecyclerViewAdapter mRecyclerViewAdapter;
    //  public static android.widget.SearchView searchview;
//    private static Menu menu;

    public LruCache<String,Bitmap> mImgMemoryCache;

    /*   final private class MyDownloadImageAsyncTask extends AsyncTask<String,Void, Bitmap> {


           private final WeakReference<ImageView> imageViewReference;
           public MyDownloadImageAsyncTask(ImageView imv){
               imageViewReference = new WeakReference<ImageView>(imv);

           }
           @Override
           protected  Bitmap doInBackground(String... urls){
               Bitmap bitmap =null;
               for(String url:urls){
                   bitmap = MyUtility.downloadImage(url);
                   if(bitmap!=null){
                       mImgMemoryCache.put(url,bitmap);
                   }
               }
               return bitmap;
           }
           @Override
           protected void onPostExecute(Bitmap bitmap){
               if(imageViewReference!=null && bitmap!=null){
                   final ImageView imageView = imageViewReference.get();
                   if(imageView != null){
                       imageView.setImageBitmap(bitmap);
                   }
               }
           }
       }

   *///  public static PlaceholderFragment newInstance(int sectionNumber,android.widget.SearchView search){
    public static PlaceholderFragment newInstance(int sectionNumber){
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public PlaceholderFragment() {

    }

  /*  public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }*/
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);


//

   //     movieData = new MovieDataJson();
        //  movieData = new MovieJsonLocal();

        //     movieData.downloadMovieDataJson(MovieDataJson.PHP_SERVER+"movies/");
        //    movieData.loadLocalMovieDataJson(getActivity());


        if(mImgMemoryCache==null){
            final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
            final int cacheSize = maxMemory/8;
            mImgMemoryCache = new LruCache<String,Bitmap>(cacheSize){
                @Override
                protected  int sizeOf(String key,Bitmap bitmap){
                    return bitmap.getByteCount()/1024;
                }
            };

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView;

        setRetainInstance(true);
        int option = getArguments().getInt(ARG_SECTION_NUMBER);
        switch(option){



            case R.id.selection_AboutMe:
                rootView = inflater.inflate(R.layout.fragment_aboutme,container,false);
                break;



            default:
                //lab 9
                rootView = inflater.inflate(R.layout.fragment_main, container, false);
                Button btnMaster = (Button)rootView.findViewById(R.id.btnJumpActivity1);
                btnMaster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    Intent intent = new Intent(getActivity(),ExerciseMainActivity.class);
                     //   startActivity(intent);
                        Intent intent = new Intent(getActivity(),DetailActivity.class);
                        startActivity(intent);

                    }
                });
                Button btnMasterTask2 = (Button)rootView.findViewById(R.id.btnJumpActivity2);
                btnMasterTask2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),RecyclerViewActivity.class);
                        startActivity(intent);

                    }
                });



        }




        return rootView;
    }










    }






