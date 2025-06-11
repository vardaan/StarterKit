package com.mobiledev.starterkit.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyIntentServiceWorkerThread") {

    private val TAG = "MyIntentService"

    /**
     * This method is called on a worker thread with a request to process.
     * Each Intent received by startService() is put into a queue and
     * delivered to this method one at a time on the background thread.
     * When this method returns, IntentService stops the service, as appropriate.
     *
     * @param intent The Intent that started this service.
     */
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "IntentService created on Thread: ${Thread.currentThread().name} (ID: ${Thread.currentThread().id})")
    }

    override fun onHandleIntent(intent: Intent?) {
        // Ensure intent is not null before proceeding
        intent?.let {
            // Get the task ID from the intent
            val taskId = it.getIntExtra("task_id", -1)

            // Log the start of the task, current thread name, and thread ID
            val threadName = Thread.currentThread().name
            val threadId = Thread.currentThread().id
            Log.i(TAG, "--- Starting Task ID: $taskId on Thread: $threadName (ID: $threadId)")

            try {
                // Simulate a long-running operation
                // This will make the sequential processing very evident in the logs
                Thread.sleep(2000) // Sleep for 2 seconds
            } catch (e: InterruptedException) {
                // Restore interrupt status
                Thread.currentThread().interrupt()
                Log.e(TAG, "Task ID: $taskId interrupted!", e)
            }

            // Log the end of the task
            Log.i(TAG, "--- Finished Task ID: $taskId on Thread: $threadName (ID: $threadId)")
        } ?: run {
            Log.e(TAG, "onHandleIntent received a null intent.")
        }
    }
}
