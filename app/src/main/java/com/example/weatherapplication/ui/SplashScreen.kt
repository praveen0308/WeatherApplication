package com.example.weatherapplication.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentMainBinding
import com.example.weatherapplication.databinding.FragmentSplashScreenBinding


class SplashScreen : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        binding.apply {
            lavWeather.setAnimation(R.raw.weather_partly_shower)
            lavWeather.playAnimation()
//            lavWeather.loop(true)
        }


        Handler().postDelayed({
            navController.navigate(R.id.action_splashScreen_to_mainFragment)
        }, 2000)

    }
}