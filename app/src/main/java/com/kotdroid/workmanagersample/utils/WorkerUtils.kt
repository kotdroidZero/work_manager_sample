package com.kotdroid.workmanagersample.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * @AUTHOR Pushkar Srivastava
 * @DATE   19/05/2021
 */
object WorkerUtils {


    const val BITMAP_SCALE = 0.4f
    const val BITMAP_RADIUS = 20.5f

    fun blurImage(image: Bitmap, context: Context): Bitmap {
        val width = (image.width * BITMAP_SCALE).roundToInt()
        val height = (image.height * BITMAP_SCALE).roundToInt()

        val input = Bitmap.createScaledBitmap(image, width, height, false)
        val output = Bitmap.createBitmap(input)
        val rs = RenderScript.create(context)
        val si = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val allocation = Allocation.createFromBitmap(rs, input)
        val allocation1 = Allocation.createFromBitmap(rs, output)
        si.setRadius(BITMAP_RADIUS)
        si.setInput(allocation)
        si.forEach(allocation1)
        allocation1.copyTo(output)
        return output;


    }


    fun writeBitmapToFile(context: Context, bitmap: Bitmap): Uri {

        val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date());
        val imageName = "blurry_image$timeStamp.jpg"

        val cw = ContextWrapper(context)

        var file = cw.getDir("images", Context.MODE_PRIVATE)

        file = File(file, "snap_$imageName.jpg")

        try {

            val outputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

            outputStream?.flush()


        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.fromFile(file)
    }
}