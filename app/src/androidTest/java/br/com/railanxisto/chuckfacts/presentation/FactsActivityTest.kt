package br.com.railanxisto.chuckfacts.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.railanxisto.chuckfacts.MockService
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.config.ToastMatcher
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.presentation.facts.FactsActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
@LargeTest
class FactsActivityTest : KoinTest {
    val service: ChuckFactsService by inject()
    private lateinit var mockService: MockService

    @get:Rule
    var activity = ActivityScenarioRule(FactsActivity::class.java)

    @Before
    fun setUp() {
        mockService = MockService(service)
    }

    @Test
    fun checkIfFactsActivityIsShown() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
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
    fun checkIfItGoesBackToFactsActivityWhenPressBackButton() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.pressBack()
        checkIfFactsActivityIsShown()
    }

    @Test
    fun checkIfItShowsCorrectNumberOfResultsOnFactsActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText)).perform(
            ViewActions.typeText(mockService.QUERY),
            ViewActions.pressImeActionButton()
        )
        Espresso.onView(ViewMatchers.withId(R.id.factsRecyclerView)).check { view, _ ->
            Assert.assertEquals(RecyclerView::class.java, view::class.java)
            val recyclerView = view as RecyclerView
            Assert.assertTrue(recyclerView.adapter?.itemCount == mockService.listOfFacts.size)
        }
    }

    @Test
    fun checkIfItShowsSuggestions() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.suggestionsRecyclerView)).check { view, _ ->
            Assert.assertEquals(RecyclerView::class.java, view::class.java)
            val recyclerView = view as RecyclerView
            Assert.assertNotNull(recyclerView.adapter)
            Assert.assertTrue(recyclerView.adapter?.itemCount != 0)
        }
    }

    @Test
    fun checkIfItShowsToastWhenNoResultsForTerm() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText)).perform(
            ViewActions.typeText(mockService.QUERY_EMPTY),
            ViewActions.pressImeActionButton()
        )
        Thread.sleep(500)
        onView(withText(R.string.no_results)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun checkIfItShowsErrorScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.action_search)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText)).perform(
            ViewActions.typeText(mockService.QUERY_ERROR),
            ViewActions.pressImeActionButton()
        )
        Thread.sleep(500)
        Espresso.onView(ViewMatchers.withId(R.id.loadingErrorView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}