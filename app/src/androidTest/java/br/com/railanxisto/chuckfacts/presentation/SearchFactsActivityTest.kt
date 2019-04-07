package br.com.railanxisto.chuckfacts.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.presentation.facts.FactsActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchFactsActivityTest {

    @get:Rule
    var activity = ActivityScenarioRule(FactsActivity::class.java)

    @Test
    fun checkIfSearchFactsActivityIsLaunchedWhenSearchButtonClicked() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun listIsEmptyOnFirstLoad() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.pastSearchesRecyclerView)).check { view, _ ->
            Assert.assertEquals(RecyclerView::class.java, view::class.java)
            val recyclerView = view as RecyclerView
            Assert.assertEquals(1, recyclerView.adapter?.itemCount)
        }
    }
}