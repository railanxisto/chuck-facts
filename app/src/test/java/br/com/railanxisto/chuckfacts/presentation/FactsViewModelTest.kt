package br.com.railanxisto.chuckfacts.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import br.com.railanxisto.chuckfacts.data.remote.repositories.FactsRepository
import domain.Fact
import br.com.railanxisto.chuckfacts.presentation.facts.FactsViewModel
import br.com.railanxisto.chuckfacts.testObserver
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FactsViewModelTest : AutoCloseKoinTest() {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val factsRepository by lazy { Mockito.mock(FactsRepository::class.java) }
    private val factsViewModel by lazy { FactsViewModel(factsRepository) }

    @Test
    fun `should return empty list for facts at the first load`() {
        val observer = factsViewModel.getFactsList().testObserver()
        Assert.assertEquals(listOf<Fact>(), observer.values)
    }

    @Test
    fun `should return empty list for isLoading at the first load`() {
        val testObserver = factsViewModel.isLoading.testObserver()
        Assert.assertEquals(listOf<Fact>(), testObserver.values)
    }

    @Test
    fun `should return facts when a term is searched`() {
        val facts = FactsResponse(1, listOf(
            Fact("1", "", "Fact 1", null, "")
        ))
        Mockito.`when`(factsRepository.getFacts("food")).thenReturn(Single.just(facts))
        Mockito.`when`(factsRepository.saveSearch("food")).thenAnswer { Completable.complete() }
        val observer = factsViewModel.getFactsList().testObserver()
        factsViewModel.getFacts("food")
        Assert.assertEquals(listOf(facts.result), observer.values)
    }

    @Test
    fun `should update facts when a new term is searched`() {
        val term1 = "food"
        val term2 = "dev"
        val facts = FactsResponse(1, listOf(
            Fact("1", "", "Fact 1", null, "")
        ))
        val facts2 = FactsResponse(1, listOf(
            Fact("2", "", "Fact 2", null, "")
        ))
        Mockito.`when`(factsRepository.saveSearch(term1)).thenAnswer { Completable.complete() }
        Mockito.`when`(factsRepository.saveSearch(term2)).thenAnswer { Completable.complete() }
        Mockito.`when`(factsRepository.getFacts(term1)).thenReturn(Single.just(facts))
        val valuesTerm1 = factsViewModel.getFactsList().testObserver().values
        factsViewModel.getFacts(term1)
        Mockito.`when`(factsRepository.getFacts(term2)).thenReturn(Single.just(facts2))
        val observer = factsViewModel.getFactsList().testObserver()
        factsViewModel.getFacts(term2)
        Assert.assertNotEquals(valuesTerm1[0], observer.values[1])
    }

    @Test
    fun `should call saveSearch in the database when a term is searched`() {
        val response = FactsResponse(1, listOf())
        Mockito.`when`(factsRepository.getFacts("food")).thenReturn(Single.just(response))

        var sucess = false
        Mockito.`when`(factsRepository.saveSearch("food")).thenAnswer {
                sucess = true
                Completable.complete()
        }

        factsViewModel.getFacts("food")
        Assert.assertEquals(true, sucess)
    }
}
