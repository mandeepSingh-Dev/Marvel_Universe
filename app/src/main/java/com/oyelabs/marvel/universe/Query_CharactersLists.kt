package com.oyelabs.marvel.universe

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Query_CharactersLists(val context: Context) {


    var requestQueue: RequestQueue? = null
    var publicKey = "466d97f4e5bf657ce8780e7b317c57d7"
    var charactersURL =
        "https://gateway.marvel.com/v1/public/characters?ts=1&apikey=" + publicKey + "&hash=4a866f2f4a437b10fcd9744b8286b4db"

    //inititalize requestQueue object when creating this class Object
    init {
        requestQueue = Volley.newRequestQueue(context)
    }

    fun getCharactersList(): ArrayList<list_items> {
        Log.d("dfdfd", "dfdf")
        /**creating arraylist to add all characters in arraylist */
        var characterList=ArrayList<list_items>()

        var jsonObjectRequest: JsonObjectRequest = JsonObjectRequest(Request.Method.GET,
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
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.d("dfhdj", error?.message + "d")
                }
            })
        requestQueue?.add(jsonObjectRequest)

        return characterList
    }
}

