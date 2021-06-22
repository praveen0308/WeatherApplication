package com.example.weatherapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.weatherapplication.databinding.FragmentMainBinding
import com.example.weatherapplication.databinding.TemplateDayTitleBinding
import com.example.weatherapplication.models.Forecastday
import com.example.weatherapplication.utils.CommonMethods
import com.example.weatherapplication.utils.CustomDateFormatter

class DaysAdapter(private val daysList: List<Forecastday>, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {

    private var currentActiveDay = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val binding = TemplateDayTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        holder.bind(daysList[position])
        if (position==currentActiveDay){
            holder.binding.templateDayImgIndicator.visibility =View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    inner class DaysViewHolder(public val binding: TemplateDayTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

                val position = bindingAdapterPosition
                listener.onItemClick(daysList[position])
                currentActiveDay=position
                notifyDataSetChanged()
            }
        }

        fun bind(forecastday: Forecastday) {
            binding.apply {
                templateDayImgIndicator.visibility = View.INVISIBLE
                templateDayTitleTxt.text = CustomDateFormatter().getDateCategory(forecastday.date)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(forecastday: Forecastday)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Forecastday>() {
            override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday) =
                    oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday) =
                    oldItem == newItem
        }
    }

}