package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.data.Disease
import kotlinx.android.synthetic.main.fragment_disease_detail.*

class DiseaseDetailFragment : Fragment() {
    private lateinit var disease: Disease
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // получение данных из прошлого фрагмента
        val args = arguments
        val diseaseFromArgs = args?.getString("diseaseTagBundle")
        if (diseaseFromArgs != null){
            val gson = Gson()
            disease = gson.fromJson(diseaseFromArgs, Disease::class.java)
        }
        return inflater.inflate(R.layout.fragment_disease_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDiseaseDetailData()

        backToDiseaseFragment.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setDiseaseDetailData(){
        headingDisease.text = disease.getDiseaseName()
        diseaseImage.setImageResource(disease.getDiseaseImage())
        disease_detailedText.text = disease.getDiseaseDetailedText()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DiseaseDetailFragment()
    }
}