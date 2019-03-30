package br.com.railanxisto.chuckfacts.di

import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.config.HttpClientImpl
import br.com.railanxisto.chuckfacts.data.remote.config.RetrofitManager
import org.koin.dsl.module

val networkModule = module {
    single { HttpClientImpl().create() }

    single { RetrofitManager() }
    single { ChuckFactsService.create() }
}