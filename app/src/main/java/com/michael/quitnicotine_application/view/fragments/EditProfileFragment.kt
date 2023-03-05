package com.michael.quitnicotine_application.view.fragments

import android.content.Context
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
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(
            ShConstants.SHARED_PREF_KEY, Context.MODE_PRIVATE
        )
        // Отображение данных в окне редактирования
        setProfileData(getSharedPreferencesParsedObject())

        backToProfileImageButton.setOnClickListener {
            val fragment: Fragment = ProfileFragment.newInstance()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame_layout, fragment)
                commit()
            }
        }

        // сохранение новых данных
        saveProfileChangesImageButton.setOnClickListener {
            if (checkAllFields()){
                userName = editName.text.toString()
                cigarettesCount = editNumber.text.toString().toInt()
                packetPrice = editPrice.text.toString().toInt()
                onSaveUserData()

                // редактирование завершено, данные сохранены, делаем переход на фрагмент профиля
                val fragment = ProfileFragment.newInstance()
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
        if (checkBoxGoal1Edit.isChecked){
            if (goal1DayEdit.length() == 0){
                goal1DayEdit.requestFocus()
                goal1DayEdit.error = "Введите кол-во дней"
                return false
            }
            checkBox1Flag = true
            goal1DayCount = goal1DayEdit.text.toString().toInt()
        }

        if (checkBoxGoal2Edit.isChecked){
            if (goal2NameEdit.length() == 0){
                goal2NameEdit.requestFocus()
                goal2NameEdit.error = "Введите товар"
                return false
            }
            if (goal2PriceEdit.length() == 0){
                goal2PriceEdit.requestFocus()
                goal2PriceEdit.error = "Введите цену товара"
                return false
            }
            checkBox2Flag = true
            goal2ProductName = goal2NameEdit.text.toString()
            goal2ProductPrice = goal2PriceEdit.text.toString().toInt()
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
        if (editName.length() == 0){
            editName.requestFocus()
            editName.error = "Введите ваше имя!"
            return false
        }
        if (editNumber.length() == 0){
            editNumber.requestFocus()
            editNumber.error = "Введите кол-во сигарет"
            return false
        }
        if (editNumber.text.toString().toInt() == 0){
            editNumber.requestFocus()
            editNumber.error = "Введите число большее 0"
            return false
        }
        if (editPrice.length() == 0){
            editPrice.requestFocus()
            editPrice.error = "Введите стоимость пачки"
            return false
        }
        if (!checkCheckBoxesResult()){
            return false
        }
        return true
    }

    // сохранение в кэш объекта
    private fun onSaveUserData(){
        val userData = getSharedPreferencesParsedObject()

        if (checkBox1Flag){
            userData?.setGoal1DayCount(goal1DayCount)
        }
        else{
            userData?.setGoal1DayCount(null)
        }
        if (checkBox2Flag){
            userData?.setGoal2ProductName(goal2ProductName)
            userData?.setGoal2ProductPrice(goal2ProductPrice)
        }
        else{
            userData?.setGoal2ProductName(null)
            userData?.setGoal2ProductPrice(null)
        }
        userData?.setUserName(userName)
        userData?.setCigarettesCount(cigarettesCount)
        userData?.setPacketPrice(packetPrice)
        userData?.updateSavedCigarettes()
        userData?.updateSavedMoney()

        val gson = Gson()
        val myJson = gson.toJson(userData)
        Log.d("jsonDataLog", myJson)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(ShConstants.KEY_NAME_USER_DATA, myJson)
        editor.apply()
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
        editName.setText(userData?.getUserName())
        editNumber.setText(userData?.getCigarettesCount().toString())
        editPrice.setText(userData?.getPacketPrice().toString())
        if (userData?.getGoal1DayCount() != null){
            checkBoxGoal1Edit.isChecked = true
            goal1DayEdit.setText(userData.getGoal1DayCount().toString())
        }
        if (userData?.getGoal2ProductName() != null && userData.getGoal2ProductPrice() != null){
            checkBoxGoal2Edit.isChecked = true
            goal2NameEdit.setText(userData.getGoal2ProductName())
            goal2PriceEdit.setText(userData.getGoal2ProductPrice().toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditProfileFragment()
    }
}