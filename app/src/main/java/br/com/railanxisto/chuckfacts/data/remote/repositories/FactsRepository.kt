package br.com.railanxisto.chuckfacts.data.remote.repositories

import br.com.railanxisto.chuckfacts.data.local.DAO.TermDao
import br.com.railanxisto.chuckfacts.data.local.model.Term
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.domain.Fact
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface FactsRepository {
    fun saveSearch(term: String): Completable

    fun getFacts(term: String): Single<List<Fact>>
}

class FactsRepositoryImpl(val apiService: ChuckFactsService, val termDao: TermDao) : FactsRepository {
    override fun getFacts(term: String): Single<List<Fact>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveSearch(term: String) = termDao
        .insert(Term(term = term))
        .subscribeOn(Schedulers.io())
}