package br.com.railanxisto.chuckfacts.presentation

import android.app.Application
import androidx.room.Room
import br.com.railanxisto.chuckfacts.data.local.AppDatabase
import br.com.railanxisto.chuckfacts.data.remote.ChuckFactsService
import br.com.railanxisto.chuckfacts.data.remote.config.HttpClientImpl
import br.com.railanxisto.chuckfacts.data.remote.config.RetrofitManager
import br.com.railanxisto.chuckfacts.data.remote.repositories.FactsRepository
import br.com.railanxisto.chuckfacts.data.remote.repositories.FactsRepositoryImpl
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepository
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepositoryImpl
import br.com.railanxisto.chuckfacts.presentation.facts.FactsViewModel
import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mockito

class MockApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val service by lazy { Mockito.mock(ChuckFactsService::class.java) }
        val presentationModule = module {

            // SearchFacts ViewModel
            viewModel { SearchFactsViewModel(get()) }

            // Facts ViewModel
            viewModel { FactsViewModel(get()) }
        }

        val networkModule = module {
            single { HttpClientImpl().create() }

            single { RetrofitManager() }

            single { service }
        }

        val databaseModule = module {

            single<AppDatabase> {
                Room.databaseBuilder(get(), AppDatabase::class.java, "myDatabase")
                    .build()
            }

            single { get<AppDatabase>().categoryDao() }
            single { get<AppDatabase>().termDao() }
        }

        val repositoryModule = module {

            // SearchFacts repository
            single<SearchFactsRepository> { SearchFactsRepositoryImpl(get(), get(), get()) }

            // Facts repository
            single<FactsRepository> { FactsRepositoryImpl(service, get()) }
        }

        startKoin {
            androidLogger()
            androidContext(this@MockApplication)
            modules(listOf(presentationModule, databaseModule, networkModule, repositoryModule))
        }
    }
}