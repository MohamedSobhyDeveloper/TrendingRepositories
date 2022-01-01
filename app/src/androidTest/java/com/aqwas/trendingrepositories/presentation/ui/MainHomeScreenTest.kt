package com.aqwas.trendingrepositories.presentation.ui

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.rule.ActivityTestRule
import com.aqwas.trendingrepositories.R
import com.aqwas.trendingrepositories.home.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainHomeScreenTest {
    @Rule @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)
    @Test
    fun isMainActivityInView() {
       ActivityScenario.launch(MainActivity::class.java)
       onView(ViewMatchers.withId(R.id.layoutHome)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
    @Test
    fun sortListByName() {
        onView(ViewMatchers.withId(R.id.sortByName)).perform(click())
    }

    @Test
    fun sortListByStar() {
        onView(ViewMatchers.withId(R.id.sortByStar)).perform(click())
    }

    @Test
    fun retryButton() {
        onView(ViewMatchers.withId(R.id.retryBtn)).perform(click())
    }


}