package br.com.railanxisto.chuckfacts.di

import androidx.room.Room
import br.com.railanxisto.chuckfacts.data.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "myDatabase")
            .build()
    }

    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().termDao() }
}