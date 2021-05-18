package com.kotdroid.workmanagersample.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kotdroid.workmanagersample.R.drawable
import com.kotdroid.workmanagersample.utils.WorkerUtils

/**
 * @AUTHOR Pushkar Srivastava
 * @DATE   19/05/2021
 */
class BlurWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {


    companion object {
        var output: Bitmap? = null
        var outputUri: Uri? = null
    }

    override fun doWork(): Result {

        return try {
            val pic = BitmapFactory.decodeResource(applicationContext.resources, drawable.image)
            output = WorkerUtils.blurImage(pic, applicationContext)
            outputUri = WorkerUtils.writeBitmapToFile(applicationContext, output!!)
            Result.success()
        } catch (t: Throwable) {
            t.printStackTrace()
            Result.failure()
        }

    }
}