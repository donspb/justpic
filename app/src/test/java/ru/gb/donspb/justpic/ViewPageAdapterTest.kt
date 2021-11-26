package ru.gb.donspb.justpic

import androidx.fragment.app.FragmentManager
import org.junit.Test
import org.junit.Assert.*
import ru.gb.donspb.justpic.ui.addon.EarthFragment
import ru.gb.donspb.justpic.ui.addon.ViewPageAdapter

class FragmentManagerTest : FragmentManager() {
}

class ViewPageAdapterTest {

    val fragmentManagerTest = FragmentManagerTest()

    @Test
    fun viewPageAdapter_creationTest_returnNotNull() {
        val viewPageAdapter = ViewPageAdapter(fragmentManagerTest)
        assertNotNull(viewPageAdapter)
    }

    @Test
    fun viewPageAdapter_getCountTest_returnEquals() {
        assertEquals(3, ViewPageAdapter(fragmentManagerTest).count)
    }

    @Test
    fun viewPageAdapter_getItemTest_EarthFragment_returnTrue() {
        assertTrue(ViewPageAdapter(fragmentManagerTest).getItem(0) is EarthFragment)
    }

    @Test
    fun viewPageAdapter_getItemTitle_Weather_returnTrue() {
        assertTrue(ViewPageAdapter(fragmentManagerTest).getPageTitle(2)!!.equals("Weather"))
    }
}