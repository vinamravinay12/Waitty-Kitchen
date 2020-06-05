package com.waitty.kitchen.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.translabtechnologies.visitormanagementsystem.vmshost.database.SharedPreferenceManager
import com.waitty.kitchen.R
import com.waitty.kitchen.activity.HomeActivity
import com.waitty.kitchen.activity.SplashActivity
import com.waitty.kitchen.constant.WaittyConstants
import com.waitty.kitchen.retrofit.API
import org.json.JSONObject

class WKNotificationMessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    storeNewToken(task.result!!.token)
                })
    }


    private fun storeNewToken(token: String) {
      val sharedPreferenceManager = SharedPreferenceManager(context = this,name = WaittyConstants.LOGIN_SP)
        sharedPreferenceManager.storeStringPreference(WaittyConstants.USER_FCMTOKENID,token)
        val uniqueId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        sharedPreferenceManager.storeStringPreference(WaittyConstants.USER_DEVICEID,uniqueId)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        try {
            if (remoteMessage.data.isNotEmpty()) {
                val message = JSONObject(remoteMessage.data["body"] ?: "")
                val sharedPreferenceManager = SharedPreferenceManager(context = this, name = WaittyConstants.USER_SP)
                if (sharedPreferenceManager.getBooleanPreference(WaittyConstants.NOTIFICATIONS_SHOW, false) &&
                        sharedPreferenceManager.getBooleanPreference(WaittyConstants.KEY_IS_LOGGED_IN, false)) {

                    if (message.getInt(API.ORDER_STATUS_ID) == WaittyConstants.ORDER_PLACED) {
                        sharedPreferenceManager.storeBooleanPreference(WaittyConstants.NEW_RELOAD_BY_FCM, true)
                        val notificationCount =
                                sharedPreferenceManager.storeIntegerPreference(WaittyConstants.NOTIFICATION_COUNT_NEW, sharedPreferenceManager.getIntegerPreference(WaittyConstants.NOTIFICATION_COUNT_NEW, 0) + 1)
                        showNotificationServer(message)

                    } else {
                        showNotificationFCM(remoteMessage.notification?.body)
                    }
                }
            }

        } catch (exception : Exception) {

        }
    }

    private fun showNotificationServer(message: JSONObject) {

        try {
            val `when` = System.currentTimeMillis()
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(API.DATA, message.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(this, `when`.toInt(), intent, PendingIntent.FLAG_ONE_SHOT)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(message.getString(API.MESSAGE).trim { it <= ' ' })
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message.getString(API.MESSAGE).trim { it <= ' ' }))
                    .setContentIntent(pendingIntent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.drawable.notification_icon)
                notificationBuilder.color = resources.getColor(R.color.colorBlack)
            } else notificationBuilder.setSmallIcon(R.mipmap.launcher_icon)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationBuilder.setChannelId(WaittyConstants.PRIMARY_CHANNEL)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(`when`.toInt(), notificationBuilder.build())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    private fun showNotificationFCM(body: String?) {
        try {
            val intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                    .setContentIntent(pendingIntent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.drawable.notification_icon)
                notificationBuilder.color = resources.getColor(R.color.colorBlack)
            } else notificationBuilder.setSmallIcon(R.mipmap.launcher_icon)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationBuilder.setChannelId(WaittyConstants.PRIMARY_CHANNEL)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}