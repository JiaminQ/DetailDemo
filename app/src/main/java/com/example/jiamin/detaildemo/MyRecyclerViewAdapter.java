package com.example.jiamin.detaildemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiamin on 8/7/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Map<String, ?>> mDataset;
    OnItemClickListener mItemClickListener;   ///check
 //   LruCache<String,Bitmap> mImgMemoryCache;

    public MyRecyclerViewAdapter(Context context, List<Map<String, ?>> movieList) {
        mContext = context;
        mDataset = movieList;
  //      mImgMemoryCache = ImgMemoryCache;
    }
    /*
        public MyRecyclerViewAdapter(Context context, List<Map<String, ?>> movieList,LruCache<String,Bitmap> ImgMemoryCache) {
        mContext = context;
        mDataset = movieList;
  //      mImgMemoryCache = ImgMemoryCache;
    }

    */


    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType){
     /*   View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;*/
        View v;
        switch (viewType){
            case 0:
                v = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.gridcardview, parent, false);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.fragment_singlegridview, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.gridcardview,parent,false);
                break;
        }
        return new  ViewHolder(v);
    }
    //if I already have a view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Map<String,?>movie = mDataset.get(position);
        holder.bindMovieData(movie);


    }
    @Override
    public int getItemCount(){return mDataset.size();}
    @Override
    public int getItemViewType(int position){
        Map<String,?>movie = mDataset.get(position);
        return 1;


    }


    public Object getItem(int position){
        return mDataset.get(position);
    }
    @Override
    public long getItemId(int position){
        return 0;
    }
    public void removeItem(int position){
        mDataset.remove(position);
    }
    public void duplicateItem(int position){
        HashMap<String,?> movie = (HashMap<String,?>)mDataset.get(position);
        mDataset.add(position,(HashMap<String,?>)movie.clone());
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
        public void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;



        public ViewHolder(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.icon);
            vTitle = (TextView) v.findViewById(R.id.title);



            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mItemClickListener!=null){
                        mItemClickListener.onItemClick(v,getPosition());
                    }
                }

            });
            v.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    if(mItemClickListener!=null){
                        mItemClickListener.onItemLongClick(v,getPosition());
                    }
                    return true;
                }


            });


        }
        public void bindMovieData(Map<String,?>movie){
            vTitle.setText((String)movie.get("pname"));
            vIcon.setImageResource((Integer)movie.get("image"));




        }




    }///the end of holder view class


}

