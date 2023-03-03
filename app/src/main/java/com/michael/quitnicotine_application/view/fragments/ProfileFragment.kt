package com.michael.quitnicotine_application.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE
        )
        setProfileData(getSharedPreferencesParsedObject())

        profileImage.setOnClickListener {
            // TODO - обновление картинки от пользователя
        }

        editButton.setOnClickListener {
            // TODO - редактирование
        }

        exitButton.setOnClickListener {
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
            bottomNavigationView.menu.findItem(R.id.menu_main).isChecked = true
            bottomNavigationView.visibility = View.INVISIBLE

        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkSharedPreferencesData() = sharedPreferences.contains(ShConstants.KEY_NAME_USER_DATA)

    private fun getSharedPreferencesParsedObject(): UserData?{
        if (checkSharedPreferencesData()){
            val jsonData = sharedPreferences.getString(ShConstants.KEY_NAME_USER_DATA, null)
            val gson = Gson()
            return gson.fromJson(jsonData, UserData::class.java)
        }
        return null
    }

    private fun setProfileData(userData: UserData?){
        Log.d("CheckBoxCheckResult", "Данные целей пользователя: ${userData?.getGoal1DayCount()}, ${userData?.getGoal2ProductName()}, ${userData?.getGoal2ProductPrice()}")
        profile_name.text = userData?.getUserName()
        profile_cigarettesCount.text = userData?.getCigarettesCount().toString()
        profile_packetPrice.text = userData?.getPacketPrice().toString()
        profile_dayCount.text = userData?.getDayCount().toString()
        profile_savedMoney.text = userData?.getSavedMoney().toString()
        profile_savedCigarettes.text = userData?.getSavedCigarettes().toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}