package com.hongsam.famstory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hongsam.famstory.define.Define;
import com.hongsam.famstory.define.ViewHolderStruct;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolderStruct.BasicViewHolder> {

    Context mContext;
    int layoutId;
    int viewType;
    Object mDataList;

    public RecyclerAdapter(Context context, int layoutId, int viewType, Object datList) {
        mContext = context;
        this.layoutId = layoutId;
        this.viewType = viewType;
        mDataList = datList;
    }


    /**
     * 뷰 객체 생성
     * */
    @NonNull
    @Override
    public ViewHolderStruct.BasicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View holder = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);

        switch (viewType) {
            case Define.VIEWTYPE_MEMBER:
                return new ViewHolderStruct.ViewHolderMember(holder, mContext, mDataList);
            default:
                return null;
        }
    }


    /**
     * 뷰 아이템 생성 (리스트 size만큼 생성)
     * */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderStruct.BasicViewHolder viewHolder, int position) {
        viewHolder.init(position);
    }

    @Override
    public int getItemCount() {
        return ((ArrayList)mDataList).size();
    }
}
