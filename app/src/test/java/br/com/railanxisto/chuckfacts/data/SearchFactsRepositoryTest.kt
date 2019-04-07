package br.com.railanxisto.chuckfacts.data

import br.com.railanxisto.chuckfacts.data.local.DAO.CategoryDao
import br.com.railanxisto.chuckfacts.data.local.DAO.TermDao
import br.com.railanxisto.chuckfacts.data.local.model.Category
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepositoryImpl
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchFactsRepositoryTest : AutoCloseKoinTest() {

    val service by lazy { Mockito.mock(ChuckFactsService::class.java) }
    private val termDao by lazy { Mockito.mock(TermDao::class.java) }
    private val categoryDao by lazy { Mockito.mock(CategoryDao::class.java) }
    private val searchFactsRepository by lazy { SearchFactsRepositoryImpl(service, categoryDao, termDao) }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_mock_service() {
        val result = Single.just(listOf("dev", "food", "animal"))
        Mockito.`when`(service.getCategories()).thenReturn(result)
        Assert.assertEquals(service.getCategories(), result)
    }

    @Test
    fun `should return categories from api if database is empty`() {
        val response = Single.just(listOf("food", "dev"))
        Mockito.`when`(service.getCategories()).thenReturn(response)
        Mockito.`when`(categoryDao.getCategories()).thenReturn(
            Single.just(listOf())
        )
        searchFactsRepository
            .getCategories()
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertResult(
                listOf(
                    br.com.railanxisto.chuckfacts.domain.Category("food"),
                    br.com.railanxisto.chuckfacts.domain.Category("dev")
                )
            )
    }

    @Test
    fun `should return categories from database if they are not empty`() {
        Mockito.`when`(service.getCategories()).thenReturn(
            Single.just(listOf("animal", "science"))
        )
        Mockito.`when`(categoryDao.getCategories()).thenReturn(
            Single.just(listOf(Category("food"), Category("dev")))
        )
        searchFactsRepository
            .getCategories()
            .test()
            .await()
            .assertNoErrors()
            .assertComplete()
            .assertResult(
                listOf(
                    br.com.railanxisto.chuckfacts.domain.Category("food"),
                    br.com.railanxisto.chuckfacts.domain.Category("dev")
                )
            )
    }
}