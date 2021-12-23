package ru.gb.donspb.justpic.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import ru.gb.donspb.justpic.R
import ru.gb.donspb.justpic.ui.addon.AddonActivity
import ru.gb.donspb.justpic.ui.rv.RecyclerFragment

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
                R.id.navigation_one -> startActivity(Intent(context, AddonActivity::class.java))
                R.id.navigation_two -> requireActivity().supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, RecyclerFragment.newInstance())
                        .addToBackStack("")
                        .commit()
                }
            }
            dismiss()
            true
        }
    }
}