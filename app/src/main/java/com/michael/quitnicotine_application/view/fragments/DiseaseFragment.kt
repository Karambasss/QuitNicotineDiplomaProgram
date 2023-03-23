package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.adapters.DiseaseAdapter
import com.michael.quitnicotine_application.data.Disease
import kotlinx.android.synthetic.main.fragment_disease.*

class DiseaseFragment : Fragment(), DiseaseAdapter.RecyclerViewInterface {
    private var list = mutableListOf<Disease>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_disease, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initRecycler()
    }

    private fun initData(){
        if (list.size != 0){
            list.clear()
        }
        val diseaseName = resources.getStringArray(R.array.diseaseNames)
        val diseaseDetailedText = resources.getStringArray(R.array.diseaseDetailedTexts)
        val diseaseImages = arrayOf(
            R.drawable.virus_1_svgrepo_com,
            R.drawable.medical_brain_svgrepo_com,
            R.drawable.heart_rate_1_svgrepo_com,
            R.drawable.intestine_svgrepo_com,
            R.drawable.heart_rate_1_svgrepo_com,
            R.drawable.bones_svgrepo_com,
            R.drawable.eye_off_svgrepo_com,
            R.drawable.eye_off_svgrepo_com,
            R.drawable.intestine_svgrepo_com,
            R.drawable.pregnant_woman_svgrepo_com
        )
        for (i in diseaseName.indices){
            list.add(Disease(diseaseName[i], diseaseImages[i], diseaseDetailedText[i]))
        }
    }

    private fun initRecycler(){
        val adapter = DiseaseAdapter(list, this)
        disease_recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val disease = list[position]

        val gson = Gson()
        val json = gson.toJson(disease)
        val bundle = Bundle()
        bundle.putString("diseaseTagBundle", json)

        val fragment = DiseaseDetailFragment.newInstance()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DiseaseFragment()
    }
}