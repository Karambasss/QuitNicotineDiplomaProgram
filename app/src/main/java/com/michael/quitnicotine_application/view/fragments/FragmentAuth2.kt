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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class FragmentAuth2 : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userName: String
    private var cigarettesCount: Int = 0
    private var packetPrice: Int = 0
    private var checkBox1Flag: Boolean = false
    private var checkBox2Flag: Boolean = false
    private var goal1DayCount: Int = 0
    private lateinit var goal2ProductName: String
    private var goal2ProductPrice: Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // получение данных из прошлого фрагмента
        val args = this.arguments
        val fragment1UserName = args?.get("userNameDataBundle").toString()
        Log.d("Fr2DataFromFf1", fragment1UserName)
        userName = fragment1UserName
        // Toast.makeText(activity,"Вы успешно передали данные из фрагмента 1 в фрагмент 2!\nПолученные данные: $fragment1UserName", Toast.LENGTH_LONG).show()
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
    // проверка того, что хотя бы один чекбокс выбран и его содержимое не пустое
    private fun checkCheckBoxesResult() : Boolean {
        checkBox1Flag = false
        checkBox2Flag = false
        if (checkBoxGoal1.isChecked){
            if (goal1Day.length() == 0){
                goal1Day.requestFocus()
                goal1Day.error = "Введите кол-во дней"
                return false
            }
            checkBox1Flag = true
            goal1DayCount = goal1Day.text.toString().toInt()
        }

        if (checkBoxGoal2.isChecked){
            if (goal2Name.length() == 0){
                goal2Name.requestFocus()
                goal2Name.error = "Введите товар"
                return false
            }
            if (goal2Price.length() == 0){
                goal2Price.requestFocus()
                goal2Price.error = "Введите цену товара"
                return false
            }
            checkBox2Flag = true
            goal2ProductName = goal2Name.text.toString()
            goal2ProductPrice = goal2Price.text.toString().toInt()
        }
        // Проверка на то, что ни один из чекбоксов не выбран - только тогда выдаем всплывающее сообщение!
        if (!checkBox1Flag && !checkBox2Flag){
            Toast.makeText(requireContext(), "Пожалуйста, выберите цель!", Toast.LENGTH_LONG).show()
            checkBox1Flag = false
            checkBox2Flag = false
            return false
        }

        return true
    }

    // проверка данных
   private fun checkAllFields() : Boolean {
        if (authNumber.length() == 0){
            authNumber.requestFocus()
            authNumber.error = "Введите кол-во сигарет"
            return false
        }
        if (authNumber.text.toString().toInt() == 0){
            authNumber.requestFocus()
            authNumber.error = "Введите число большее 0"
            return false
        }
        if (authPrice.length() == 0){
            authPrice.requestFocus()
            authPrice.error = "Введите стоимость пачки"
            return false
        }
        if (!checkCheckBoxesResult()){
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

        if (checkBox1Flag){
            userData.setGoal1DayCount(goal1DayCount)
        }
        if (checkBox2Flag){
            userData.setGoal2ProductName(goal2ProductName)
            userData.setGoal2ProductPrice(goal2ProductPrice)
        }

        val format = SimpleDateFormat("dd.MM.yyyy")
        val time = Calendar.getInstance().time
        val currentDateTime = format.format(time)

        Log.d("CurrentDateCheck", currentDateTime)
        userData.setRegistrationTime(currentDateTime)

        // Обновление кол-ва дней, сэкономленных денег, и кол-во невыкуренных сигарет сразу после успешной авторизации
        val localDateNow = LocalDate.now()

        val dateFormatInput = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val registrationTime = LocalDate.parse(currentDateTime, dateFormatInput)

        val days = ChronoUnit.DAYS.between(registrationTime, localDateNow) + 1

        // обновляем дни
        userData.updateDayCount(days.toInt())

        // обновляем кол-во денег сэкономленных
        userData.updateSavedMoney()

        // обновляем кол-во невыкуренных сигарет
        userData.updateSavedCigarettes()

        // Сохраняем данных на кэш
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