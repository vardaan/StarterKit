package com.mobiledev.starterkit

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
/**
 * MySimpleHandlerThread: A custom HandlerThread that focuses on Looper and Handler setup.
 * It performs a simple background task and sends status updates back to the UI.
 */
class MySimpleHandlerThread(name: String, private val uiHandler: Handler) : HandlerThread(name) {
    private val TAG = "MySimpleHandlerThread"

    private var workerHandler: Handler? = null // This Handler is tied to THIS thread's Looper
    private val MESSAGE_WORKER_READY = 1

    /**
     * This method is called after the thread has started and its Looper has been prepared.
     * This is the crucial point where you get access to the thread's Looper
     * and can create Handlers that will handle messages on THIS background thread.
     */
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        Log.d(TAG, "Worker Thread: Looper prepared and ready.")

        // 1. Create a Handler for this worker thread
        //    It automatically binds to `getLooper()` (which is the Looper of this HandlerThread).
        workerHandler = object : Handler(looper) {
            override fun handleMessage(msg: Message) {
                // This method is called when messages are sent to 'workerHandler'
                // This code runs on the background HandlerThread
                when (msg.what) {
                    // We're not explicitly sending messages WITH .what here,
                    // but a more complex app would use it to differentiate tasks.
                    // For now, we'll just log any incoming message for demonstration.
                    0 -> { // Default 'what' for general Runnables
                        Log.d(TAG, "Worker Thread: Received a general message/Runnable.")
                        sendMessageToUI("Worker Thread: Doing background work (2s)...")
                        try {
                            Thread.sleep(2000) // Simulate work
                        } catch (e: InterruptedException) {
                            Thread.currentThread().interrupt()
                            sendMessageToUI("Worker Thread: Interrupted during work.")
                            Log.e(TAG, "Worker Thread: Interrupted!", e)
                            return
                        }
                        sendMessageToUI("Worker Thread: Background work finished!")
                    }
                    // Add more 'when' cases for different types of messages/tasks
                }
            }
        }

        // 2. Send a status message to the UI thread
        sendMessageToUI("Worker Thread: Started and ready for tasks.")

        // 3. Post an initial task (Runnable) to this worker thread's handler
        //    This demonstrates how to put a task onto the Looper's message queue.
        workerHandler?.post(object : Runnable {
            override fun run() {
                // This Runnable will be executed on this HandlerThread's Looper
                Log.d(TAG, "Worker Thread: Initial task (Runnable) posted and executing.")
                try {
                    Thread.sleep(1000) // Simulate some quick setup work
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
                sendMessageToUI("Worker Thread: Initial task complete. Awaiting more instructions.")
            }
        })
    }

    /**
     * Provides access to this HandlerThread's Handler.
     * Other threads (like the UI thread) will use this to send messages/tasks.
     * @return The Handler associated with this worker thread's Looper, or null if not yet prepared.
     */
    fun getWorkerHandler(): Handler? {
        return workerHandler
    }

    /**
     * Helper to send a status message back to the main UI thread.
     * @param status The message string to send.
     */
    private fun sendMessageToUI(status: String) {
        val message = Message.obtain(uiHandler, 2)
        val bundle = Bundle()
        bundle.putString("status", status)
        message.data = bundle
        uiHandler.sendMessage(message)
    }

    // You can also override onLooperFinished() if needed for specific cleanup
    // @Override
    // protected void onLooperFinished() {
    //     super.onLooperFinished();
    //     Log.d(TAG, "Worker Thread: Looper finished.");
    // }
}

