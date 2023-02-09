package com.michael.quitnicotine_application.view.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.Achievement
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_auth2.*
import kotlinx.android.synthetic.main.fragment_auth2.button_auth1

class FragmentAuth2 : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userName: String
    private var cigarettesCount: Int = 0
    private var packetPrice: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // получение данных из прошлого фрагмента
        val args = this.arguments
        val fragment1UserName = args?.get("userNameDataBundle").toString()
        Log.d("Fr2DataFromFf1", fragment1UserName)
        userName = fragment1UserName
        Toast.makeText(activity,"Вы успешно передали данные из фрагмента 1 в фрагмент 2!\nПолученные данные: $fragment1UserName", Toast.LENGTH_LONG).show()
        return inflater.inflate(R.layout.fragment_auth2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_auth1.setOnClickListener {
            if (checkAllFields()) {
                cigarettesCount = authNumber.text.toString().toInt()
                packetPrice = authPrice.text.toString().toInt()
                Log.d("CheckFragment2Text","Num: ${authNumber.text} and Price: ${authPrice.text} rub")
                onSaveUserData()

                // авторизация завершена, данные сохранены, делаем переход на фрагмент главной страницы
                val fragment = MainFragment.newInstance()
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_layout, fragment)
                    commit()
                }
            }
        }
    }
    // проверка данных
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
    // создание объекта
    private fun onCreateUserData(): UserData {
        val achievementList = mutableListOf<Achievement>()
        val achievementNameList = resources.getStringArray(R.array.achievementName_txt)
        val achievementConditionList = resources.getIntArray(R.array.achievementCondition)

        for (i in achievementNameList.indices) {
            achievementList.add(Achievement(achievementNameList[i], achievementConditionList[i]))
        }
        return UserData(userName, cigarettesCount, packetPrice, achievementList)
    }
    // сохранение в кэш объекта
    private fun onSaveUserData(){
        val userData = onCreateUserData()

        val gson = Gson()
        val myJson = gson.toJson(userData)
        Log.d("jsonDataLog", myJson)
        sharedPreferences = requireContext().getSharedPreferences(ShConstants.SHARED_PREF_KEY, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(ShConstants.KEY_NAME_USER_DATA, myJson)
        editor.apply()
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentAuth2()
    }
}