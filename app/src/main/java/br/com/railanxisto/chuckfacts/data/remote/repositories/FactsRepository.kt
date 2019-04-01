package br.com.railanxisto.chuckfacts.data.remote.repositories

import br.com.railanxisto.chuckfacts.data.local.DAO.TermDao
import br.com.railanxisto.chuckfacts.data.local.model.Term
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.model.FactsResponse
import br.com.railanxisto.chuckfacts.domain.Fact
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.Response

interface FactsRepository {
    fun saveSearch(term: String): Completable

    fun getFacts(term: String): Single<FactsResponse>
}

class FactsRepositoryImpl(val apiService: ChuckFactsService, val termDao: TermDao) : FactsRepository {
    override fun getFacts(term: String): Single<FactsResponse> {
        return apiService
            .getFacts(term)
            .subscribeOn(Schedulers.io())
    }

    override fun saveSearch(term: String) = termDao
        .insert(Term(term = term))
        .subscribeOn(Schedulers.io())
}