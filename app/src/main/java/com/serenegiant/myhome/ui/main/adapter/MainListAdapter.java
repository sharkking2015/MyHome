package com.serenegiant.myhome.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.serenegiant.myhome.ItemClickListener;
import com.serenegiant.myhome.R;
import com.serenegiant.myhome.ui.main.vo.MainItemBean;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder>{
    ArrayList<MainItemBean> list;
    Context context;
    ItemClickListener listener;

    public MainListAdapter(Context context,ItemClickListener listener,ArrayList<MainItemBean> list) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_main_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MainItemBean bean = list.get(position);
        holder.iconImage.setImageResource(bean.getResourceId());
        holder.messageTv.setText(bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iconImage;
        private TextView messageTv;

        public ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.icon);
            messageTv = itemView.findViewById(R.id.message_tv);

        }
    }
}
