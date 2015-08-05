package com.example.jiamin.detaildemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

/**
 * Created by jiamin on 8/5/15.
 */
public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.ViewHolder> {
    private List<Map<String, ?>> mDataset;
    private Context mContext;
    private int mCurrentPosition = 0;

    // Constructor
    public DetailRecyclerViewAdapter(Context myContext, List<Map<String, ?>> myDataset) {
        mContext = myContext;
        mDataset = myDataset;
        Log.d("DetailRecyclerViewAdapter","structure function");

    }

    //Setup the View
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        switch(viewType){
            case DetailData.TYPE1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_detail_userinfo, parent, false);
                break;
            case DetailData.TYPE2:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_detail_logo, parent, false);
                break;
            case DetailData.TYPE3:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_detail_brief, parent, false);
                break;
            case DetailData.TYPE4:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_detail_attributepair, parent, false);
                break;
            case DetailData.TYPE5:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_detail_endpage, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_detail_logo, parent, false);
                break;
        }

        ViewHolder vh = new ViewHolder(v, viewType);
        return vh;
    }
    //replace the contents of a view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Map<String, ?> item = mDataset.get(position);
        holder.bindMovieData(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vValue;
        public View vView;

        public int vViewtype;

        public ViewHolder(View v,int viewtype) {
            super(v);
            vView =v;
            vViewtype = viewtype;
            vIcon = (ImageView) v.findViewById(R.id.icon);
            vTitle = (TextView) v.findViewById(R.id.title);
            vValue = (TextView) v.findViewById(R.id.value);
      //      vLine = (ImageView)v.findViewById(R.id.Line);


        }

        public void bindMovieData(Map<String, ?> item,int position){

            switch (vViewtype){
                case DetailData.TYPE1:
                    if(vIcon!=null)
                        vIcon.setImageResource(((Integer)item.get("icon")).intValue());
                    if(vTitle!=null)
                        vTitle.setText((String)item.get("title"));
                    break;
                case DetailData.TYPE2:
                    if(vTitle!=null)
                        vTitle.setText((String)item.get("title"));
                    /*if(vIcon!=null)
                        vIcon.setImageResource(((Integer)item.get("icon")).intValue());*/
                    break;
                case DetailData.TYPE3:
                    if(vTitle!=null)
                        vTitle.setText((String)item.get("title"));
                    break;
                case DetailData.TYPE4:
                    if(vTitle!=null)
                        vTitle.setText((String)item.get("title"));
                    if(vValue!=null)
                        vValue.setText((String)item.get("value"));
                    break;
                case DetailData.TYPE5:

                    break;
                default:
                    break;


            }
        }
    }

    @Override
    public int getItemViewType(int position){
        Map<String, ?> item = mDataset.get(position);
        return (Integer) item.get("type");
    }
    @Override
    public int getItemCount() { return mDataset.size();}

    public Object getItem(int position) {return mDataset.get(position);}


}
