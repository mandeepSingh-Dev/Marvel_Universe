package com.oyelabs.marvel.universe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.marveluniverse.R
import org.json.JSONObject

class Character_Fragment : Fragment() {

    private lateinit var receiver: BroadcastReceiver
    private var charactersListsFragment:Characters_Fragment?=null
    private lateinit var receiver2:BroadcastReceiver
    private var charcaterId:Int?=null
    private var requestQueue:RequestQueue?=null
    private var characterList:ArrayList<list_items>?=null
    private var seriesArrayList:ArrayList<String>?=null
    var characterimagee:ImageView?=null
    var characterName:TextView?=null
    var DescriptionTEXT:TextView?=null
    var linkText:TextView?=null
    var seriesRecyclerView:RecyclerView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersListsFragment= Characters_Fragment()
        characterList=ArrayList()
        seriesArrayList=ArrayList()



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize_Views(view)
        Log.d("SIIIZIE","fragmentCharacter")

        /**Receiving textQuery from MainActivity SearchView*/
        receiver=object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {

                if(intent?.action.equals("SEND_QUERY_2")) {
                    var newText = intent?.getStringExtra("newText2").toString()
                    Log.d("sdsdsds", newText)
                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter("SEND_QUERY_2"))

        if(arguments!=null) {
             charcaterId = arguments?.getInt("CHARACTER_ID")
            Log.d("fdfdfdfdcdfe",charcaterId.toString())
            getting(charcaterId!!)
        }

        }//End of onViewCreated
    fun getting(characterID:Int){

        var url="https://gateway.marvel.com/v1/public/characters/"+characterID+"?ts=1&apikey=466d97f4e5bf657ce8780e7b317c57d7&hash=4a866f2f4a437b10fcd9744b8286b4db"
        requestQueue= Volley.newRequestQueue(context)
        var jsonObjectRequest=JsonObjectRequest(Request.Method.GET,url,null,object:Response.Listener<JSONObject>{
            override fun onResponse(response: JSONObject?) {

                val resultsArray = response?.getJSONObject("data")?.getJSONArray("results")
                for (i in 0..resultsArray?.length()?.minus(1)!!) {
                    var resultobject = resultsArray.getJSONObject(i)
                    var name = resultobject.getString("name")
                    Log.d("njhdgfhgsdcniwefuyiwe",name)
                    var id = resultobject.getInt("id")
                    var description = resultobject.getString("description")
                    var path = resultobject.getJSONObject("thumbnail").getString("path")
                    var extension = resultobject.getJSONObject("thumbnail").getString("extension")
                    var itemsArray=resultobject.getJSONObject("series").getJSONArray("items")
                      for(i in 0..itemsArray.length().minus(1))
                      {
                         var itemObject=itemsArray.getJSONObject(i)
                          seriesArrayList?.add(itemObject.getString("name"))
                      }
                    var urlsArray=resultobject.getJSONArray("urls")
                    var url=urlsArray.getJSONObject(0).getString("url")

                    /**creating thumbnail array of path and extension*/
                    var thumnailarray= arrayOf(path,extension)
                    characterList?.add( list_items(id,name,description,thumnailarray,seriesArrayList,url) )
                }
                characterName?.text=characterList?.get(0)?.name

                val path=characterList?.get(0)?.thumbnailArry?.get(0)
                val extension=characterList?.get(0)?.thumbnailArry?.get(1)
                var imageLink=path+"."+extension
                var finalImagelink=imageLink.replace("http","https")
                Log.d("ifgggwvhwerjvkerov1",finalImagelink)
                Glide.with(context!!).load(finalImagelink).into(characterimagee!!)

                DescriptionTEXT?.text =characterList?.get(0)?.description
                linkText?.text=characterList?.get(0)?.url

                var myadapter=MyAdapter_SeriesItems(context!!,seriesArrayList!!)
                seriesRecyclerView?.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
              seriesRecyclerView?.adapter=myadapter

            }
        },object:Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
            }
        })
        requestQueue?.add(jsonObjectRequest)

    }
    fun initialize_Views(view:View)
    {
         characterimagee=view.findViewById(R.id.characterimagee)
         characterName=view.findViewById(R.id.characterName)
         DescriptionTEXT=view.findViewById(R.id.DescriptionTEXT)
         linkText=view.findViewById(R.id.linkText)
        seriesRecyclerView=view.findViewById(R.id.seriesRecyclerView)
    }
}