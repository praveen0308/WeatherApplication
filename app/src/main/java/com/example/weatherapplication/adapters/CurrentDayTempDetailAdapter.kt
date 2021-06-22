package com.example.weatherapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.databinding.TemplateDetailItemBinding
import com.example.weatherapplication.models.ModelKeyValue

class CurrentDayTempDetailAdapter:RecyclerView.Adapter<CurrentDayTempDetailAdapter.DailyTempDetailViewHolder>() {

    private var properties = ArrayList<ModelKeyValue>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyTempDetailViewHolder {
        val binding= TemplateDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DailyTempDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyTempDetailViewHolder, position: Int) {
        holder.bindView(properties[position])
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    public fun setProperties(newProperties:ArrayList<ModelKeyValue>){
        properties.clear()
        properties.addAll(newProperties)
        notifyDataSetChanged()
    }


    inner class DailyTempDetailViewHolder(private val binding: TemplateDetailItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

                fun bindView(modelKeyValue: ModelKeyValue){
                    binding.apply {
                        title.text = modelKeyValue.Property
                        propertyValue.text = modelKeyValue.Value
                    }
                }
    }


}