package com.oyelabs.marvel.universe

import java.io.Serializable

data class list_items(val id:Int,val name:String,val description:String,
                      val thumbnailArry:Array<String>
                      ,val series:ArrayList<String>?,val url:String?,):Serializable
{
    constructor(id2:Int,name2:String,description2:String,thumbnailArry2:Array<String>):this(id2,name2,description2,thumbnailArry2,null,null)

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}