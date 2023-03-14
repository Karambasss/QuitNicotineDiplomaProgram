package com.michael.quitnicotine_application.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_progress.*

class ProgressFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var goalTagConstant = 1 // меняется при нажатии на элемент progress bar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE
        )
        setProgressData(getSharedPreferencesParsedObject())

        circularProgressBar.setOnClickListener {
            setTagConstant(getSharedPreferencesParsedObject())
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

    private fun setProgressData(userData: UserData?){
        if (userData?.getGoal1DayCount() != null){
            goalName.text = "Не курить ${userData.getGoal1DayCount()} дн."
            circularProgressBar.progress = userData.getGoal1ProgressPercent().toFloat()
            goalProgress.text = userData.getGoal1ProgressPercent().toString() + "%"
            if (userData.getGoal1AchievementStatus()){
                circularProgressBar.progressBarColor = Color.parseColor("#0AAF67")
                view11.setBackgroundColor(Color.parseColor("#0AAF67"))
                headingTextView2.text = "Вы достигли цели"
            }
            else{
                circularProgressBar.progressBarColor = Color.parseColor("#298AF1")
                view11.setBackgroundColor(Color.parseColor("#298AF1"))
                headingTextView2.text = "Пройдено вами до цели"
            }
        }
        else if (userData?.getGoal2ProductName() != null && userData.getGoal2ProductPrice() != null){
            goalName.text = "Накопить на ${userData.getGoal2ProductName()} за ${userData.getGoal2ProductPrice()} руб."
            circularProgressBar.progress = userData.getGoal2ProgressPercent().toFloat()
            goalProgress.text = userData.getGoal2ProgressPercent().toString() + "%"
            if (userData.getGoal2AchievementStatus()){
                circularProgressBar.progressBarColor = Color.parseColor("#0AAF67")
                view11.setBackgroundColor(Color.parseColor("#0AAF67"))
                headingTextView2.text = "Вы достигли цель"
            }
            else{
                circularProgressBar.progressBarColor = Color.parseColor("#298AF1")
                view11.setBackgroundColor(Color.parseColor("#298AF1"))
                headingTextView2.text = "Пройдено вами до цели"
            }
        }
    }

    private fun setTagConstant(userData: UserData?){
        // Проверка на то, что есть и цель 1 и цель 2
        if ((userData?.getGoal1DayCount() != null) && (userData.getGoal2ProductName() != null && userData.getGoal2ProductPrice() != null)){
            goalTagConstant++ // перемещение указателя (если было нажатие на цель 1 - то выведется график цели номер 2, и так наоборот)
            if (goalTagConstant == 3) goalTagConstant = 1 // если нажатий 3 - то, значит, было нажатие на цель 2, значит, выведется график цели номер 1
        }
        // Проверка на то, что есть только цель 1
        else if ((userData?.getGoal1DayCount() != null) && (userData.getGoal2ProductName() == null && userData.getGoal2ProductPrice() == null)){
            goalTagConstant = 1
        }
        // Проверка на то, что есть только цель 2
        else if ((userData?.getGoal1DayCount() == null) && (userData?.getGoal2ProductName() != null && userData.getGoal2ProductPrice() != null)){
            goalTagConstant = 2
        }
        setOnChangeProgressBar(userData)
    }

    private fun setOnChangeProgressBar(userData: UserData?){
        Log.d("onChangeGoalTag", "Вывод графика целей № $goalTagConstant")
        when (goalTagConstant){
            1 -> {
                goalName.text = "Не курить ${userData?.getGoal1DayCount()} дн."
                circularProgressBar.progress = userData?.getGoal1ProgressPercent()!!.toFloat()
                goalProgress.text = userData.getGoal1ProgressPercent().toString() + "%"
                if (userData.getGoal1AchievementStatus()){
                    circularProgressBar.progressBarColor = Color.parseColor("#0AAF67")
                    view11.setBackgroundColor(Color.parseColor("#0AAF67"))
                    headingTextView2.text = "Вы достигли цели"
                }
                else{
                    circularProgressBar.progressBarColor = Color.parseColor("#298AF1")
                    view11.setBackgroundColor(Color.parseColor("#298AF1"))
                    headingTextView2.text = "Пройдено вами до цели"
                }
            }
            2 -> {
                goalName.text = "Накопить на ${userData?.getGoal2ProductName()} за ${userData?.getGoal2ProductPrice()} руб."
                circularProgressBar.progress = userData?.getGoal2ProgressPercent()!!.toFloat()
                goalProgress.text = userData.getGoal2ProgressPercent().toString() + "%"
                if (userData.getGoal2AchievementStatus()){
                    circularProgressBar.progressBarColor = Color.parseColor("#0AAF67")
                    view11.setBackgroundColor(Color.parseColor("#0AAF67"))
                    headingTextView2.text = "Вы достигли цель"
                }
                else{
                    circularProgressBar.progressBarColor = Color.parseColor("#298AF1")
                    view11.setBackgroundColor(Color.parseColor("#298AF1"))
                    headingTextView2.text = "Пройдено вами до цели"
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProgressFragment()
    }
}