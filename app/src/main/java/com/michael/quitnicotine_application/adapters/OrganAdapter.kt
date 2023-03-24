package com.michael.quitnicotine_application.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.data.Organ
import kotlinx.android.synthetic.main.health_cardview.view.*
import kotlinx.android.synthetic.main.health_cardview.view.expandableLayout

class OrganAdapter(private val mList: MutableList<Organ>) : RecyclerView.Adapter<OrganAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.health_cardview, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("checkOnBindOrganViewHolder", "cool, organ viewHolder №$position is binding")

        val organ = mList[position]
        holder.itemView.organ_Image.setImageResource(organ.getOrganImage())
        holder.itemView.organName.text = organ.getOrganName()
        holder.itemView.organDetailedText.text = organ.getOrganDetailedText()

        // проверка на то, что элемент открыт или скрыт + установление ему видимости (visible или gone) в зависимости от этого
        if (organ.isVisible()){
            holder.itemView.expandableLayout.visibility = View.VISIBLE
            holder.itemView.imageView_expendableIcon2.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
        }
        else{
            holder.itemView.expandableLayout.visibility = View.GONE
            holder.itemView.imageView_expendableIcon2.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Умная функция - при нажатии на элемент списка в нее передается этот нажатый элемент.
    // В функции происходит обход всего списка, и если это не нажатый элемент, то такие элементы закрываются.
    // Если нашелся нажатый элемент, то, в зависимости от его текущего состояния - открыт или закрыт, устанавливается состояние,
    // противоположное текущему его состоянию.
    private fun shouldBeExpanded(currentClickedOrgan: Organ){
        if (currentClickedOrgan.isVisible()){
            currentClickedOrgan.setVisible(false)
        }
        else{
            for (i in mList.indices){
                if (mList[i] == currentClickedOrgan){
                    currentClickedOrgan.setVisible(true)
                }
                else{
                    mList[i].setVisible(false)
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val organ = mList[bindingAdapterPosition]
                shouldBeExpanded(organ)
                Log.d("CheckOrganRecycler", "organName = ${organ.getOrganName()} is visible = ${organ.isVisible()}}")
                notifyDataSetChanged() // для того, чтобы обновили видную пользователю часть
            }
        }
    }
}