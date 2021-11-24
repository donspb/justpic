package ru.gb.donspb.justpic.ui.addon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import ru.gb.donspb.justpic.R

class AddonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addon)
        val viewPagerAdapter = this.findViewById<ViewPager>(R.id.view_pager)
        viewPagerAdapter.adapter = ViewPageAdapter(supportFragmentManager)
    }
}