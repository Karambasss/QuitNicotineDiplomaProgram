package com.michael.quitnicotine_application.view.fragments

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.view.ArActivity
import kotlinx.android.synthetic.main.fragment_ar.*
import java.util.*
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
            if (checkSystemSupport(requireActivity())){
                val intent = Intent(activity, ArActivity::class.java)
                startActivity(intent)
            }
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

    private fun checkSystemSupport(activity: Activity): Boolean {

        //checking whether the API version of the running Android >= 24 that means Android Nougat 7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val openGlVersion = (Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager).deviceConfigurationInfo.glEsVersion

            //checking whether the OpenGL version >= 3.0
            if (openGlVersion.toDouble() >= 3.0) {
                return true
            } else {
                Toast.makeText(
                    activity,
                    "App needs OpenGl Version 3.0 or later",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        } else {
            Toast.makeText(
                activity,
                "App does not support required Build Version",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArFragment()
    }
}