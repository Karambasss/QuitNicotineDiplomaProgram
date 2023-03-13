package com.michael.quitnicotine_application.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_progress_params_detail.*

class ProgressParamsDetailFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var detailCode = -1 // код для отображения только той части, на которую пользователь нажал (пример: нажал на кол-во сэкономленных денег, открывается этот фрагмент, показывающий инфу о сэкономленных деньгах)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // получение данных из прошлого фрагмента
        val args = this.arguments
        val fragmentTag = args?.getInt("progressParamsTagBundle")
        if (fragmentTag != null) {
            detailCode = fragmentTag
        }
        return inflater.inflate(R.layout.fragment_progress_params_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE
        )
        setProgressDetailData(getSharedPreferencesParsedObject())

        backToProfileImageButton2.setOnClickListener {
            val fragment: Fragment = ProfileFragment.newInstance()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame_layout, fragment)
                commit()
            }
        }

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

    private fun setProgressDetailData(userData: UserData?){
        if (detailCode == 1){
            val savedMoneyFor1Day = userData?.getSavedMoneyForADay()

            typeTextView.text = userData?.getSavedMoney().toString()
            setProgressSavedMoneyData(savedMoneyFor1Day)
        }
        else if (detailCode == 2){
            val savedCigarettesFor1Day = userData?.getCigarettesCount()

            typeTextView.text = userData?.getSavedCigarettes().toString()
            setProgressSavedCigarettesTextAndImages()
            setProgressSavedCigarettesData(savedCigarettesFor1Day)
        }
    }

    private fun setProgressSavedMoneyData(savedMoneyFor1Day: Double?){
        if (savedMoneyFor1Day != null){
            progressDataForDay.text = savedMoneyFor1Day.toString()
            progressDataForWeek.text = (7 * savedMoneyFor1Day).toString()
            progressDataForMonth.text = (30 * savedMoneyFor1Day).toString()
            progressDataForYear.text = (365 * savedMoneyFor1Day).toString()
        }
    }

    private fun setProgressSavedCigarettesTextAndImages(){
        headingTextView.text = "Не выкурено"
        headingImageView.setImageResource(R.drawable.heart_svgrepo_com)
        typeImageView.setImageResource(R.drawable.cigarette_try_svgrepo_com)
        progressTextForDay.text = "сиг./день"
        progressTextForWeek.text = "сиг./неделю"
        progressTextForMonth.text = "сиг./месяц"
        progressTextForYear.text = "сиг./год"
    }

    private fun setProgressSavedCigarettesData(savedCigarettesFor1Day: Int?){
        if (savedCigarettesFor1Day != null){
            progressDataForDay.text = savedCigarettesFor1Day.toString()
            progressDataForWeek.text = (7 * savedCigarettesFor1Day).toString()
            progressDataForMonth.text = (30 * savedCigarettesFor1Day).toString()
            progressDataForYear.text = (365 * savedCigarettesFor1Day).toString()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProgressParamsDetailFragment()
    }
}