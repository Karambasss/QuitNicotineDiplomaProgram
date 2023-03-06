package com.michael.quitnicotine_application.view.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.data.UserData
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavigationView: BottomNavigationView
    private val GALLERY_REQUEST = 1
    private var lastClickTime: Long = 0
    private val MIN_DELAY_TIME = 1500

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
            if (isFastClick()){
                return@setOnClickListener
            }
            else{
                val permissionStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                Log.d("PermissionStatus", "$permissionStatus")

                // проверка на разрешения (если нет - запрашиваются, если уже есть - вызывается галерея, из которой можно выбрать аватар)
                if (permissionStatus == PackageManager.PERMISSION_GRANTED){
                    val intentAvatarPicker = Intent(Intent.ACTION_PICK)
                    intentAvatarPicker.type = "image/*"
                    startActivityForResult(intentAvatarPicker, GALLERY_REQUEST)
                }
                else{
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_REQUEST)
                }
            }
        }

        editButton.setOnClickListener {
            val fragment: Fragment = EditProfileFragment.newInstance()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame_layout, fragment)
                commit()
            }
        }

        exitButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("ВНИМАНИЕ!")
            alertDialogBuilder.setMessage("Вы действительно хотите выйти?")
            alertDialogBuilder.setNegativeButton("Нет", null)
            alertDialogBuilder.setPositiveButton("Да"){
                    _, _ ->
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
            alertDialogBuilder.setCancelable(true)
            alertDialogBuilder.show()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            GALLERY_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val intentAvatarPicker = Intent(Intent.ACTION_PICK)
                    intentAvatarPicker.type = "image/*"
                    startActivityForResult(intentAvatarPicker, GALLERY_REQUEST)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GALLERY_REQUEST -> {
                Log.d("RESULT_CODE_STATUS", "$resultCode")
                if (resultCode == RESULT_OK ){
                    // сохранение аватара в кэш а также его отображение сразу после выбора пользователем
                    val selectedImage: Uri? = data?.data
                    profileImage.setImageURI(selectedImage)

                    val userData = getSharedPreferencesParsedObject()
                    userData?.setAvatar(selectedImage.toString())

                    val gson = Gson()
                    val myJson = gson.toJson(userData)

                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(ShConstants.KEY_NAME_USER_DATA, myJson)
                    editor.apply()
                }
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

    private fun setProfileData(userData: UserData?){
        Log.d("CheckDateOfRegFromCache","Дата регистрации пользователя: ${userData?.getRegistrationTime()}")
        Log.d("CheckBoxCheckResult", "Данные целей пользователя: ${userData?.getGoal1DayCount()}, ${userData?.getGoal2ProductName()}, ${userData?.getGoal2ProductPrice()}")
        profile_name.text = userData?.getUserName()
        profile_cigarettesCount.text = userData?.getCigarettesCount().toString()
        profile_packetPrice.text = userData?.getPacketPrice().toString()
        profile_dayCount.text = userData?.getDayCount().toString()
        profile_savedMoney.text = userData?.getSavedMoney().toString()
        profile_savedCigarettes.text = userData?.getSavedCigarettes().toString()

        if (userData?.getAvatar() == null){
            profileImage.setImageResource(android.R.mipmap.sym_def_app_icon)
        }
        else{
            profileImage.setImageURI(Uri.parse(userData.getAvatar()))
        }
    }

    private fun isFastClick() : Boolean{
        var flag = true
        val currentClickTime = System.currentTimeMillis()
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME){
            flag = false
        }
        lastClickTime = currentClickTime
        return flag
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}