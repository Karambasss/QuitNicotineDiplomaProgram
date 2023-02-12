package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.michael.quitnicotine_application.R
import com.michael.quitnicotine_application.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var bottomNavigationView: BottomNavigationView

    private val fragmentsList = listOf(
        ProgressFragment.newInstance(),
        ArFragment.newInstance(),
        MotivationFragment.newInstance(),
        AchievementFragment.newInstance()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottomNavigationView.visibility = View.VISIBLE

        val fragmentsListTitle = listOf(
            "Прогресс",
            "Дополненная реальность",
            "Мотивация",
            "Достижения"
        )

        val fragmentsListIcons = listOf(
            R.drawable.ic_baseline_equalizer_24 ,
            R.drawable.ic_baseline_camera_alt_24 ,
            R.drawable.ic_baseline_insert_emoticon_24 ,
            R.drawable.ic_baseline_emoji_events_24
        )

        val adapter: ViewPagerAdapter = ViewPagerAdapter(requireActivity(), fragmentsList)
        viewPager2.adapter = adapter

        TabLayoutMediator(tab_layout, viewPager2){
            tab, position ->
            tab.setIcon(fragmentsListIcons[position])
            tab.text = fragmentsListTitle[position]
        }.attach()

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}