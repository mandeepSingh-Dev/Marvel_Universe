package com.oyelabs.marvel.universe

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class list_items(val id:Int,val name:String,val description:String,
                      val thumbnailArry:Array<String>
                      ,val series:ArrayList<String>?,val url:String?,):Serializable,Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArray()!!,
        TODO("series"),
        parcel.readString()
    ) {
    }

    constructor(id2:Int, name2:String, description2:String, thumbnailArry2:Array<String>):this(id2,name2,description2,thumbnailArry2,null,null)

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeStringArray(thumbnailArry)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<list_items> {
        override fun createFromParcel(parcel: Parcel): list_items {
            return list_items(parcel)
        }

        override fun newArray(size: Int): Array<list_items?> {
            return arrayOfNulls(size)
        }
    }
}