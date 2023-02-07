package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michael.quitnicotine_application.R
import kotlinx.android.synthetic.main.fragment_auth2.*

class FragmentAuth2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth2, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentAuth2()
    }
}