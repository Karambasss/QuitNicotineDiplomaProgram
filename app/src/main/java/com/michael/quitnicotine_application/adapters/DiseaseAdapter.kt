package com.michael.quitnicotine_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.data.Disease
import kotlinx.android.synthetic.main.disease_cardview.view.*


class DiseaseAdapter(private val mList: MutableList<Disease>, private val mRecyclerViewInterface: RecyclerViewInterface) :
    RecyclerView.Adapter<DiseaseAdapter.ViewHolder>(){

    interface RecyclerViewInterface {
        fun onItemClick(position: Int)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.disease_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = mList[position]
        holder.itemView.imageView_diseaseIcon.setImageResource(disease.getDiseaseImage())
        holder.itemView.disease_name.text = disease.getDiseaseName()
    }
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION){
                    mRecyclerViewInterface.onItemClick(pos)
                }
            }
        }
    }
}