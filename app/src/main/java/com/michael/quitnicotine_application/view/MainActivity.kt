package com.michael.quitnicotine_application.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.UserData
import com.michael.quitnicotine_application.view.fragments.FragmentAuth1
import com.michael.quitnicotine_application.view.fragments.MainFragment
import com.michael.quitnicotine_application.view.fragments.ProfileFragment
import com.michael.quitnicotine_application.view.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: Fragment
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // проверяем кэш приложения, если там есть данные, фрагмент авторизации не вызываем, вызываем сразу фрагмент главной страницы
        sharedPreferences = getSharedPreferences(ShConstants.SHARED_PREF_KEY, MODE_PRIVATE)

        // создаем фрагмент в зависимости от кэша, и если он существует то отрисовываем панель навигации )
        if (checkSharedPreferencesData()){
            updateUserProgressAndSaveUserData(getSharedPreferencesParsedObject())
            fragment = MainFragment.newInstance()
            bottomNavigationView.visibility = View.VISIBLE
        }
        else{
            fragment = FragmentAuth1.newInstance()
            bottomNavigationView.visibility = View.INVISIBLE
        }

        setNewFragment(fragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.menu_main -> setNewFragment(MainFragment.newInstance())

                R.id.menu_profile -> setNewFragment(ProfileFragment.newInstance())

                R.id.menu_settings -> setNewFragment(SettingsFragment())

                else -> {}
            }
            true
        }
    }
    // метод проверки кэша
    private fun checkSharedPreferencesData(): Boolean = sharedPreferences.contains(ShConstants.KEY_NAME_USER_DATA)

    // метод перехода во фрагмент
    private fun setNewFragment(fragment : Fragment){

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }

    private fun getSharedPreferencesParsedObject(): UserData?{
        if (checkSharedPreferencesData()){
            val jsonData = sharedPreferences.getString(ShConstants.KEY_NAME_USER_DATA, null)
            val gson = Gson()
            return gson.fromJson(jsonData, UserData::class.java)
        }
        return null
    }

    // Обновление кол-ва дней, сэкономленных денег, и кол-во невыкуренных сигарет (при заходе пользователя в приложение)
    private fun updateUserProgressAndSaveUserData(userData: UserData?){
        // Определяем разницу между датой регистрации пользователя и текущей датой (проще говоря, кол-во дней)
        val localDateNow = LocalDate.now()

        val dateFormatInput = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val registrationTime = LocalDate.parse(userData?.getRegistrationTime(), dateFormatInput)

        val days = ChronoUnit.DAYS.between(registrationTime, localDateNow) + 1
        Log.d("DaysDifferenceTAG", "Разница между регистрацией и текущей датой: $days")

        // обновляем дни
        userData?.updateDayCount(days.toInt())

        // обновляем кол-во денег сэкономленных
        userData?.updateSavedMoney()

        // обновляем кол-во невыкуренных сигарет
        userData?.updateSavedCigarettes()

        // обновление достижений
        updateAchievements(userData)

        // Сохранение на кэш память
        val gson = Gson()
        val myJson = gson.toJson(userData)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(ShConstants.KEY_NAME_USER_DATA, myJson)
        editor.apply()
    }

    private fun updateAchievements(userData: UserData?){
        val dayCount = userData?.getDayCount()
        val cigarettesSaved = userData?.getSavedCigarettes()
        val moneySaved = userData?.getSavedMoney()

        val achievementsList = userData?.getAchievements()
        //0..7 - ачивки за дни
        //8..17 - ачивки за сигареты
        //18..len()-1 - ачивки за деньги

        for (i in 0 until achievementsList!!.size){
            when (i){
                in 0..8 ->{
                    if (!achievementsList[i].getAchievementStatus()){
                        if (dayCount != null) {
                            achievementsList[i].updateStatusAndProgress(dayCount)
                        }
                    }
                }
                in 9..19 ->{
                    if (!achievementsList[i].getAchievementStatus()){
                        if (cigarettesSaved != null) {
                            achievementsList[i].updateStatusAndProgress(cigarettesSaved)
                        }
                    }
                }
                in 20..32 ->{
                    if (!achievementsList[i].getAchievementStatus()){
                        if (moneySaved != null) {
                            achievementsList[i].updateStatusAndProgress(moneySaved.toInt())
                        }
                    }
                }
            }
        }
        userData.updateAchievements(achievementsList)
    }
}