package com.oyelabs.marvel.universe

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marveluniverse.R

class MyAdapter(context:Context,characterList:ArrayList<list_items>): RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
   var context=context
    var arraylist=characterList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
     var view=LayoutInflater.from(context).inflate(R.layout.items_list,parent,false)
     return MyViewHolder(context,view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.characterName?.text=arraylist.get(position).name
      holder.description?.text=arraylist.get(position).description

        val path=arraylist.get(position).thumbnailArry.get(0)
        val extension=arraylist.get(position).thumbnailArry.get(1)
        Log.d("pathextension",path+"."+extension)
        val finalimagelink=path+"."+extension
     Glide.with(context).asBitmap().load(replace_http_To_https(finalimagelink)).into(holder?.characterImage!!)


    }

    override fun getItemCount(): Int {
   return arraylist.size
    }
    class MyViewHolder(context: Context?,itemView: View?):RecyclerView.ViewHolder(itemView!!)
    {
     var characterImage=itemView?.findViewById<ImageView>(R.id.characterImage)
     var characterName=itemView?.findViewById<TextView>(R.id.charcaterName)
     var description=itemView?.findViewById<TextView>(R.id.characterDescription)

        init {
            Log.d("dsdsds","fjksd")
            var animation=AnimationUtils.loadAnimation(context,R.anim.items_animation)
            itemView?.animation=animation
        }

    }

    fun replace_http_To_https(link:String):String {
        return link.replace("http","https")}

}