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
import com.example.marveluniverse.R

class Character_Fragment : Fragment() {

 lateinit var receiver: BroadcastReceiver
 var charactersListsFragment:Characters_Fragment?=null
    lateinit var receiver2:BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersListsFragment= Characters_Fragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        }
}