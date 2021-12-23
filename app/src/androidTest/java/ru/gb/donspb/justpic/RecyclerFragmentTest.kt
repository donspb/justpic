package ru.gb.donspb.justpic

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.gb.donspb.justpic.ui.rv.EpicRecycler
import ru.gb.donspb.justpic.ui.rv.RecyclerFragment

@RunWith(AndroidJUnit4::class)
class RecyclerFragmentTest {

    private lateinit var scenario: FragmentScenario<RecyclerFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
        onView(isRoot()).perform(delay())
    }

    @Test
    fun fragmentRecycler_ScrollTo() {
        onView(withId(R.id.recycler_earth))
            .perform(RecyclerViewActions.scrollToPosition<EpicRecycler.ViewHolder>(20))
    }

    @Test
    fun fragmentRecycler_CLickOnPosition() {
        onView(withId(R.id.recycler_earth)).perform(
            RecyclerViewActions.actionOnItemAtPosition<EpicRecycler.ViewHolder>(0, click())
        )
    }

    @Test
    fun activitySearch_PerformClickOnItem() {
        onView(withId(R.id.recycler_earth))
            .perform(RecyclerViewActions.scrollToPosition<EpicRecycler.ViewHolder>(20))
        onView(withId(R.id.recycler_earth))
            .perform(RecyclerViewActions.actionOnItemAtPosition<EpicRecycler.ViewHolder>(
                10, click()))
    }

    private fun delay(ms: Long = 1000): ViewAction {
        return object: ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait a little"
            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(ms)
            }

        }
    }
}