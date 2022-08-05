package space.dlsunity.arctour.utils.tools

import android.graphics.Bitmap
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidmads.library.qrgenearator.QRGSaver


object QRCodeUtil {

    const val SMALLER_DIMENSION = 10
    const val BIGGER_DIMENSION = 500
    private const val savePath = "/QRCode"

    fun genQRCode(stringForQrGeneration: String, dimension: Int): Bitmap{
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        val qrgEncoder = QRGEncoder(stringForQrGeneration, null, QRGContents.Type.TEXT, dimension);
        qrgEncoder.colorWhite = -0x322a1c // <--- R.color.lite_gray java to kotlin
        return qrgEncoder.bitmap
    }

    fun saveQRCode(bitmap: Bitmap){
        // Save with location, value, bitmap returned and type of Image(JPG/PNG).
        val qrgSaver = QRGSaver()
        qrgSaver.save(
            savePath,
            "QRCode",
            bitmap,
            QRGContents.ImageType.IMAGE_PNG // or IMAGE_JPEG
        )
    }
}