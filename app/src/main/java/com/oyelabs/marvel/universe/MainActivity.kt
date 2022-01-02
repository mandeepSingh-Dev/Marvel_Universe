package com.oyelabs.marvel.universe

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.marveluniverse.R
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var navHostFragment=supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        var navController=navHostFragment.navController
        var string=navController.currentDestination?.displayName!!
        Toast.makeText(this,string+"",Toast.LENGTH_SHORT).show()


        /*var requestQueue: RequestQueue?=null
        requestQueue= Volley.newRequestQueue(this)
        var publicKey="466d97f4e5bf657ce8780e7b317c57d7"
        var charactersURL="https://gateway.marvel.com/v1/public/characters?ts=1&apikey="+publicKey+"&hash=4a866f2f4a437b10fcd9744b8286b4db"

            Log.d("dfdfd","dfdf")
            var jsonObjectRequest: JsonObjectRequest = JsonObjectRequest(Request.Method.GET, charactersURL, null, object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject?) {
                    Log.d("dfhujjhjhj","dfdf"+response.toString())

                    val resultsArray = response?.getJSONObject("data")?.getJSONArray("results")
                    for (i in 0..resultsArray?.length()?.minus(1)!!) {
                        var resultobject = resultsArray.getJSONObject(i)
                        var name = resultobject.getString("name")
                        Log.d("voolyeey", name + "dfdj")
                    }
                }
            },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Log.d("dfhdj",error?.message+"d")
                    }
                })
            requestQueue.add(jsonObjectRequest)*/
     }
}
