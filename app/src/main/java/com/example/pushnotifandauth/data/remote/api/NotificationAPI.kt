package com.example.pushnotifandauth.data.remote.api


import com.example.pushnotifandauth.Constants.Companion.CONTENT_TYPE
import com.example.pushnotifandauth.Constants.Companion.SERVER_KEY
import com.example.pushnotifandauth.data.local.localEntity.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}