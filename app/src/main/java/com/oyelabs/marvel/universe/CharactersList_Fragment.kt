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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.marveluniverse.R
import org.json.JSONObject

class Characters_Fragment : Fragment() {
    private lateinit var searchView:androidx.appcompat.widget.SearchView
    private lateinit var recyclerView: RecyclerView
    private  var onCallback:MyCallback2?=null
    private var arraylist:ArrayList<list_items>?=null
    private var filterlist:ArrayList<list_items>?=null
  //  lateinit var textview:TextView

   lateinit var receiver:BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arraylist= ArrayList()
        filterlist= ArrayList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characterslist_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView=activity?.findViewById(R.id.searchview)!!
        recyclerView=view.findViewById(R.id.charactersRecyclerView)
       // textview=view.findViewById(R.id.textvieww)



        onCallback=object :MyCallback2{
            override fun onSucces(arrayit: ArrayList<list_items>) {
                   arraylist=arrayit
                Log.d("skshhsjdhsjdhsj","${arrayit.size}g")
            }
        }
        var requestQueue: RequestQueue? = null
        var publicKey = "466d97f4e5bf657ce8780e7b317c57d7"
        var charactersURL = "https://gateway.marvel.com/v1/public/characters?ts=1&apikey=" + publicKey + "&hash=4a866f2f4a437b10fcd9744b8286b4db"

        //inititalize requestQueue object when creating this class Object
        requestQueue = Volley.newRequestQueue(context)

        var characterList=ArrayList<list_items>()

        var jsonObjectRequest: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            charactersURL,
            null,
            object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject?) {
                    Log.d("dfhujjhjhj", "dfdf")

                    val resultsArray = response?.getJSONObject("data")?.getJSONArray("results")
                    for (i in 0..resultsArray?.length()?.minus(1)!!) {
                        var resultobject = resultsArray.getJSONObject(i)
                        var name = resultobject.getString("name")
                        var id = resultobject.getInt("id")
                        var description = resultobject.getString("description")
                        var path = resultobject.getJSONObject("thumbnail").getString("path")
                        var extension = resultobject.getJSONObject("thumbnail").getString("extension")
                        /**creating thumbnail array of path and extension*/
                        var thumnailarray= arrayOf(path,extension)
                        characterList.add( list_items(id,name,description,thumnailarray) )
                    }

                    val myadapter=MyAdapter_forList(requireContext(),characterList)
                    recyclerView.layoutManager=GridLayoutManager(context,2)
                    recyclerView.adapter=myadapter

                    onCallback?.onSucces(characterList)
                    filterList(characterList)
                    /**sending list to charcater_Fragment*/
                    LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent("SENDING_LIST").putParcelableArrayListExtra("charcaterList",characterList!!))
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.d("dfhdj", error?.message + "d")
                }
            })
        requestQueue?.add(jsonObjectRequest)




    }
    private fun filterList(arraylist:ArrayList<list_items>)
    {
       /**Receieving Search query on every textChanging in serachbar MainActivity */
        receiver=object:BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                var newText = intent?.getStringExtra("newText")
                if(newText.equals(" "))
                {
                    Log.d("siizee_SPACE_ELSE",filterlist?.size.toString())
                   // recyclerView.visibility=View.GONE
                }
                else if(newText?.isEmpty()!!){
                    Log.d("siizee_EMPTY_ELSE",filterlist?.size.toString())
                   // recyclerView.visibility=View.GONE
                }
                else{
                    Log.d("fdfdfdfxcsew", newText!!)
                    filterlist?.removeAll(filterlist!!)
                    arraylist?.forEach {
                        if (it.name.lowercase().contains(newText?.lowercase()!!) /*||it.name.lowercase().equals(newText?.lowercase()!!)*/) {
                            filterlist?.add(it)
                        }
                    }
                    //SETTING THAT if filterlist is not null then excute beolow code
                    val myadapter = MyAdapter_forList(requireContext(), filterlist!!)
                    recyclerView.layoutManager = GridLayoutManager(context, 2)
                    recyclerView.adapter = myadapter
                }//end of else block
            }
             }
            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver!!, IntentFilter("SEND_QUERY"))

        }
    fun setOnListener(callback:MyCallback2)
    {
        onCallback=callback
    }
    interface MyCallback2{
        fun onSucces(arrayit:ArrayList<list_items>)
    }
}