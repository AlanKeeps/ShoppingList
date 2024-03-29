package com.example.shoppinglist.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var defPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, SettingsFragment()).
                commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getSelectedTheme(): Int{
        return if(defPref.getString("theme_key", "green") == "green"){
            R.style.Theme_ShoppingListGreen
        }else{
            R.style.Theme_ShoppingListBrown
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}