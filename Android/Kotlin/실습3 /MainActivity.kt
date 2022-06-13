package com.example.notificationexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_notification1-> {createNotificationChannel("default channel")
                showNotificationRegularActivity("default channel")}
            R.id.show_notification2->{createNotificationChannel("notice channel")
                showNotification("notice channel")}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createNotificationChannel(channelID:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android 8.0
            val channel = NotificationChannel(
                channelID, channelID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Notification Example"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private var myNotificationID = 1
        get() = field++

    private fun showNotificationRegularActivity(channelID:String) {
        val intent = Intent(this, Noti2Activity::class.java)
        val pendingIntent = with (TaskStackBuilder.create(this)) {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.android_hsu)
        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(bitmap)
            .setContentTitle("notification1")
            .setContentText("this is notification 1")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // auto remove this notification when user touches it
        NotificationManagerCompat.from(this)
            .notify(myNotificationID, builder.build())
    }

    private fun showNotification(channelID:String) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.android_hsu)
        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(bitmap)
            .setContentTitle("notification2")
            .setContentText("this is Notification 2")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this)
            .notify(myNotificationID, builder.build())
    }
}