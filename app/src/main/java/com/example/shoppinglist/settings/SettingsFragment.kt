package com.example.shoppinglist.settings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.shoppinglist.R
import com.example.shoppinglist.billing.BillingManager

class SettingsFragment: PreferenceFragmentCompat() {
    private lateinit var removeAdPref: Preference
    private lateinit var bManager: BillingManager
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
        init()
    }

    private fun init(){
        bManager = BillingManager(activity as AppCompatActivity)
        removeAdPref = findPreference("remove_ads_key")!!
        removeAdPref.setOnPreferenceClickListener {
            bManager.startConnection()
            true
        }
    }

    override fun onDestroy() {
        bManager.closeConnection()
        super.onDestroy()
    }
}