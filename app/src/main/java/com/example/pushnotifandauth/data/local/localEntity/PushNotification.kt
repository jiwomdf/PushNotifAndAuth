package com.example.pushnotifandauth.data.local.localEntity

import com.example.pushnotifandauth.data.local.localEntity.NotificationData

data class PushNotification (
    val data: NotificationData,
    val to: String
)