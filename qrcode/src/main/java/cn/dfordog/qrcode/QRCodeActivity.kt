package cn.dfordog.qrcode

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.github.sumimakito.awesomeqr.option.background.BlendBackground
import com.github.sumimakito.awesomeqr.option.background.StillBackground
import com.github.sumimakito.awesomeqr.option.color.Color
import com.github.sumimakito.awesomeqr.option.logo.Logo

class QRCodeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        val qrcode = findViewById<ImageView>(R.id.qrcode)

// A still background (a still image as the background)
        val background = StillBackground()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.asdds)
        background.bitmap = bitmap // assign a bitmap as the background
        background.clippingRect = Rect(0, 0, 200, 200) // crop the background before
        background.alpha = 0.7f // alpha of the background to be drawn


        val color =  Color ()
        color.light =  android.graphics.Color.BLACK //对于空格
        color.dark =  android.graphics.Color.BLACK //对于非空格
        color.background =  android.graphics.Color.WHITE //用于背景（将被背景图像覆盖，如果设置）
        color.auto =  true  //设置为 true 以自动从背景图像中挑选颜色（仅当背景图像存在时才有效）

        val logo = Logo()
        logo.bitmap = bitmap
        logo.borderRadius = 10 // radius for logo's corners
        logo.borderWidth = 10 // width of the border to be added around the logo
        logo.scale = 0.2f // scale for the logo in the QR code
        logo.clippingRect = RectF(0F, 0F, 10F, 10F) // crop the logo image before applying it to the QR code

        val renderOption = RenderOption()
        renderOption.content = "Special, thus awesome." // content to encode
        renderOption.size = 800 // size of the final QR code image
        renderOption.borderWidth = 20 // width of the empty space around the QR code
//        renderOption.ecl = ErrorCorrectionLevel.M // (optional) specify an error correction level
        renderOption.patternScale = 0.35f // (optional) specify a scale for patterns
        renderOption.roundedPatterns = true // (optional) if true, blocks will be drawn as dots instead
        renderOption.clearBorder = true // if set to true, the background will NOT be drawn on the border area
        renderOption.color = color// set a color palette for the QR code
        renderOption.background = background // set a background, keep reading to find more about it
        renderOption.logo = logo // set a logo, keep reading to find more about it
        renderOption

        val result = AwesomeQrRenderer.renderAsync(renderOption, { result ->
            if (result.bitmap != null) {
                Log.e("QRCodeActivity: ", result.bitmap.toString())
                qrcode.setImageBitmap(result.bitmap)
            } else if (result.type == RenderResult.OutputType.GIF) {
                Log.e("QRCodeActivity: ", result.bitmap.toString())
                qrcode.setImageBitmap(result.bitmap)
            } else {
                Log.e("QRCodeActivity: ", result.bitmap.toString())
                qrcode.setImageBitmap(result.bitmap)
            }
        }, {
                exception -> exception.printStackTrace()
            Log.e("QRCodeActivity: ",exception.message.toString())
        })



    }



}