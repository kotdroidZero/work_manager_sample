package com.kotdroid.workmanagersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.kotdroid.workmanagersample.workers.BlurWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val oneTimeRequest = OneTimeWorkRequest.Builder(BlurWorker::class.java).build()



        btnBlurImage.setOnClickListener {
            WorkManager.getInstance(this).enqueue(oneTimeRequest)
            ivImage.setImageURI(BlurWorker.outputUri)
        }
    }
}