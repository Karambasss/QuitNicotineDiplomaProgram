package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.adapters.OrganAdapter
import com.michael.quitnicotine_application.data.Organ
import kotlinx.android.synthetic.main.fragment_health.*

class HealthFragment : Fragment() {
    private val organList = mutableListOf<Organ>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initRecycler()
    }

    private fun initData(){
        if (organList.size != 0){
            organList.clear()
        }
        val organsNames = resources.getStringArray(R.array.organName)
        val organsDetailedTexts = resources.getStringArray(R.array.organDetailedText)
        val organsImages = arrayOf(
            R.drawable.blood_donation_2_svgrepo_com,
            R.drawable.blood_vessel_svgrepo_com,
            R.drawable.heart_illustration_1_svgrepo_com,
            R.drawable.brain_illustration_12_svgrepo_com,
            R.drawable.human_back_svgrepo_com,
            R.drawable.lungs_svgrepo_com,
            R.drawable.intestines_svgrepo_com
        )
        for (i in organsNames.indices){
            organList.add(Organ(organsNames[i], organsImages[i], organsDetailedTexts[i]))
        }
    }

    private fun initRecycler(){
        val adapter = OrganAdapter(organList)
        healthRecycler.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = HealthFragment()
    }
}