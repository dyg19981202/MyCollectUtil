package cn.dfordog.im

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import org.json.JSONException
import org.json.JSONObject
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.io.IOException


class MyMQTTActivity : AppCompatActivity() {

    private val appId: String = "vsuqg0"
    private val mqttUri = "vsuqg0.cn1.mqtt.chat"
    private val mqttPort = "1883"
    private val orgName = "1105210925200562"
    private val appName = "collect"
    private val tokenUrl = "https://a1.easemob.com/%s/%s/token"

    private lateinit var mMqttClient: MqttAndroidClient
    private lateinit var client: MqttClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_mqttactivity)
        client = MqttClient.getInstance(this)

        findViewById<Button>(R.id.sendMessage).setOnClickListener {
            client.sendMsg("topic", "message", 0)
        }

        getMQTTToken()
        mqttListener()
    }


    private fun mqttListener(){
        mMqttClient.setCallback(object : MqttCallback{
            override fun connectionLost(cause: Throwable?) {
                Log.d("MainActivity", "connectionLost: 连接断开")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("MainActivity", "收到消息:"+message.toString())

            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
    }


    private fun getMQTTToken(){
        val reqBody = JSONObject()
        reqBody.put("grant_type", "password")
        reqBody.put("username", "dyg")
        reqBody.put("password", "123456")


        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val mediaType: MediaType? = "application/json".toMediaTypeOrNull()
        val requestBody = RequestBody.create(mediaType, reqBody.toString())
        val request: Request = Request.Builder()
            .url(String.format(tokenUrl, orgName, appName))
            .post(requestBody)
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "okhttp_onFailure:" + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body!!.string()
                if (response.code == 200) {
                    try {
                        val result = JSONObject(responseBody)
                        val token = result.getString("access_token")
                        client.connectMQTT(
                            appId,
                            mqttUri,
                            mqttPort,
                            "dyg",
                            token,
                            object : IMqttActionListener {
                                override fun onSuccess(asyncActionToken: IMqttToken) {
                                    Log.e("MainActivity", "connect success")
                                    client.subscribeMQTT("topic",0,object : IMqttActionListener{
                                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                                            Log.e("MainActivity", "subscribe success")
                                            sendToast("subscribe success")
                                        }

                                        override fun onFailure(
                                            asyncActionToken: IMqttToken?,
                                            exception: Throwable?
                                        ) {
                                            Log.e("MainActivity", "subscribe failure")
                                            sendToast("subscribe failure")
                                        }
                                    })
                                }
                                override fun onFailure(
                                    asyncActionToken: IMqttToken,
                                    exception: Throwable
                                ) {
                                    Log.e("MainActivity", "connect failure")
                                   
                                }
                            })
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
    
    
    private fun sendToast(content: String) {
        runOnUiThread {
            Toast.makeText(
                this,
                content,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * 取消订阅
     */
    private fun cancelSubscribe(){
        try {
            mMqttClient.unsubscribe("topic")
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}