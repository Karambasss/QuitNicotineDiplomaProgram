package com.michael.quitnicotine_application.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.constances.ShConstants
import com.michael.quitnicotine_application.view.fragments.FragmentAuth1
import com.michael.quitnicotine_application.view.fragments.MainFragment
import com.michael.quitnicotine_application.view.fragments.ProfileFragment
import com.michael.quitnicotine_application.view.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

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
}