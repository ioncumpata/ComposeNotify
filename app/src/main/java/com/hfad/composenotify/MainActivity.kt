package com.hfad.composenotify

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.hfad.composenotify.ui.theme.ComposeNotifyTheme

class MainActivity : ComponentActivity() {
    private lateinit var pushBroadcast: BroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
            }
        }



        pushBroadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == MyFirebaseMessagingService.INTENT_FILTER) {
                    val intents = Intent(this@MainActivity, MainActivity::class.java)
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intents.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intents)
                    // Handle the intent action here
                    // For example, you can show a notification or perform some other action
                }
            }
        }

        // Register the BroadcastReceiver with the intent filter
        val intentFilter = IntentFilter()
        intentFilter.addAction(MyFirebaseMessagingService.INTENT_FILTER)
        registerReceiver(pushBroadcast, intentFilter)



        setContent {
            ComposeNotifyTheme {
                // A surface container using the 'background' color from the theme
                NotifyScreen()

            }

        }

    }
    companion object {
        const val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 123 // Use any unique integer code
    }
}




    @Preview(showBackground = true)
    @Composable
    fun NotifyScreen() {

        Box(
            modifier = Modifier.fillMaxSize()

        ) {
            Image(
                painter = painterResource(id = R.drawable.background_notify),
                contentDescription = "Background Phone",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Unknown",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(50.dp, top = 150.dp, end = 30.dp)
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {


                Button(
                    onClick = { },
                    modifier = Modifier.padding(50.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_call_end_24),
                        contentDescription = "Not answer"
                    )

                }

                Button(
                    onClick = { },
                    modifier = Modifier.padding(50.dp),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_call_24),
                        contentDescription = "Answer"
                    )

                }
            }

        }
    }


