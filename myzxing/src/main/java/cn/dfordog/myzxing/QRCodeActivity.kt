package cn.dfordog.myzxing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import cn.dfordog.basecommon.BaseActivity
import cn.dfordog.myzxing.databinding.ActivityQrcodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*

class QRCodeActivity : BaseActivity<ActivityQrcodeBinding>(R.layout.activity_qrcode) {
    override fun initData() {
//        binding.qrcodeImg.setImageResource(R.drawable.ic_launcher_background)

        binding.qrcodeImg.setImageBitmap(createQRCodeBitmap("test",800,800,
                "UTF-8","H","1", Color.LTGRAY,Color.WHITE,this))
    }

    override fun listenClick() {

    }
}

private var bitmap1: Bitmap? = null

/**
 * 生成简单二维码
 *
 * @param content                字符串内容
 * @param width                  二维码宽度
 * @param height                 二维码高度
 * @param characterSet          编码方式（一般使用UTF-8）
 * @param errorCorrectionLevel 容错率 L：7% M：15% Q：25% H：35%
 * @param margin                 空白边距（二维码与边框的空白区域）
 * @param colorBlack            黑色色块
 * @param colorWhite            白色色块
 * @return BitMap
 */
fun createQRCodeBitmap(
    content: String?, width: Int, height: Int,
    characterSet: String?, errorCorrectionLevel: String?,
    margin: String?, colorBlack: Int, colorWhite: Int,
    context: Context
): Bitmap? {
    // 字符串内容判空
    if (TextUtils.isEmpty(content)) {
        return null
    }
    // 宽和高>=0
    if (width < 0 || height < 0) {
        return null
    }
    return try {
        /** 1.设置二维码相关配置  */
        val hints =
            Hashtable<EncodeHintType, String?>()
        // 字符转码格式设置
        if (!TextUtils.isEmpty(characterSet)) {
            hints[EncodeHintType.CHARACTER_SET] = characterSet
        }
        // 容错率设置
        if (!TextUtils.isEmpty(errorCorrectionLevel)) {
            hints[EncodeHintType.ERROR_CORRECTION] = errorCorrectionLevel
        }
        // 空白边距设置
        if (!TextUtils.isEmpty(margin)) {
            hints[EncodeHintType.MARGIN] = margin
        }
        /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象  */
        val bitMatrix =
            QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints)


        /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值  */
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                if (bitMatrix[x, y]) {
                    pixels[y * width + x] = R.mipmap.a //黑色色块像素设置，可以通过传入不同的颜色实现彩色二维码，例如Color.argb(1,55,206,141)等设置不同的颜色。
                } else {
                    pixels[y * width + x] = colorWhite // 白色色块像素设置
                }
            }
        }
        /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象  */
//        var options: BitmapFactory.Options? = BitmapFactory.Options()
//        bitmap1 = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background, options)
//        options!!.inPreferredConfig = Bitmap.Config.ARGB_8888
//        options.inBitmap = bitmap1

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val bitmap = Bitmap.createBitmap(bitmap1!!)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        bitmap
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
    //调用示例代码
//        imageView.setImageBitmap(QRCodeBitmap.newInstance().createQRCodeBitmap("test",80,80,
//                "UTF-8","L","1", Color.BLACK,Color.WHITE))
}

private fun convertToBitmap(context: Context){
    val bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.ic_launcher_background)
}