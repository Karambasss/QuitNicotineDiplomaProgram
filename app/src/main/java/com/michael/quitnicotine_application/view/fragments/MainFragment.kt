package com.michael.quitnicotine_application.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView = activity?.findViewById(R.id.bottomNavigationView)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomNavigationView?.visibility = View.VISIBLE
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )

        // Получаем данные из кэша о человеке
        //val userData = getSharedPreferencesParsedObject()
        //textView6.text = userData?.getUserName()
        //textView7.text = userData?.getCigarettesCount().toString()
        //textView8.text = userData?.getPacketPrice().toString()

        // TODO заменить кнопку выхода на элемент из контекстного меню "Выход из профиля"
        button.setOnClickListener {

            // Удаляем из кэша данные
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            // Переходим на фрагмент авторизации))))
            val fragment: Fragment = FragmentAuth1.newInstance()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame_layout, fragment)
                commit()
            }
            bottomNavigationView?.visibility = View.INVISIBLE
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkSharedPreferencesData() = sharedPreferences.contains(ShConstants.KEY_NAME_USER_DATA)

    private fun getSharedPreferencesParsedObject(): UserData? {
        if (checkSharedPreferencesData()){
            val jsonData = sharedPreferences.getString(ShConstants.KEY_NAME_USER_DATA, null)
            val gson = Gson()
            return gson.fromJson(jsonData, UserData::class.java)
        }
        return null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}