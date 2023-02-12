package com.michael.quitnicotine_application.view.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.michael.quitnicotine_application.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }


}