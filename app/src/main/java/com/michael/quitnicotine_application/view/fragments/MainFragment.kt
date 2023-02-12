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
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomNavigationView.visibility = View.VISIBLE
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )

        // Получаем данные из кэша о человеке
        val userData = getSharedPreferencesParsedObject()
        println("FragmentMainGotData: Username - ${userData?.getUserName()}; CigarettesCount - ${userData?.getCigarettesCount()}; PacketPrice - ${userData?.getPacketPrice()}")

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