package de.uniks.codecamp.group_a.weather

import android.Manifest
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.GestureDetectorCompat
import dagger.hilt.android.AndroidEntryPoint
import de.uniks.codecamp.group_a.weather.sensor.EnvironmentSensor
import de.uniks.codecamp.group_a.weather.sensor.SensorViewModel
import de.uniks.codecamp.group_a.weather.ui.screens.EnvironmentSensorScreen
import de.uniks.codecamp.group_a.weather.ui.theme.WeatherTheme
import de.uniks.codecamp.group_a.weather.viewmodel.WeatherViewModel
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private lateinit var swipeDetector: GestureDetectorCompat
    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>
    private val viewModel: WeatherViewModel by viewModels()
    private val sensorViewModel: SensorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //swipeDetector = GestureDetectorCompat(this, SimpleGestureListener())

        // request permission to access the location, load the weather information
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            viewModel.loadCurrentWeather()
        }
        locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                    //EnvironmentSensorScreen(viewModel = sensorViewModel)
                    WeatherScreen(sensorViewModel = sensorViewModel)
            }
        }
    }

    /*override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if (swipeDetector.onTouchEvent(event)) {
                return true
            }
        }
        return super.onTouchEvent(event)
    }*/

    /*inner class SimpleGestureListener: GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 10
        private val SWIPE_VELOCITY_THRESHOLD = 10

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = e2.x.minus(e1.x)
            val diffY = e2.y.minus(e1.y)

            return if (abs(diffX) > abs(diffY)) {
                // horizontal swipe
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        //right swipe
                        this@MainActivity.rightSwipe()
                    } else {
                        //left swipe
                        this@MainActivity.leftSwipe()
                    }
                    true
                } else {
                    super.onFling(e1, e2, velocityX, velocityY)
                }
            } else {
                // vertical swipe
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        //down swipe
                        this@MainActivity.downSwipe()
                    } else {
                        //up swipe
                        this@MainActivity.upSwipe()
                    }
                    true
                } else {
                    super.onFling(e1, e2, velocityX, velocityY)
                }
            }


            //return super.onFling(e1, e2, velocityX, velocityY)
        }
    }*/

    /*private fun upSwipe() {
        //sensorViewModel.startListening()
        Toast.makeText(sensorViewModel.ambientTemperatureSensor.context, "Up Swipe", Toast.LENGTH_LONG).show()
    }

    private fun downSwipe() {
        //sensorViewModel.startListening()
        Toast.makeText(sensorViewModel.ambientTemperatureSensor.context, "Down Swipe", Toast.LENGTH_LONG).show()
    }

    private fun leftSwipe() {
        //sensorViewModel.startListening()
        Toast.makeText(sensorViewModel.ambientTemperatureSensor.context, "Left Swipe", Toast.LENGTH_LONG).show()
    }

    private fun rightSwipe() {
        //sensorViewModel.startListening()
        Toast.makeText(sensorViewModel.ambientTemperatureSensor.context, "Right Swipe", Toast.LENGTH_LONG).show()
    }*/
}