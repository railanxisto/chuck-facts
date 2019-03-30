package br.com.railanxisto.chuckfacts.di

import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepository
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    // SearchFacts repository
    single<SearchFactsRepository> { SearchFactsRepositoryImpl(get(), get()) }

}