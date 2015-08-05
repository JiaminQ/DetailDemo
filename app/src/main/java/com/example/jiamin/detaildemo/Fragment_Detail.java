package com.example.jiamin.detaildemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Fragment_Detail extends Fragment {

  //  public static DetailData detailData;
    public static DetailDataLocalJason detailData;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment_Detail() {
    }

    public static Fragment_Detail newInstance(int sectionNumber) {
        Fragment_Detail fragment = new Fragment_Detail();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        //    movieData = new MovieJsonLocal();

        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        detailData = new DetailDataLocalJason();
        detailData.loadLocaletailDataJson(getActivity());

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
        final DetailRecyclerViewAdapter mRecyclerViewAdapter = new DetailRecyclerViewAdapter(getActivity(), detailData.getDataList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        return rootView;
    }

}