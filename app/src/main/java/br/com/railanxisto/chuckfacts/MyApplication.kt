package br.com.railanxisto.chuckfacts

import android.app.Application
import br.com.railanxisto.chuckfacts.di.databaseModule
import br.com.railanxisto.chuckfacts.di.networkModule
import br.com.railanxisto.chuckfacts.di.presentationModule
import br.com.railanxisto.chuckfacts.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(networkModule, presentationModule, repositoryModule, databaseModule)
        }
    }
}