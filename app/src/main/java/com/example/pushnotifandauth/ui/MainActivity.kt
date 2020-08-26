package com.example.pushnotifandauth.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pushnotifandauth.*
import com.example.pushnotifandauth.data.local.localEntity.NotificationData
import com.example.pushnotifandauth.data.local.localEntity.PushNotification
import com.example.pushnotifandauth.data.remote.RetrofitInstance
import com.example.pushnotifandauth.service.FirebaseService
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

const val TOPIC = "/topics/myTopic"

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            etToken.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        btnSend.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val recipientToken = etToken.text.toString()

            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()){

                val data = PushNotification(
                    NotificationData(title, message),
                    //TOPIC *if want to send to all who subscribe the topic
                    recipientToken
                )

                sendNotification(data)
            }
        }
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)

            if(response.isSuccessful){
                Log.d(TAG, "Response : ${Gson().toJson(response)}")
            }
            else{
                Log.e(TAG, response.errorBody().toString())
            }
        }
        catch (e: Exception){
            Log.e(TAG, e.toString())
        }
    }

    
}