package com.test.facts_of_numbers.fragments.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.test.facts_of_numbers.R
import com.test.facts_of_numbers.model.Numbers
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var numbersList = emptyList<Numbers>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = numbersList[position]
        holder.itemView.numberTxt.text = currentItem.number.toString()
        holder.itemView.factOfNumberTxt.text = currentItem.factOfNumber

        holder.itemView.rowLayout.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAboutFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(numbers: List<Numbers>) {
        this.numbersList = numbers.reversed()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return numbersList.size
    }
}