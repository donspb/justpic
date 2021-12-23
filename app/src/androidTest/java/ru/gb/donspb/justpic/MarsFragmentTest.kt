package ru.gb.donspb.justpic

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.gb.donspb.justpic.ui.addon.MarsFragment

@RunWith(AndroidJUnit4::class)
class MarsFragmentTest {
    private lateinit var scenario: FragmentScenario<MarsFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun testRecyclerFragment() {
        onView(withId(R.id.totalCountTextView))
            .check(matches(withText("Number of results: 0")))
    }

    @Test
    fun fragment_testSetCountMethod() {
        scenario.onFragment() {
            fragment -> fragment.setCount(10)
        }
        onView(withId(R.id.totalCountTextView))
            .check(matches(withText("Number of results: 10")))
    }

    @Test
    fun fragment_testIncButton() {
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView))
            .check(matches(withText("Number of results: 1")))
    }

    @Test
    fun fragment_testDecButton() {
        onView(withId(R.id.decrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView))
            .check(matches(withText("Number of results: -1")))
    }


}