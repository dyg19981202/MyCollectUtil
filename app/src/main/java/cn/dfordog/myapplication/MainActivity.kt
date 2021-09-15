package cn.dfordog.myapplication

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import java.util.*
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var ll:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll = findViewById(R.id.mainLL)

//        val spinner = findViewById<Spinner>(R.id.planets_spinner)
//        ArrayAdapter.createFromResource(
//            this,
//            R.array.planets_array,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter
//        }


        ll.setOnClickListener {
           startActivity(Intent(this,MainActivity2::class.java))
        }



    }


    private fun showPop(width:Int){
        val view = LayoutInflater.from(this).inflate(R.layout.pop_window,null)
        val popupWindow = PopupWindow(view,width,ViewGroup.LayoutParams.WRAP_CONTENT,true)
        popupWindow.isTouchable = true
        popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_launcher_background))
        popupWindow.showAsDropDown(ll)
        popupWindow.setOnDismissListener {
            Toast.makeText(this, "dismissListener", Toast.LENGTH_SHORT).show()
        }

        val list = mutableListOf<String>()
        list.add("Mercury")
        list.add("Venus")
        list.add("Earth")
        list.add("Mars")
        list.add("Jupiter")
        list.add("Saturn")

        val adapter = PopAdapter(list)
        val rcv = view.findViewById<RecyclerView>(R.id.popRcv)
        rcv.adapter = adapter
        rcv.layoutManager = LinearLayoutManager(this)
    }


    private fun createAlarm(){

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        Log.e("nowTime: ", "$hour:$minute")

        val btn = findViewById<Button>(R.id.btn)
        val coor = findViewById<CoordinatorLayout>(R.id.ShowSnackBar)
        btn.setOnClickListener {

            alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmIntent = Intent(this, MainActivity2::class.java).let { intent ->
                PendingIntent.getActivity(this, 0, intent, 0)
            }

            // Set the alarm to start at 8:30 a.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            // setRepeating() lets you specify a precise custom interval--in this case,
            // 20 minutes.
            alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                1000 * 60 * 1,
                alarmIntent
            )

        }

    }

}