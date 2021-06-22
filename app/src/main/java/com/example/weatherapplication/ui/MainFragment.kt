package com.example.weatherapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.weatherapplication.R
import com.example.weatherapplication.adapters.CurrentDayTempDetailAdapter
import com.example.weatherapplication.adapters.DaysAdapter
import com.example.weatherapplication.adapters.HourlyTempAdapter
import com.example.weatherapplication.databinding.FragmentMainBinding
import com.example.weatherapplication.models.ApiResponse
import com.example.weatherapplication.models.Forecastday
import com.example.weatherapplication.models.ModelKeyValue
import com.example.weatherapplication.utils.CustomDateFormatter
import com.example.weatherapplication.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainFragment : Fragment(), CoroutineScope, DaysAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var daysAdapter: DaysAdapter
    private lateinit var hourlyTempAdapter: HourlyTempAdapter
    private lateinit var currentDayTempDetailAdapter: CurrentDayTempDetailAdapter
    private lateinit var daysList: List<Forecastday>
    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var response: ApiResponse
    private var job: Job = Job()

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            binding.loader.visibility = View.VISIBLE
            response = viewModel.getForecastWeather("Mumbai")

            onResponseFetched(response)
        }
    }

    private fun bindViews(forecastday: Forecastday) {
        binding.apply {
            Glide.with(binding.root)
                    .load("http:" + forecastday.day.condition.icon)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(fragmentMainImgWeather)
            val customDateFormatter = CustomDateFormatter()
            fragmentMainTvDayName.text = customDateFormatter.getDateCategory(forecastday.date)
            fragmentMainTvDate.text = customDateFormatter.formatDateEMD(forecastday.date)
            fragmentMainTvTemperature.text = forecastday.day.avgtemp_c.toString()
            fragmentMainTvSunriseTiming.text = "Sunrise " + forecastday.astro.sunrise
            fragmentMainTvSunsetTiming.text = "Sunset " + forecastday.astro.sunset

        }
    }


    private fun onResponseFetched(apiResponse: ApiResponse) {
        binding.loader.visibility = View.GONE
        binding.frameLayout.visibility = View.VISIBLE
        daysList = apiResponse.forecast.forecastday
        daysAdapter = DaysAdapter(daysList, this)
        hourlyTempAdapter = HourlyTempAdapter()
        currentDayTempDetailAdapter = CurrentDayTempDetailAdapter()
        binding.fragmentMainTvLocation.text = apiResponse.location.name + "," + apiResponse.location.country
        binding.apply {
            fragmentMainRvDaysList.setHasFixedSize(true)
            fragmentMainRvDaysList.layoutManager = GridLayoutManager(context, 3)
            fragmentMainRvDaysList.adapter = daysAdapter
            daysAdapter.notifyDataSetChanged()

            fragmentMainRvHourlyTemp.setHasFixedSize(true)
            fragmentMainRvHourlyTemp.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            fragmentMainRvHourlyTemp.adapter = hourlyTempAdapter

            fragmentMainHourlyWeatherDetail.templateWeatherDetailRvProperties.setHasFixedSize(true)
            fragmentMainHourlyWeatherDetail.templateWeatherDetailRvProperties.layoutManager = GridLayoutManager(context,2)
            fragmentMainHourlyWeatherDetail.templateWeatherDetailRvProperties.adapter=currentDayTempDetailAdapter


        }
        bindViews(daysList[0])
        hourlyTempAdapter.setData(daysList[0].hour)
        setActiveDayWeatherDetail(daysList[0])

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onItemClick(forecastday: Forecastday) {
        bindViews(forecastday)
        hourlyTempAdapter.setData(forecastday.hour)
        setActiveDayWeatherDetail(forecastday)
    }

    private fun setActiveDayWeatherDetail(forecastday: Forecastday) {
        binding.fragmentMainHourlyWeatherDetail.apply {
//            templateHourlyWeatherDay.text = CustomDateFormatter().getDateCategory(forecastday.date)
            templateWeatherDetailCondition.text = forecastday.day.condition.text
            Glide.with(binding.root)
                    .load("http:" + forecastday.day.condition.icon)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(templateWeatherDetailImage)

            templateWeatherDetailMaxTemp.text = forecastday.day.maxtemp_c.toString() + getString(R.string.degree_celsius)+"  |"
            templateWeatherDetailMinTemp.text = forecastday.day.mintemp_c.toString() + getString(R.string.degree_celsius)

            currentDayTempDetailAdapter.setProperties(generatePropertiesList(forecastday))

        }


    }

    private fun generatePropertiesList(forecastday: Forecastday):ArrayList<ModelKeyValue>{
        var propertiesList = ArrayList<ModelKeyValue>()

        propertiesList.add(ModelKeyValue("Wind",forecastday.day.maxwind_kph.toString()+" km/h"))
        propertiesList.add(ModelKeyValue("Humidity",forecastday.day.avghumidity.toString()+" %"))
        propertiesList.add(ModelKeyValue("Visibility",forecastday.day.avgvis_km.toString()+" km"))
        propertiesList.add(ModelKeyValue("UV",forecastday.day.uv.toString()))
        propertiesList.add(ModelKeyValue("Precip.",forecastday.day.totalprecip_mm.toString()+" mm"))
        propertiesList.add(ModelKeyValue("Rain",forecastday.day.maxwind_kph.toString()+" %"))
        propertiesList.add(ModelKeyValue("Snow",forecastday.day.maxwind_kph.toString()+" %"))


        return propertiesList
    }
}