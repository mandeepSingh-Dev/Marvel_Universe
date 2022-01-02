package com.oyelabs.marvel.universe

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marveluniverse.R

class MyAdapter_SeriesItems(context: Context, arrayList:ArrayList<String>):RecyclerView.Adapter<MyAdapter_SeriesItems.MyViewHolder>() {

    var context=context
    var seriesList=arrayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view=LayoutInflater.from(context).inflate(R.layout.series_item,parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

     holder.seriesTextView.text=seriesList.get(position)
    }

    override fun getItemCount(): Int {
   return  seriesList.size
    }
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        var seriesTextView=itemView.findViewById<TextView>(R.id.seriesTextView)

    }
}