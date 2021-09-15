package cn.dfordog.myapplication

import android.annotation.SuppressLint
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("WrongConstant")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder = Notification.Builder(context,"Test")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Alarm Manager")
                .setContentText("Test")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            if (context != null) {
                val from = NotificationManagerCompat.from(context)
                from.notify(123,builder.build())
            }
        }

        Log.e("aaaaaa","1111111111")

    }
}