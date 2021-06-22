package com.example.weatherapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.models.ApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var response: ApiResponse

    private var pressedTime: Long = 0
//    private val viewModel by viewModels<WeatherViewModel>()

    private var job: Job = Job()

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        launch {
//            response = viewModel.getCurrentWeather("Mumbai")
//
//        }

    }

//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + job

//    override fun onBackPressed() {
//        if (pressedTime + 2000 > System.currentTimeMillis()) {
//            finish();
//        } else {
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        pressedTime = System.currentTimeMillis();
//    }
}