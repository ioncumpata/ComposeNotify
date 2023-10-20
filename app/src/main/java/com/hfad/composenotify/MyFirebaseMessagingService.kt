package com.hfad.composenotify

import android.content.BroadcastReceiver
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
       super.onMessageReceived(message)

        val intent = Intent(INTENT_FILTER)
        message.data.forEach(){entity->
            intent.putExtra(entity.key, entity.value)

        }
        sendBroadcast(intent)

    }

companion object{

    const val INTENT_FILTER = "OPEN_NEW_ACTIVITY"
}

}

