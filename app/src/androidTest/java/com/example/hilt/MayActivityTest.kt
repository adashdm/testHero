package com.example.hilt


import MyRecycleViewViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@LargeTest
class MayActivityTest {

    @Rule
    fun activityRule(): ActivityScenarioRule<MayActivity> {
        return ActivityScenarioRule(MayActivity::class.java)
    }

    @Test
    fun recyclerViewDisplayedTest() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewItemCountTest() {
        sleep(2000)
        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)))
    }

}