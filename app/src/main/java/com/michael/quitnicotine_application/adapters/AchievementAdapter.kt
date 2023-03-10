package com.michael.quitnicotine_application.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.data.Achievement
import kotlinx.android.synthetic.main.achievement_cardview.view.*

class AchievementAdapter(private val mList: MutableList<Achievement>) : RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.achievement_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achievement = mList[position]
        holder.itemView.achievement_name.text = achievement.getAchievementName()
        holder.itemView.progressTextView.text = achievement.getProgressPercent().toString() + "%"
        holder.itemView.progressBarAchievement.progress = achievement.getProgressPercent()

        if (achievement.getAchievementStatus()) {
            holder.itemView.progressBarAchievement.setIndicatorColor(Color.parseColor("#0AAF67"))
            holder.itemView.progressTextResult.text = "Достигнуто"
        } else {
            holder.itemView.progressBarAchievement.setIndicatorColor(Color.parseColor("#298AF1"))
            holder.itemView.progressTextResult.text = "Не достигнуто"
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
