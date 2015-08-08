package com.example.jiamin.detaildemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiamin on 8/8/15.
 */
public class RecyclerViewFragment extends Fragment {


    public static DetailDataJson detailData;
    private  OnListItemSelectedListener mListner;
    private static final String ARG_SECTION_NUMBER="section_number";
    private RotaryKnobView rotaryKnob;

    ///----RotaryKnob
    private Canvas canvas;
    private Bitmap backgroundBitmap;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private int angle = 0;
    private int scale1 = 180;
    private int scale2 = 0;



    public static RecyclerViewFragment newInstance(int sectionNumber){
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,sectionNumber);
        //    movieData = new MovieJsonLocal();

        return fragment;
    }
    public RecyclerViewFragment() {

    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);

        boolean connect = isNetworkAvailable(getActivity());
        if(connect==false)
            Log.d("network connect","false");
        else
            Log.d("network connect","connected");
        detailData = new DetailDataJson();
    //    detailData.downloadMovieDataJson(DetailDataJson.PHP_SERVER+"pics/",getActivity());
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        //     View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //     View rootView = inflater.inflate(R.layout.fragment_seekbar, container, false);
        final View rootView;


        setRetainInstance(true);
        rootView = inflater.inflate(R.layout.fragment_recyclerview,container,false);
  ///---rotaryKnob
   //     final RotaryKnobView
        rotaryKnob = (RotaryKnobView)rootView.findViewById(R.id.rotaryKnobView);
        rotaryKnob.setCurrentValue(angle);
        rotaryKnob.setOnRotateListener(new RotaryKnobView.OnRotateListener(){
            @Override
           public void onRotate(int arg){
            //
                angle = arg;
                if(arg ==scale1){
                    setUpEquipmentAdapter();


                    Log.d("onRotate",Integer.toString(angle)+" scale1");
                }
                if(arg ==scale2){
                    setUpPicAdapter();
                    Log.d("onRotate",Integer.toString(angle)+" scale2");


                }
            }
        });


        setRecyclerView(rootView);




        return rootView;
    }

    public interface OnListItemSelectedListener{
        public void onListItemSelected(List<Map<String,?>>pic);

    }
    //   public Activity_CoverPage extends ActionBarActivity implements OnListItemSelectedListener;
    @Override
    public void onAttach(Activity activity){
        super.onAttach((activity));
        try{
            mListner = (OnListItemSelectedListener)activity;

        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString());

        }

    }


    public void setRecyclerView(View rootView){

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        if(angle<=scale1) {
            setUpPicAdapter();

            Log.d("setUpView",Integer.toString(angle)+" scale1");


        }else {
            setUpEquipmentAdapter();
            Log.d("setUpView",Integer.toString(angle)+" scale2");

        }

        // mRecyclerViewAdapter.setOn



    }
    private void setUpEquipmentAdapter(){
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),detailData.getEquipmentsList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        MyDownloadEquipmentJsonAsyncTask downloadJson = new MyDownloadEquipmentJsonAsyncTask(mRecyclerViewAdapter);
        // String url = DetailDataJson.PHP_SERVER + "pics/";
        //   String url = "http://www.example.com/index.php/movies/";
        String url =DetailDataJson.PHP_SERVER+"equipments/";
        downloadJson.execute(new String[]{url});
        //    Log.d("equipment length", Integer.toString(detailData.getEquipmentSize()));
        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("equipment length", Integer.toString(detailData.getEquipmentSize()));
                HashMap<String,?>movie = (HashMap<String,?>)detailData.getEquipmentItem(position);
                //    mListner.onListItemSelected(movie);
                int pid = (Integer)movie.get("eid");
                String id = Integer.toString(pid);
                Log.d("onItemClick",id);
                String url = DetailDataJson.PHP_SERVER+"equipments/id/"+id;
                //      String url = MovieDataJson.FILE_SERVER+(String)movie.get("id")+".json";
                MyDownloadEquipmentDetailAsyncTask downloadDetail = new MyDownloadEquipmentDetailAsyncTask(mListner);
                downloadDetail.execute(url);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Not action on long click", Toast.LENGTH_LONG).show();

            }

        });

    }
    private void setUpPicAdapter(){
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), detailData.getPicsList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        MyDownloadJsonAsyncTask downloadJson = new MyDownloadJsonAsyncTask(mRecyclerViewAdapter);
        String url = DetailDataJson.PHP_SERVER+"pics/";
        downloadJson.execute(new String[]{url});


        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                HashMap<String, ?> movie = (HashMap<String, ?>) detailData.getPicsItem(position);
                //    mListner.onListItemSelected(movie);
                int pid = (Integer) movie.get("pid");
                String id = Integer.toString(pid);
                Log.d("onItemClick", id);
                String url = DetailDataJson.PHP_SERVER + "pics/id/" + id;
                //      String url = MovieDataJson.FILE_SERVER+(String)movie.get("id")+".json";
                MyDownloadPicDetailAsyncTask downloadDetail = new MyDownloadPicDetailAsyncTask(mListner);
                downloadDetail.execute(url);
            }

            @Override
            public void onItemLongClick(View view, int position) {
               /* mRecyclerViewAdapter.duplicateItem(position);
                mRecyclerViewAdapter.notifyItemInserted(position+1);*/
                Toast.makeText(getActivity(), "Not action on long click", Toast.LENGTH_LONG).show();

            }

        });

    }

    private class MyDownloadPicDetailAsyncTask extends AsyncTask<String,Void,List> {

        private final WeakReference<OnListItemSelectedListener> weakListener;
        public MyDownloadPicDetailAsyncTask(OnListItemSelectedListener listener){
            weakListener = new WeakReference<OnListItemSelectedListener>(listener);

        }
        @Override
        protected  List doInBackground(String... urls){
            List pic = new ArrayList<Map<String,?>>();
            for(String url:urls)
                pic = detailData.downloadSinglePicJson(url,getActivity()) ;
            if(pic==null){
                Log.d("Debug message","null");
            }
            else {
                Log.d("Pic Length",Integer.toString(pic.size()));
            }
            return pic;
        }
        @Override
        protected  void onPostExecute(List pic){
            final OnListItemSelectedListener listlistener = weakListener.get();
            if(listlistener!=null)
                listlistener.onListItemSelected(pic);

        }
    }

    private class MyDownloadEquipmentDetailAsyncTask extends AsyncTask<String,Void,List> {

        private final WeakReference<OnListItemSelectedListener> weakListener;
        public MyDownloadEquipmentDetailAsyncTask(OnListItemSelectedListener listener){
            weakListener = new WeakReference<OnListItemSelectedListener>(listener);

        }
        @Override
        protected  List doInBackground(String... urls){
            List equipment = new ArrayList<Map<String,?>>();
            for(String url:urls)
                equipment = detailData.downloadSingleEquipmentJson(url,getActivity()) ;
            if(equipment==null){
                Log.d("Debug message","null");
            }

            return equipment;
        }
        @Override
        protected  void onPostExecute(List equipment){
            final OnListItemSelectedListener listlistener = weakListener.get();
            if(listlistener!=null)
                listlistener.onListItemSelected(equipment);

        }
    }

    private class MyDownloadJsonAsyncTask extends AsyncTask<String,Void,DetailDataJson> {
        private  final WeakReference<MyRecyclerViewAdapter> adapterReference;
        public  MyDownloadJsonAsyncTask(MyRecyclerViewAdapter adapter){
            adapterReference = new WeakReference<MyRecyclerViewAdapter>(adapter);
        }
        @Override
        protected  DetailDataJson doInBackground(String... urls){
            DetailDataJson threadMovieData = new DetailDataJson();
            for(String url:urls){
                threadMovieData.downloadPicsDataJson(url,getActivity());
            }

            return threadMovieData;
        }
        @Override
        protected  void onPostExecute(DetailDataJson threadMovieData){
            detailData.getPicsList().clear();
            for(int i=0;i<threadMovieData.getPicsSize();i++){
                detailData.getPicsList().add(threadMovieData.getPicsList().get(i));
            }

            if(adapterReference!=null){
                final MyRecyclerViewAdapter adapter = adapterReference.get();
                if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }
            }

        }
    }
    private class MyDownloadEquipmentJsonAsyncTask extends AsyncTask<String,Void,DetailDataJson> {
        private  final WeakReference<MyRecyclerViewAdapter> adapterReference;
        public  MyDownloadEquipmentJsonAsyncTask(MyRecyclerViewAdapter adapter){
            adapterReference = new WeakReference<MyRecyclerViewAdapter>(adapter);
        }
        @Override
        protected  DetailDataJson doInBackground(String... urls){
            DetailDataJson threadEquipmentData = new DetailDataJson();
            for(String url:urls){
                threadEquipmentData.downloadEquipmentsDataJson(url,getActivity());
            }

            return threadEquipmentData;
        }
        @Override
        protected  void onPostExecute(DetailDataJson threadMovieData){
            detailData.getEquipmentsList().clear();
            for(int i=0;i<threadMovieData.getEquipmentSize();i++){
                detailData.getEquipmentsList().add(threadMovieData.getEquipmentsList().get(i));
            }

            if(adapterReference!=null){
                final MyRecyclerViewAdapter adapter = adapterReference.get();
                if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }
            }

        }
    }

}
