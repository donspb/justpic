package ru.gb.donspb.justpic.ui.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuItemCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import ru.gb.donspb.justpic.R

private const val THEME_TAG = "ISDARK"

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.menu_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navigationView = view.findViewById<NavigationView>(R.id.navigation_view)
        val menuItem = navigationView.menu.findItem(R.id.navigation_set_dark)
        val switch = menuItem.actionView.findViewById<Switch>(R.id.nav_menu_switch)
        val sharedPrefs = activity?.getPreferences(Context.MODE_PRIVATE) ?: return


        switch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean(THEME_TAG, isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
         }

        navigationView.setNavigationItemSelectedListener {
            menuItem -> when(menuItem.itemId) {
                R.id.navigation_one -> Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                R.id.navigation_two -> Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}