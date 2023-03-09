package com.michael.quitnicotine_application.view.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.adapters.IdeaAdapter
import com.michael.quitnicotine_application.data.Idea
import kotlinx.android.synthetic.main.fragment_ideas.*

class IdeasFragment : Fragment() {
    private var ideasList = mutableListOf<Idea>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ideas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initRecycler()
    }

    private fun initData(){
        val ideasHeadings = resources.getStringArray(R.array.ideasHeadings)
        val ideasDetailedText = resources.getStringArray(R.array.detailedTexts)
        val ideasImages = arrayOf(
            R.drawable.sport_bike_svgrepo_com,
            R.drawable.explore_svgrepo_com,
            R.drawable.work_case_filled_svgrepo_com,
            R.drawable.doctor_svgrepo_com,
            R.drawable.shield_svgrepo_com,
            R.drawable.no_smoke_svgrepo_com,
            R.drawable.psychotherapy_fill_svgrepo_com,
            R.drawable.psychologist_svgrepo_com,
            R.drawable.bandage_fill_svgrepo_com,
            R.drawable.profit_graph_infographic_data_element_svgrepo_com
        )
        for (i in ideasHeadings.indices){
            ideasList.add(Idea(ideasHeadings[i], ideasImages[i], ideasDetailedText[i]))
        }
    }

    private fun initRecycler(){
        val adapter = IdeaAdapter(ideasList)
        ideas_recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = IdeasFragment()
    }
}