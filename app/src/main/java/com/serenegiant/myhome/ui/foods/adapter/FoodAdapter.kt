package com.serenegiant.myhome.ui.foods.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.serenegiant.myhome.ItemClickListener
import com.serenegiant.myhome.R
import com.serenegiant.myhome.bean.FoodBean
import kotlinx.android.synthetic.main.item_food.view.*

class FoodAdapter(var context: Context,var list:ArrayList<FoodBean>,var listener:ItemClickListener):RecyclerView.Adapter<FoodAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_food,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var bean = list.get(position)
        with(holder.itemView){
            name_tv.text = bean.name
            introduce.text = bean.introduce
            if(null == bean.picUrl || "".equals(bean.picUrl)){
                image.setImageResource(R.drawable.no_pic)
            }else{
                image.setImageBitmap(BitmapFactory.decodeFile(bean.picUrl))
            }

        }
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            listener.OnItemLongClicked(position)
            false
        })

        holder.itemView.setOnClickListener(View.OnClickListener {
            listener.OnItemClicked(position)
        })
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
}