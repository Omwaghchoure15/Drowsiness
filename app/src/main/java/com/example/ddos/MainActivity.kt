package com.example.ddos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ddos.model.DrowsinessViewModel
import com.example.ddos.DrowsinessScreen
import com.example.ddos.ui.theme.DDosTheme

class MainActivity : ComponentActivity() {
    private val viewModel: DrowsinessViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                100
            )
        }

        setContent {
            installSplashScreen()
            DDosTheme {
                DrowsinessScreen(viewModel)
            }
        }
    }
}
