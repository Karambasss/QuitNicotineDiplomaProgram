package com.michael.quitnicotine_application.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.data.Category
import kotlinx.android.synthetic.main.motivation_cardview.view.*

class CategoryAdapter(private val mList: MutableList<Category>, private val mRecyclerViewInterface: RecyclerViewInterface) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    interface RecyclerViewInterface {
        fun onItemClick(position: Int)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.motivation_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = mList[position]
        holder.itemView.imageView_icon.setImageResource(category.getCategoryImage())
        holder.itemView.category_name.text = category.getCategoryName()
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