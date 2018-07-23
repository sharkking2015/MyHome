package com.serenegiant.myhome.ui.fooddetail.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serenegiant.myhome.R;
import com.serenegiant.myhome.ui.fooddetail.vo.FoodDetailBean;

import java.util.ArrayList;

public class FoodDetailAdapter extends RecyclerView.Adapter<FoodDetailAdapter.ViewHolder>{
    private Context context;
    private ArrayList<FoodDetailBean> list;

    public FoodDetailAdapter(Context context, ArrayList<FoodDetailBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 0){
            view = LayoutInflater.from(context).inflate(R.layout.item_detail_title,parent,false);
        }else if(viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.item_detail_compacts,parent,false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_detail_steps,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDetailBean bean = list.get(position);
        if(bean.getType() == 0){
            holder.nameTv.setText(bean.getName());
        }else if(bean.getType() == 1){
            holder.nameTv.setText(bean.getName());
            holder.introduceTv.setText(bean.getIntrodece());
        }else{
            holder.nameTv.setText(bean.getName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView introduceTv;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            introduceTv = itemView.findViewById(R.id.introduce_tv);
        }
    }
}
