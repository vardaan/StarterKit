package com.mobiledev.starterkit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mobiledev.starterkit.service.MyIntentService
import com.mobiledev.starterkit.service.SimpleForegroundService
import com.mobiledev.starterkit.ui.theme.StarterKitTheme
import com.mobiledev.starterkit.workerManager.HelloWorker

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // Handle permission denied
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        enableEdgeToEdge()
        setContent {
            StarterKitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ForegroundServiceApp(
                    )
                }
            }
        }
    }
}
@Composable
fun ServiceScreen(activityTag: String) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                // Launch the IntentService multiple times
                for (i in 1..5) { // Let's use 5 instances for a clearer demo
                    val serviceIntent = Intent(context, MyIntentService::class.java).apply {
                        putExtra("task_id", i) // Pass a unique ID for each "task"
                    }
                    Log.d(activityTag, "Launching MyIntentService with Task ID: $i")
                    context.startService(serviceIntent)
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)), // Deep Purple
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Start IntentService Multiple Times",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewServiceScreen() {
    StarterKitTheme {
        ServiceScreen("PreviewActivity")
    }
}



@Composable
fun ForegroundServiceApp() {
    val context = LocalContext.current
    var isServiceRunning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Foreground Service Demo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                val intent = Intent(context, SimpleForegroundService::class.java).apply {
                    action = SimpleForegroundService.ACTION_START
                }

                for( i in 1..5) { // Start the service multiple times
                    val request = OneTimeWorkRequestBuilder<HelloWorker>().build()
                    WorkManager.getInstance(context).enqueue(request)
                }

                // Use startForegroundService for API 26+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
                isServiceRunning = true
            },
            enabled = !isServiceRunning,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Foreground Service")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, SimpleForegroundService::class.java).apply {
                    action = SimpleForegroundService.ACTION_STOP
                }
                context.startService(intent)
                isServiceRunning = false
            },
            enabled = isServiceRunning,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Stop Foreground Service")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "What to observe:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text("• Persistent notification appears")
                Text("• Counter updates every 2 seconds")
                Text("• Service continues when app is closed")
                Text("• Check Logcat for service logs")
                Text("• Tap 'Stop' in notification to stop")
            }
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
