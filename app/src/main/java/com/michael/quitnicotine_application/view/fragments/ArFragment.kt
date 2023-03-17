package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michael.quitnicotine_application.R
import kotlinx.android.synthetic.main.fragment_ar.*
import kotlin.random.Random

class ArFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        openArModelButton.setOnClickListener {

        }
    }

    private fun initData(){
        val quotesArray = resources.getStringArray(R.array.citationArray)
        val quote = getRandomQuote(quotesArray)
        textViewCitates.text = quote
    }

    private fun getRandomQuote(quotesArray : Array<String>) : String{
        val number = Random.nextInt(quotesArray.size - 0)
        return quotesArray[number]
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArFragment()
    }
}