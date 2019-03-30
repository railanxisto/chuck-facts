package br.com.railanxisto.chuckfacts.presentation.searchFacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.railanxisto.chuckfacts.data.remote.repositories.SearchFactsRepository
import br.com.railanxisto.chuckfacts.domain.Category

class SearchFactsViewModel(private val repository: SearchFactsRepository): ViewModel() {

    private val categories = MutableLiveData<List<String>>()

    fun getCategories(): LiveData<List<String>> {
        categories.value = repository.getCategories().execute().body()
        return categories
    }

}