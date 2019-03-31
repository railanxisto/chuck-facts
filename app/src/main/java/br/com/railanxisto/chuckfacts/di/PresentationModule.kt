package br.com.railanxisto.chuckfacts.di

import br.com.railanxisto.chuckfacts.presentation.searchFacts.SearchFactsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module

val presentationModule = module {

    // SearchFacts ViewModel
    viewModel { SearchFactsViewModel(get())}
}