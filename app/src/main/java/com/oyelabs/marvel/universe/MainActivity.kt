package com.oyelabs.marvel.universe

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.marveluniverse.R

import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var myCallback:Characters_Fragment.MyCallback2?=null
    private  lateinit var searchView:androidx.appcompat.widget.SearchView
    private lateinit var recyclerViewLinearLayout:LinearLayout
    private lateinit var recyclerView: RecyclerView
    private var localBroadcastManager:LocalBroadcastManager?=null
    private lateinit var receiver2:BroadcastReceiver
    private var chracterList:ArrayList<list_items>?=null
    private  var filterListt:ArrayList<String>?=null


    @SuppressLint("RestrictedApi", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_main)
     searchView = findViewById(R.id.searchview)
        recyclerView=findViewById(R.id.SearchrecylerView)
        recyclerViewLinearLayout=findViewById(R.id.lll)
        chracterList=ArrayList()
        filterListt=ArrayList()
        localBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)

     var navHostFragment = supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
     var navController = navHostFragment.navController
     var string = navController.currentDestination?.displayName!!
     Log.d("ejreuyruehfn", navController.currentDestination?.label.toString())

     Toast.makeText(this, string + "", Toast.LENGTH_SHORT).show()


        /**RECEIVING list from CharactersList_Fragment*/
        receiver2=object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if(intent?.action.equals("SENDING_LIST")){
                    Log.d("dfddddddddddd","fsdfds")
                    chracterList=intent?.getParcelableArrayListExtra<list_items>("charcaterList")
                    Log.d("fhduhfd","${chracterList?.size}ll")
                }
            }
        }
        localBroadcastManager?.registerReceiver(receiver2, IntentFilter("SENDING_LIST"))



     searchView.setOnQueryTextListener(object :
         androidx.appcompat.widget.SearchView.OnQueryTextListener {
         override fun onQueryTextChange(newText: String?): Boolean {
             var fragmentLabel = navController.currentDestination?.label.toString()
             if (fragmentLabel.equals("fragment_characterslist_")) {
                 Log.d("dfdfdfddf", "dfd" + newText)

                 localBroadcastManager?.sendBroadcast(
                     Intent("SEND_QUERY").putExtra(
                         "newText",
                         newText
                     )
                 )
             } else {
                 filterList(chracterList!!,newText!!)
                 Log.d("hfjcbcxbcuys",   newText)
                 localBroadcastManager?.sendBroadcast(Intent("SEND_QUERY_2").putExtra("newText2", newText))

           
             }
             return true
         }

         override fun onQueryTextSubmit(query: String?): Boolean {
             navController.navigate(R.id.fragment_character_)
             return true
         }
     })




    }

    private fun filterList(arrayList:ArrayList<list_items>, newText:String){

        Log.d("dsdsds",arrayList.size.toString())
        filterListt?.removeAll(filterListt!!)

         if(newText.equals(" "))
        {

            Log.d("siizee_SPACE_ELSE",filterListt?.size.toString())
            recyclerViewLinearLayout.visibility=View.GONE
            recyclerView.visibility=View.GONE
        }
        else if(newText.isEmpty()){

             Log.d("siizee_EMPTY_ELSE",filterListt?.size.toString())
             recyclerViewLinearLayout.visibility=View.GONE
             recyclerView.visibility=View.GONE

        }
        else {
             recyclerViewLinearLayout.visibility=View.VISIBLE
             recyclerView.visibility = View.VISIBLE
             arrayList?.forEach {
                 if (it.name.lowercase().contains(newText.lowercase())) {
                     filterListt?.add(it.name)
                 }
             }
             Log.d("siizee ELSE_CON",filterListt?.size.toString())
             var adapter=MyAdapter_for_SearchList(applicationContext,filterListt!!)
             recyclerView.layoutManager=LinearLayoutManager(this)
             recyclerView.adapter=adapter
         }

    }
}
