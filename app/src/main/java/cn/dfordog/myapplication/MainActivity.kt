package cn.dfordog.myapplication

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import java.util.*
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.dfordog.baseretrofit.RetrofitActivity
import cn.dfordog.basezego.ZegoActivity
import cn.dfordog.im.ImActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var ll:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll = findViewById(R.id.mainLL)
        val progress = findViewById<Button>(R.id.progressBtn)

        progress.setOnClickListener {
            val dialog = ProgressDialog(this).apply {
                setMessage("正在上传,请稍等")
                setCanceledOnTouchOutside(false)
                show()
            }
            Thread.sleep(5000)
            dialog.dismiss()
            val coor = findViewById<CoordinatorLayout>(R.id.ShowSnackBar)
            Snackbar.make(coor,"testTestTestTest",Snackbar.LENGTH_LONG).show()

        }

        val toSelect = findViewById<Button>(R.id.toSelectPhoto)
        toSelect.setOnClickListener {
            startActivity(Intent(this,SelectPhotoActivity::class.java))
        }

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
           startActivity(Intent(this,UseCameraActivity::class.java))
        }

        findViewById<Button>(R.id.toEasy).setOnClickListener {
            startActivity(Intent(this,ImActivity::class.java))
        }

        findViewById<Button>(R.id.toZego).setOnClickListener {
            startActivity(Intent(this,ZegoActivity::class.java))
        }

        findViewById<Button>(R.id.toRetrofit).setOnClickListener {
            startActivity(Intent(this,RetrofitActivity::class.java))
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

        val coor = findViewById<CoordinatorLayout>(R.id.ShowSnackBar)
//        btn.setOnClickListener {
//
//            alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmIntent = Intent(this, UseCameraActivity::class.java).let { intent ->
//                PendingIntent.getActivity(this, 0, intent, 0)
//            }
//
//            // Set the alarm to start at 8:30 a.m.
//            val calendar: Calendar = Calendar.getInstance().apply {
//                timeInMillis = System.currentTimeMillis()
//                set(Calendar.HOUR_OF_DAY, hour)
//                set(Calendar.MINUTE, minute)
//            }
//
//            // setRepeating() lets you specify a precise custom interval--in this case,
//            // 20 minutes.
//            alarmMgr?.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                1000 * 60 * 1,
//                alarmIntent
//            )
//
//        }

    }

}