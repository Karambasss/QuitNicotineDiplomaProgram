package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.michael.quitnicotine_application.R
import kotlinx.android.synthetic.main.fragment_auth1.*
import kotlinx.android.synthetic.main.fragment_auth2.view.*

class FragmentAuth1 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_auth1.setOnClickListener {
            if (checkField()){
                Log.d("CheckFragment1Text","Name: ${authName.text}")
                val userName = authName.text.toString()

                // Способ передачи данных через Bundle - наподобие intent.putExtra()
                val bundle : Bundle = Bundle()
                bundle.putString("userNameDataBundle", userName)

                // Фрагмент, который будет запущен, ставим ему в аргументы данные Bundle
                val fragment = FragmentAuth2.newInstance()
                fragment.arguments = bundle

                // Переходим на новый фрагмент
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_layout, fragment)
                    commit()
                }
            }
        }
    }

    private fun checkField() : Boolean {
        if (authName.text.isBlank()) {
            authName.requestFocus()
            authName.error = "Введите ваше имя!"
            return false
        }
        return true
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentAuth1()
    }
}