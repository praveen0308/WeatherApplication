package com.example.weatherapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.TemplateHourTempBinding
import com.example.weatherapplication.models.Hour

class HourlyTempAdapter:RecyclerView.Adapter<HourlyTempAdapter.HourlyTempViewHolder>() {

    private val hourlyTemperature = ArrayList<Hour>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyTempViewHolder {
        val binding = TemplateHourTempBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HourlyTempViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyTempViewHolder, position: Int) {
        holder.bindViews(hourlyTemperature[position])
    }

    override fun getItemCount(): Int {
        return hourlyTemperature.size
    }


    fun setData(newList: List<Hour>) {
        val diffCallback = HourlyDiffCallback(hourlyTemperature, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        hourlyTemperature.clear()
        hourlyTemperature.addAll(newList)
        diffResult.dispatchUpdatesTo(this)

    }

    inner class HourlyTempViewHolder(private val binding:TemplateHourTempBinding):RecyclerView.ViewHolder(binding.root){

        fun bindViews(hour: Hour){
            binding.apply {
                templateHourTempTiming.text = hour.time.takeLast(5)
                Glide.with(itemView)
                        .load("http:"+hour.condition.icon)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_error)
                        .into(templateHourTempImg)
                templateHourTempVal.text = hour.temp_c.toString().take(2)
            }
        }
    }

    inner class HourlyDiffCallback(private val oldList: List<Hour>, private val newList: List<Hour>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].time === newList[newItemPosition].time
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val (_, value, name) = oldList[oldPosition]
            val (_, value1, name1) = newList[newPosition]

            return name == name1 && value == value1
        }

        @Nullable
        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }
}