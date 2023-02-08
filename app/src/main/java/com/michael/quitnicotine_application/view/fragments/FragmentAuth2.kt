package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.michael.quitnicotine_application.R
import kotlinx.android.synthetic.main.fragment_auth1.*
import kotlinx.android.synthetic.main.fragment_auth2.*
import kotlinx.android.synthetic.main.fragment_auth2.button_auth1

class FragmentAuth2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = this.arguments
        val fragment1UserName = args?.get("userNameDataBundle").toString()
        Log.d("Fr2DataFromFf1", fragment1UserName)
        Toast.makeText(activity,"Вы успешно передали данные из фрагмента 1 в фрагмент 2!\nПолученные данные: $fragment1UserName", Toast.LENGTH_LONG).show()
        return inflater.inflate(R.layout.fragment_auth2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_auth1.setOnClickListener {
            if (checkAllFields()) {
                Log.d("CheckFragment2Text","Num: ${authNumber.text} and Price: ${authPrice.text} rub")
            }
        }
    }

   private fun checkAllFields() : Boolean {
        if (authNumber.length() == 0){
            authNumber.requestFocus()
            authNumber.error = "Введите кол-во сигарет"
            return false
        }
        if (authPrice.length() == 0){
            authPrice.requestFocus()
            authPrice.error = "Введите стоимость пачки"
            return false
        }
        return true
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentAuth2()
    }
}