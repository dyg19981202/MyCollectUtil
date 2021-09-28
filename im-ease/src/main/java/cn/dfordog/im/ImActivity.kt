package cn.dfordog.im

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ImActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_im)

        val reg = findViewById<Button>(R.id.reg)
        val log = findViewById<Button>(R.id.login)
        val toMQTT = findViewById<Button>(R.id.toMQTT)


        reg.setOnClickListener {
            try {
                GlobalScope.launch(Dispatchers.IO){
                    EMClient.getInstance().createAccount("dyg111111111111","123456")
                }
            }catch (e: Exception){
                Log.e("注册失败错误","${e.message}")
            }
        }

        log.setOnClickListener {
            EMClient.getInstance().login("dyg","123456",object : EMCallBack{
                override fun onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Log.d("main", "登录聊天服务器成功！");
                }

                override fun onError(code: Int, error: String?) {
                    Log.d("main", "登录聊天服务器失败！");
                }

                override fun onProgress(progress: Int, status: String?) {
                    Log.d("main", "登录聊天服务器失败！");
                }
            })
        }

        toMQTT.setOnClickListener {
            startActivity(Intent(this,MyMQTTActivity::class.java))
        }


    }
}