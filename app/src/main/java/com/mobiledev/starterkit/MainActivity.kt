package com.mobiledev.starterkit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobiledev.starterkit.ui.theme.StarterKitTheme
import timber.log.Timber
class MyHandlerThread() : Thread() {
    private lateinit var myHandler: Handler
    private val uiHandler: Handler = Handler(Looper.getMainLooper()) // To send back to UI

    override fun run() {
        Timber.i("MyCustomLooperThread started")
        Looper.prepare()
        // Initialize your thread here

        myHandler = object : Handler(Looper.myLooper()!!) { // Looper.myLooper() gets the Looper for the current thread
            override fun handleMessage(msg: Message) {
                // This code runs on MyCustomLooperThread
                val messageText = msg.data.getString("data")
                val result = "Processed: $messageText on custom looper thread."
                Timber.tag("CustomLooperThread").d(result)

                // Send result back to UI thread
                val uiMsg = Message.obtain()
                val bundle = Bundle()
                bundle.putString("status", result)
                uiMsg.data = bundle
                uiHandler.sendMessage(uiMsg)
            }
        }
         Looper.loop()
    }
    fun getHandler(): Handler {
        return myHandler
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        init()
        setContent {
            StarterKitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    fun init(){
        val myHandlerThread = MyHandlerThread()
        myHandlerThread.start() // Start the thread
        Handler(Looper.getMainLooper()).post {
            val handler = myHandlerThread.getHandler()
            val message = Message.obtain()
            val bundle = Bundle()
            bundle.putString("data", "Hello from MainActivity!")
            message.data = bundle
            handler.sendMessage(message)
        }

    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StarterKitTheme {
        Greeting("Android")
    }
}
