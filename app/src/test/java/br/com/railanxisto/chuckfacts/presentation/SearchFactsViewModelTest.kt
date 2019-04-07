package br.com.railanxisto.chuckfacts.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.railanxisto.chuckfacts.data.local.model.Term
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepository
import br.com.railanxisto.chuckfacts.domain.Category
import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsViewModel
import br.com.railanxisto.chuckfacts.testObserver
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchFactsViewModelTest : AutoCloseKoinTest() {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val searchFactsRepository by lazy { Mockito.mock(SearchFactsRepository::class.java) }
    private val searchFactsViewModel by lazy { SearchFactsViewModel(searchFactsRepository) }

    @Test
    fun `should return an empty list if no categories on repository`() {
        val categories = listOf<Category>()
        Mockito.`when`(searchFactsRepository.getCategories()).thenReturn(Maybe.just(categories))
        searchFactsViewModel.getCategories()
        val observer = searchFactsViewModel.getCategoriesList().testObserver()
        Assert.assertEquals(listOf(categories), observer.values)
    }

    @Test
    fun `should return categories from repository`() {
        val categories = listOf(Category("nome"))
        Mockito.`when`(searchFactsRepository.getCategories()).thenReturn(Maybe.just(categories))
        searchFactsViewModel.getCategories()
        val observer = searchFactsViewModel.getCategoriesList().testObserver()
        Assert.assertEquals(listOf(categories), observer.values)
    }

    @Test
    fun `should return an empty list if no past terms searched on repository`() {
        val pastSearches = Observable.just(listOf<Term>())
        Mockito.`when`(searchFactsRepository.getPastSearches()).thenReturn(pastSearches)
        searchFactsViewModel.getPastTerms()
        val observer = searchFactsViewModel.getPastTermsList().testObserver()
        Assert.assertEquals(listOf(pastSearches.blockingFirst()), observer.values)
    }

    @Test
    fun `should return past searched terms from repository`() {
        val pastSearches = Observable.just(listOf(Term(1, "term")))
        Mockito.`when`(searchFactsRepository.getPastSearches()).thenReturn(pastSearches)
        searchFactsViewModel.getPastTerms()
        val observer = searchFactsViewModel.getPastTermsList().testObserver()
        Assert.assertEquals(listOf(pastSearches.blockingFirst()), observer.values)
    }
}