package com.michael.quitnicotine_application.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.data.Idea
import kotlinx.android.synthetic.main.idea_cardview.view.*

class IdeaAdapter(private val mList: MutableList<Idea>) : RecyclerView.Adapter<IdeaAdapter.ViewHolder>(){

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.idea_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val idea = mList[position]

        holder.itemView.imageView_ideaIcon.setImageResource(idea.getIdeaImage())
        holder.itemView.idea_name.text = idea.getIdeaHeading()
        holder.itemView.idea_detailedText.text = idea.getIdeaDetailedText()

        // проверка на то, что элемент открыт или скрыт + установление ему видимости (visible или gone) в зависимости от этого
        Log.d("checkRecycler", "CoolItIsBinding")
        val visible = idea.isVisible()
        if (visible){
            holder.itemView.expandableLayout.visibility = View.VISIBLE
            holder.itemView.imageView_expendableIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
        }
        else{
            holder.itemView.expandableLayout.visibility = View.GONE
            holder.itemView.imageView_expendableIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
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
    private fun shouldBeExpanded(currentClickedIdea: Idea){
        if (currentClickedIdea.isVisible()){
            currentClickedIdea.setVisible(false)
        }
        else{
            for (i in mList.indices){
                if (mList[i] == currentClickedIdea){
                    currentClickedIdea.setVisible(true)
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
                val idea = mList[bindingAdapterPosition]
                shouldBeExpanded(idea)
                Log.d("checkRecycler", "${idea.isVisible()}")
                notifyDataSetChanged() // для того, чтобы обновили видную пользователю часть
            }
        }
    }
}