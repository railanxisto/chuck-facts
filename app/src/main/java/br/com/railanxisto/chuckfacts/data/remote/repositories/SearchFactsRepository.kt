package br.com.railanxisto.chuckfacts.data.remote.repositories

import br.com.railanxisto.chuckfacts.data.local.DAO.CategoryDao
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.domain.Category
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

interface SearchFactsRepository {
    fun getCategories(): Maybe<List<Category>>

    fun getPastSearches(quantity: Int): Response<List<String>>
}

class SearchFactsRepositoryImpl(val apiService: ChuckFactsService, val categoryDao: CategoryDao) : SearchFactsRepository {
    override fun getCategories(): Maybe<List<Category>> {

        val data = getCategoriesFromDatabase()
        val dataFromApi = getCategoriesFromApi()
            .doOnSuccess { categoryDao.insertCategories(
                    it.map {
                        br.com.railanxisto.chuckfacts.data.local.model.Category(it.name)
                    })
            }

        return Maybe
            .concat(data, dataFromApi)
            .subscribeOn(Schedulers.io())
            .firstElement()
    }

    override fun getPastSearches(quantity: Int): Response<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getCategoriesFromDatabase() = categoryDao
        .getCategories()
        .filter { !it.isEmpty() }
        .map { list ->
            list.map { Category(it.name) }
        }

    private fun getCategoriesFromApi() = apiService
        .getCategories()
        .filter { !it.isEmpty() }
        .map { list ->
            list.map { Category(it) }
        }

}