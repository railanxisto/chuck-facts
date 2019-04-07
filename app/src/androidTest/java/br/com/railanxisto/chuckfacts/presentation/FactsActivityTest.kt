package br.com.railanxisto.chuckfacts.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.railanxisto.chuckfacts.MockService
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.presentation.facts.FactsActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
@LargeTest
class FactsActivityTest : AutoCloseKoinTest() {
    val service: ChuckFactsService by inject()

    @get:Rule
    var activity = ActivityScenarioRule(FactsActivity::class.java)

    @Before
    fun setUp() {
        MockService(service)
    }

    @Test
    fun listIsEmptyOnFirstLoad() {
        Espresso.onView(ViewMatchers.withId(R.id.factsRecyclerView)).check { view, _ ->
            Assert.assertEquals(RecyclerView::class.java, view::class.java)
            val recyclerView = view as RecyclerView
            Assert.assertTrue(recyclerView.adapter?.itemCount == 0)
        }
    }

    @Test
    fun checkIfSearchFactsActivityIsLaunchedWhenSearchButtonClicked() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkMock() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText)).perform(
            ViewActions.typeText("food"),
            ViewActions.pressImeActionButton()
        )
        Espresso.onView(ViewMatchers.withId(R.id.factsRecyclerView)).check { view, _ ->
            Assert.assertEquals(RecyclerView::class.java, view::class.java)
            val recyclerView = view as RecyclerView
            Assert.assertTrue(recyclerView.adapter?.itemCount == 2)
        }
    }
}