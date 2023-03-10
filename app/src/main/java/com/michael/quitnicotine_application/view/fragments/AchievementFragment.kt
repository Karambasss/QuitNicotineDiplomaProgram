package com.michael.quitnicotine_application.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.adapters.AchievementAdapter
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.Achievement
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_achievement.*

class AchievementFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var achievementList = mutableListOf<Achievement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achievement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE
        )
        initData()
        initRecycler()
    }

    private fun initData(){
        val userData = getSharedPreferencesParsedObject()
        if (userData != null){
            userData.sortAchievements()
            achievementList = userData.getAchievements()
        }
    }

    private fun initRecycler(){
        val adapter = AchievementAdapter(achievementList)
        achievement_recyclerView.adapter = adapter
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


    companion object {
        @JvmStatic
        fun newInstance() = AchievementFragment()
    }
}