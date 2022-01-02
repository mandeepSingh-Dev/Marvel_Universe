package com.oyelabs.marvel.universe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marveluniverse.R

class MyAdapter_for_SearchList(context:Context,arrayListString:ArrayList<String>) :RecyclerView.Adapter<MyAdapter_for_SearchList.MyViewHolder>()
{
    var context1=context
    var charctrList=arrayListString

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view=LayoutInflater.from(context1).inflate(R.layout.searchitems_list,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
              holder.charcterName.text=charctrList.get(position)

    }

    override fun getItemCount(): Int {
   return charctrList.size
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
     var charcterName=itemView.findViewById<TextView>(R.id.charctrNames)
       // var charcterImage=itemView.findViewById<TextView>(R.id.charcImage)

    }
}