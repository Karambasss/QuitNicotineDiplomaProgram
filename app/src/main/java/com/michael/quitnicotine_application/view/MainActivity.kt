package com.michael.quitnicotine_application.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.view.fragments.FragmentAuth1
import com.michael.quitnicotine_application.view.fragments.FragmentAuth2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNewFragment(FragmentAuth1.newInstance())
    }

    private fun setNewFragment(fragment : Fragment){
        // TODO - проверка на то, что имя заполнено - тогда фрагмент не вызывается!
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}